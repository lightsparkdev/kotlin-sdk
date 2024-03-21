package com.lightspark

import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import com.lightspark.sdk.model.Node
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.plugins.origin
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.host
import io.ktor.server.request.port
import io.ktor.server.request.receive
import io.ktor.server.request.uri
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.util.toMap
import java.util.concurrent.CompletableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future
import kotlinx.datetime.Clock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.uma.InMemoryNonceCache
import me.uma.UmaInvoiceCreator
import me.uma.UmaProtocolHelper
import me.uma.UnsupportedVersionException
import me.uma.protocol.CounterPartyDataOptions
import me.uma.protocol.KycStatus
import me.uma.protocol.LnurlpResponse
import me.uma.protocol.PayRequest
import me.uma.protocol.createCounterPartyDataOptions
import me.uma.protocol.createPayeeData
import me.uma.protocol.identifier

class Vasp2(
    private val config: UmaConfig,
    private val uma: UmaProtocolHelper,
    private val lightsparkClient: LightsparkCoroutinesClient,
) {
    private val nonceCache = InMemoryNonceCache(Clock.System.now().epochSeconds)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var senderUmaVersion: String

    suspend fun handleLnurlp(call: ApplicationCall): String {
        val username = call.parameters["username"]

        if (username == null) {
            call.respond(HttpStatusCode.BadRequest, "Username not provided.")
            return "Username not provided."
        }

        if (username != config.username && username != "$${config.username}") {
            call.respond(HttpStatusCode.NotFound, "Username not found.")
            return "Username not found."
        }

        val requestUrl = call.request.fullUrl()
        if (uma.isUmaLnurlpQuery(requestUrl)) {
            return handleUmaLnurlp(call)
        } else {
            call.respond("Only UMA Supported")
        }

        return "OK"
    }

    private suspend fun handleUmaLnurlp(call: ApplicationCall): String {
        val requestUrl = call.request.fullUrl()
        val request = try {
            uma.parseLnurlpRequest(requestUrl)
        } catch (e: UnsupportedVersionException) {
            call.respond(HttpStatusCode.PreconditionFailed, e.toLnurlpResponseJson())
            return "Unsupported version: ${e.unsupportedVersion}."
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid lnurlp request.")
            return "Invalid lnurlp request."
        }.asUmaRequest() ?: run {
            // Handle non-UMA LNURL requests.
            val response = LnurlpResponse(
                callback = getLnurlpCallback(call),
                minSendable = 1,
                maxSendable = 100_000_000,
                metadata = getEncodedMetadata(),
                currencies = getReceivingCurrencies(senderUmaVersion),
                requiredPayerData = createCounterPartyDataOptions(
                    "name" to false,
                    "email" to false,
                    "identifier" to false,
                ),
                compliance = null,
                umaVersion = null,
            )
            call.respond(response)
            return "OK"
        }

        senderUmaVersion = request.umaVersion

        val pubKeys = try {
            uma.fetchPublicKeysForVasp(request.vaspDomain)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Failed to fetch public keys. ${e.message}")
            return "Failed to fetch public keys."
        }

        try {
            require(uma.verifyUmaLnurlpQuerySignature(request, pubKeys, nonceCache)) { "Invalid lnurlp signature." }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid lnurlp signature. ${e.message}")
            return "Invalid lnurlp signature."
        }

        val response = try {
            uma.getLnurlpResponse(
                query = request.asLnurlpRequest(),
                privateKeyBytes = config.umaSigningPrivKey,
                requiresTravelRuleInfo = true,
                callback = getLnurlpCallback(call),
                encodedMetadata = getEncodedMetadata(),
                minSendableSats = 1,
                maxSendableSats = 100_000_000,
                payerDataOptions = createCounterPartyDataOptions(
                    "name" to false,
                    "email" to false,
                    "compliance" to true,
                    "identifier" to true,
                ),
                currencyOptions = getReceivingCurrencies(senderUmaVersion),
                receiverKycStatus = KycStatus.VERIFIED,
            )
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to generate lnurlp response.")
            return "Failed to generate lnurlp response."
        }

        call.respond(response)

        return "OK"
    }

    suspend fun handleLnurlPayreq(call: ApplicationCall): String {
        val uuid = call.parameters["uuid"]

        if (uuid == null) {
            call.respond(HttpStatusCode.BadRequest, "UUID not provided.")
            return "UUID not provided."
        }

        if (uuid != config.userID) {
            call.respond(HttpStatusCode.NotFound, "UUID not found.")
            return "UUID not found."
        }

        val paramMap = call.request.queryParameters.toMap()
        val payreq = try {
            PayRequest.fromQueryParamMap(paramMap)
        } catch (e: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest, "Invalid pay request.")
            return "Invalid pay request."
        }

        val lnurlInvoiceCreator = object : UmaInvoiceCreator {
            override fun createUmaInvoice(amountMsats: Long, metadata: String): CompletableFuture<String> {
                return coroutineScope.future {
                    lightsparkClient.createLnurlInvoice(config.nodeID, amountMsats, metadata).data.encodedPaymentRequest
                }
            }
        }

        val receivingCurrency = getReceivingCurrencies(senderUmaVersion)
            .firstOrNull { it.code == payreq.receivingCurrencyCode() } ?: run {
                call.respond(HttpStatusCode.BadRequest, "Unsupported currency.")
                return "Unsupported currency."
            }

        val response = uma.getPayReqResponse(
            query = payreq,
            invoiceCreator = lnurlInvoiceCreator,
            metadata = getEncodedMetadata(),
            receivingCurrencyCode = payreq.receivingCurrencyCode(),
            receivingCurrencyDecimals = receivingCurrency.decimals,
            conversionRate = receivingCurrency.millisatoshiPerUnit,
            receiverFeesMillisats = 0,
            receiverChannelUtxos = null,
            receiverNodePubKey = null,
            utxoCallback = null,
            receivingVaspPrivateKey = null,
            senderUmaVersion = senderUmaVersion,
        )

        call.respond(response)
        return "OK"
    }

    suspend fun handleUmaPayreq(call: ApplicationCall): String {
        val uuid = call.parameters["uuid"]

        if (uuid == null) {
            call.respond(HttpStatusCode.BadRequest, "UUID not provided.")
            return "UUID not provided."
        }

        if (uuid != config.userID) {
            call.respond(HttpStatusCode.NotFound, "UUID not found.")
            return "UUID not found."
        }

        val request = try {
            uma.parseAsPayRequest(call.receive<String>())
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid pay request. ${e.message}")
            return "Invalid pay request."
        }

        if (!request.isUmaRequest()) {
            call.respond(HttpStatusCode.BadRequest, "Invalid UMA pay request to POST endpoint.")
            return "Invalid UMA pay request to POST endpoint."
        }

        val pubKeys = try {
            val sendingVaspDomain = uma.getVaspDomainFromUmaAddress(request.payerData!!.identifier()!!)
            uma.fetchPublicKeysForVasp(sendingVaspDomain)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Failed to fetch public keys.")
            return "Failed to fetch public keys."
        }
        try {
            require(uma.verifyPayReqSignature(request, pubKeys, nonceCache))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid payreq signature.")
            return "Invalid payreq signature."
        }

        val receivingCurrency = getReceivingCurrencies(senderUmaVersion)
            .firstOrNull { it.code == request.receivingCurrencyCode() } ?: run {
                call.respond(HttpStatusCode.BadRequest, "Unsupported currency.")
                return "Unsupported currency."
            }

        val client = LightsparkCoroutinesClient(
            ClientConfig(
                serverUrl = config.clientBaseURL ?: "api.lightspark.com",
                authProvider = AccountApiTokenAuthProvider(config.apiClientID, config.apiClientSecret),
            ),
        )
        val expirySecs = 60 * 5
        val payeeProfile = getPayeeProfile(request.requestedPayeeData(), call)

        val response = try {
            uma.getPayReqResponse(
                query = request,
                invoiceCreator = LightsparkClientUmaInvoiceCreator(client, config.nodeID, expirySecs),
                metadata = getEncodedMetadata(),
                receivingCurrencyCode = receivingCurrency.code,
                receivingCurrencyDecimals = receivingCurrency.decimals,
                conversionRate = receivingCurrency.millisatoshiPerUnit,
                receiverFeesMillisats = 0,
                // TODO(Jeremy): Actually get the UTXOs from the request.
                receiverChannelUtxos = emptyList(),
                receiverNodePubKey = getNodePubKey(),
                utxoCallback = getUtxoCallback(call, "1234"),
                receivingVaspPrivateKey = config.umaSigningPrivKey,
                payeeData = createPayeeData(
                    identifier = payeeProfile.identifier,
                    name = payeeProfile.name,
                    email = payeeProfile.email,
                ),
                senderUmaVersion = senderUmaVersion,
            )
        } catch (e: Exception) {
            call.application.environment.log.error("Failed to create payreq response.", e)
            call.respond(HttpStatusCode.InternalServerError, "Failed to create payreq response.")
            return "Failed to create payreq response."
        }

        call.respond(response.toJson())

        return "OK"
    }

    /**
     * NOTE: In a real application, you'd want to use the authentication context to pull out this information. It's not
     * actually always Bob receiving the money ;-).
     */
    private fun getPayeeProfile(payeeData: CounterPartyDataOptions?, call: ApplicationCall) = PayeeProfile(
        name = if (payeeData?.get("name")?.mandatory == true) config.username else null,
        email = if (payeeData?.get("email")?.mandatory == true) {
            "${config.username}@${getReceivingVaspDomain(call)}"
        } else {
            null
        },
        identifier = "\$${config.username}@${getReceivingVaspDomain(call)}",
    )

    private fun getEncodedMetadata(): String {
        val metadata = mapOf(
            "text/plain" to "Pay to ${config.username}@vasp2.com",
            "text/identifier" to "${config.username}@vasp2.com",
        )
        return Json.encodeToString(metadata)
    }

    private fun getLnurlpCallback(call: ApplicationCall): String {
        val protocol = call.request.origin.scheme
        val port = call.request.origin.localPort
        val portString = if (port == 80 || port == 443) "" else ":$port"
        val host = call.request.host()
        val path = "/api/uma/payreq/${config.userID}"
        return "$protocol://$host$portString$path"
    }

    private fun getUtxoCallback(call: ApplicationCall, txId: String): String {
        val protocol = call.request.origin.scheme
        val host = call.request.host()
        val path = "/api/uma/utxoCallback?txId=$txId"
        return "$protocol://$host$path"
    }

    private suspend fun getNodePubKey(): String? {
        return lightsparkClient.executeQuery(Node.getNodeQuery(config.nodeID)).publicKey
    }

    private fun ApplicationRequest.fullUrl(): String {
        val host = host()
        val port = if (isDomainLocalhost(host)) ":${port()}" else ""
        val protocol = origin.scheme
        val path = uri
        return "$protocol://$host$port$path"
    }

    private fun getReceivingVaspDomain(call: ApplicationCall) = config.vaspDomain ?: call.originWithPort()
}

fun Routing.registerVasp2Routes(vasp2: Vasp2) {
    get("/.well-known/lnurlp/{username}") {
        call.debugLog(vasp2.handleLnurlp(call))
    }

    get("/api/uma/payreq/{uuid}") {
        call.debugLog(vasp2.handleLnurlPayreq(call))
    }

    post("/api/uma/payreq/{uuid}") {
        call.debugLog(vasp2.handleUmaPayreq(call))
    }
}

private data class PayeeProfile(
    val name: String?,
    val email: String?,
    val identifier: String,
)
