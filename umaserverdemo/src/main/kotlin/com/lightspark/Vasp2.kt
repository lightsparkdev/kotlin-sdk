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
import me.uma.UmaProtocolHelper
import me.uma.UnsupportedVersionException
import me.uma.protocol.Currency
import me.uma.protocol.KycStatus
import me.uma.protocol.PayRequest
import me.uma.protocol.PayerDataOptions
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

// In real life, this would come from some actual exchange rate API.
private const val MSATS_PER_USD_CENT = 22883.56

class Vasp2(
    private val config: UmaConfig,
    private val uma: UmaProtocolHelper,
    private val lightsparkClient: LightsparkCoroutinesClient,
) {
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

        return if (uma.isUmaLnurlpQuery(requestUrl)) {
            handleUmaLnurlp(call)
        } else {
            handleNonUmaLnurlp(call)
        }
    }

    private suspend fun handleNonUmaLnurlp(call: ApplicationCall): String {
        val response = try {
            buildJsonObject {
                put("callback", getLnurlpCallback(call))
                put("maxSendable", 10_000_000)
                put("minSendable", 1_000)
                put("metadata", getEncodedMetadata())
                put("tag", "payRequest")
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to generate normal lnurlp response.")
            return "Failed to generate normal lnurlp response."
        }

        call.respond(response)

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
        }

        val pubKeys = try {
            uma.fetchPublicKeysForVasp(request.vaspDomain)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Failed to fetch public keys. ${e.message}")
            return "Failed to fetch public keys."
        }

        try {
            require(uma.verifyUmaLnurlpQuerySignature(request, pubKeys)) { "Invalid lnurlp signature." }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid lnurlp signature. ${e.message}")
            return "Invalid lnurlp signature."
        }

        val response = try {
            uma.getLnurlpResponse(
                query = request,
                privateKeyBytes = config.umaSigningPrivKey,
                requiresTravelRuleInfo = true,
                callback = getLnurlpCallback(call),
                encodedMetadata = getEncodedMetadata(),
                minSendableSats = 1,
                maxSendableSats = 100_000_000,
                payerDataOptions = PayerDataOptions(nameRequired = false, emailRequired = false, complianceRequired = true),
                currencyOptions = listOf(
                    Currency(
                        code = "USD",
                        name = "US Dollar",
                        symbol = "$",
                        millisatoshiPerUnit = MSATS_PER_USD_CENT,
                        minSendable = 1,
                        maxSendable = 10_000_000,
                        decimals = 2,
                    ),
                ),
                receiverKycStatus = KycStatus.VERIFIED,
            )
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to generate UMA lnurlp response.")
            return "Failed to generate UMA lnurlp response."
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

        val amountParam = call.request.queryParameters["amount"]
        val amountMsats = amountParam?.toLongOrNull()
        if (amountMsats == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid or missing amount.")
            return "Invalid or missing amount."
        }

        val invoice = try {
            lightsparkClient.createLnurlInvoice(config.nodeID, amountMsats, getEncodedMetadata())
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to create invoice.")
            return "Failed to create invoice."
        }

        call.respond(
            mapOf(
                "pr" to invoice.data.encodedPaymentRequest,
                "routes" to emptyList<String>(),
            ),
        )

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
            call.receive<PayRequest>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid pay request. ${e.message}")
            return "Invalid pay request."
        }

        val pubKeys = try {
            val sendingVaspDomain = uma.getVaspDomainFromUmaAddress(request.payerData.identifier)
            uma.fetchPublicKeysForVasp(sendingVaspDomain)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Failed to fetch public keys.")
            return "Failed to fetch public keys."
        }
        try {
            require(uma.verifyPayReqSignature(request, pubKeys))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid payreq signature.")
            return "Invalid payreq signature."
        }

        val client = LightsparkCoroutinesClient(
            ClientConfig(
                serverUrl = config.clientBaseURL ?: "api.lightspark.com",
                authProvider = AccountApiTokenAuthProvider(config.apiClientID, config.apiClientSecret),
            ),
        )
        val expirySecs = 60 * 5

        val response = try {
            uma.getPayReqResponse(
                query = request,
                invoiceCreator = LightsparkClientUmaInvoiceCreator(client, config.nodeID, expirySecs),
                metadata = getEncodedMetadata(),
                currencyCode = "USD",
                currencyDecimals = 2,
                conversionRate = MSATS_PER_USD_CENT,
                receiverFeesMillisats = 0,
                // TODO(Jeremy): Actually get the UTXOs from the request.
                receiverChannelUtxos = emptyList(),
                receiverNodePubKey = getNodePubKey(),
                utxoCallback = getUtxoCallback(call, "1234"),
            )
        } catch (e: Exception) {
            call.application.environment.log.error("Failed to create payreq response.", e)
            call.respond(HttpStatusCode.InternalServerError, "Failed to create payreq response.")
            return "Failed to create payreq response."
        }

        call.respond(response)

        return "OK"
    }

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
        val path = "/api/uma/utxoCallback?txId=${txId}"
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
