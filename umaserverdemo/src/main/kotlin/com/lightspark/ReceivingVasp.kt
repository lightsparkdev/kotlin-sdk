package com.lightspark

import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import com.lightspark.sdk.model.Node
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.plugins.origin
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.host
import io.ktor.server.request.port
import io.ktor.server.request.receiveText
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
import me.uma.UMA_VERSION_STRING
import me.uma.UmaInvoiceCreator
import me.uma.UmaProtocolHelper
import me.uma.UnsupportedVersionException
import me.uma.protocol.CounterPartyDataOptions
import me.uma.protocol.InvoiceCurrency
import me.uma.protocol.KycStatus
import me.uma.protocol.LnurlpResponse
import me.uma.protocol.PayRequest
import me.uma.protocol.createCounterPartyDataOptions
import me.uma.protocol.createPayeeData
import me.uma.protocol.identifier
import java.util.UUID
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive


class ReceivingVasp(
    private val config: UmaConfig,
    private val uma: UmaProtocolHelper,
    private val lightsparkClient: LightsparkCoroutinesClient,
) {
    private val nonceCache = InMemoryNonceCache(Clock.System.now().epochSeconds)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var senderUmaVersion: String
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                },
            )
        }
    }

    suspend fun createInvoice(call: ApplicationCall): String {
        val (status, data) = createUmaInvoice(call)
        if (status != HttpStatusCode.OK) {
            call.respond(status, data)
            return data
        } else {
            call.respond(data)
        }
        return "OK"
    }

    suspend fun createAndSendInvoice(call: ApplicationCall): String {
        val senderUma = call.parameters["senderUma"] ?: run {
            call.respond(HttpStatusCode.BadRequest, "SenderUma not provided.")
            return "SenderUma not provided."
        }
        val senderUmaComponents = senderUma.split("@")
        if (senderUmaComponents.size != 2) {
            call.respond(HttpStatusCode.BadRequest, "SenderUma format invalid: $senderUma.")
            return "SenderUma format invalid: $senderUma."
        }
        val (status, data) = createUmaInvoice(call, senderUma)
        if (status != HttpStatusCode.OK) {
            call.respond(status, data)
            return data
        }
        val senderComponents = senderUma.split("@")
        val sendingVaspDomain = senderComponents.getOrNull(1) ?: run {
            call.respond(HttpStatusCode.BadRequest, "Invalid senderUma.")
            return "Invalid senderUma."
        }
        val wellKnownConfiguration = "http://$sendingVaspDomain/.well-known/uma-configuration"
        val umaEndpoint = try {
            val umaConfigResponse = httpClient.get(wellKnownConfiguration)
            if (umaConfigResponse.status != HttpStatusCode.OK) {
                call.respond(
                    HttpStatusCode.FailedDependency,
                    "failed to fetch request / pay endpoint at $wellKnownConfiguration",
                )
                return "failed to fetch request / pay endpoint at $wellKnownConfiguration"
            }
            Json.decodeFromString<JsonObject>(
                umaConfigResponse.bodyAsText(),
            )["uma_request_endpoint"]?.jsonPrimitive?.content
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.FailedDependency,
                "failed to fetch request / pay endpoint at $wellKnownConfiguration",
            )
            return "failed to fetch request / pay endpoint at $wellKnownConfiguration"
        }
        if (umaEndpoint == null) {
            call.respond(HttpStatusCode.FailedDependency, "failed to fetch $wellKnownConfiguration")
            return "failed to fetch $wellKnownConfiguration"
        }
        val response = try {
            httpClient.post(umaEndpoint) {
                contentType(ContentType.Application.Json)
                setBody(parameter("invoice", data))
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.FailedDependency, "failed to fetch $umaEndpoint")
            return "failed to fetch $umaEndpoint"
        }
        if (response.status != HttpStatusCode.OK) {
            call.respond(HttpStatusCode.InternalServerError, "Payreq to Sending Vasp failed: ${response.status}")
            return "Payreq to sending failed: ${response.status}"
        }
        call.respond(response.body())
        return "OK"
    }

    private fun createUmaInvoice(
        call: ApplicationCall, senderUma: String? = null
    ): Pair<HttpStatusCode, String> {
        val amount = try {
            call.parameters["amount"]?.toLong() ?: run {
                return HttpStatusCode.BadRequest to "Amount not provided."
            }
        } catch (e: NumberFormatException) {
            return HttpStatusCode.BadRequest to "Amount not parsable as number."
        }

        val currency = call.parameters["currencyCode"]?.let { currencyCode ->
            // check if we support this currency code.
            getReceivingCurrencies(UMA_VERSION_STRING).firstOrNull {
                it.code == currencyCode
            } ?: run {
                return HttpStatusCode.BadRequest to "Unsupported CurrencyCode $currencyCode."
            }
        } ?: run {
            return HttpStatusCode.BadRequest to "CurrencyCode not provided."
        }
        
        if (amount < currency.minSendable() || amount > currency.maxSendable()) {
            return HttpStatusCode.BadRequest to "CurrencyCode amount is outside of sendable range."
        }

        val expiresIn2Days = Clock.System.now().plus(2, DateTimeUnit.HOUR*24)

        val receiverUma = buildReceiverUma(call)

        val invoice = uma.getInvoice(
            receiverUma = receiverUma,
            invoiceUUID = UUID.randomUUID().toString(),
            amount = amount,
            receivingCurrency = InvoiceCurrency(
                currency.code, currency.name, currency.symbol, currency.decimals
            ),
            expiration = expiresIn2Days.epochSeconds,
            isSubjectToTravelRule = true,
            requiredPayerData = createCounterPartyDataOptions(
                "name" to false,
                "email" to false,
                "compliance" to true,
                "identifier" to true,
            ),
            callback = getLnurlpCallback(call), // structured the same, going to /api/uma/payreq/{user_id}
            privateSigningKey = config.umaSigningPrivKey,
            senderUma = senderUma
        )

        return HttpStatusCode.OK to invoice.toBech32()
    }

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
        val request = try {
            uma.parseLnurlpRequest(requestUrl)
        } catch (e: UnsupportedVersionException) {
            call.respond(HttpStatusCode.PreconditionFailed, e.toLnurlpResponseJson())
            return "Unsupported version: ${e.unsupportedVersion}."
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid lnurlp request.")
            return "Invalid lnurlp request."
        }.asUmaRequest() ?: run {
            senderUmaVersion = UMA_VERSION_STRING
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
            override fun createUmaInvoice(amountMsats: Long, metadata: String, receiverIdentifier: String?,): CompletableFuture<String> {
                return coroutineScope.future {
                    lightsparkClient.createLnurlInvoice(config.nodeID, amountMsats, metadata).data.encodedPaymentRequest
                }
            }
        }

        val receivingCurrency = payreq.receivingCurrencyCode()?.let {
            getReceivingCurrencies(senderUmaVersion)
                .firstOrNull { it.code == payreq.receivingCurrencyCode() } ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Unsupported currency.")
                    return "Unsupported currency."
                }
        }

        val response = uma.getPayReqResponse(
            query = payreq,
            invoiceCreator = lnurlInvoiceCreator,
            metadata = getEncodedMetadata(),
            receivingCurrencyCode = payreq.receivingCurrencyCode(),
            receivingCurrencyDecimals = receivingCurrency?.decimals,
            conversionRate = receivingCurrency?.millisatoshiPerUnit,
            receiverFeesMillisats = 0,
            receiverChannelUtxos = null,
            receiverNodePubKey = null,
            utxoCallback = null,
            receivingVaspPrivateKey = null,
            senderUmaVersion = senderUmaVersion,
        )

        call.respond(response.toJson())
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
            uma.parseAsPayRequest(call.receiveText())
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid pay request. ${e.message}")
            return "Invalid pay request. ${e.message}"
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

        senderUmaVersion = UMA_VERSION_STRING
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
                invoiceCreator = LightsparkClientUmaInvoiceCreator(
                    client = client,
                    nodeId = config.nodeID,
                    expirySecs = expirySecs,
                    enableUmaAnalytics = true,
                    signingPrivateKey = config.umaSigningPrivKey,
                ),
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

    private fun buildReceiverUma(call: ApplicationCall) = "$${config.username}@${getReceivingVaspDomain(call)}"

    private fun getReceivingVaspDomain(call: ApplicationCall) = config.vaspDomain ?: call.originWithPort()
}

fun Routing.registerReceivingVaspRoutes(receivingVasp: ReceivingVasp) {
    get("/.well-known/lnurlp/{username}") {
        call.debugLog(receivingVasp.handleLnurlp(call))
    }

    get("/api/uma/payreq/{uuid}") {
        call.debugLog(receivingVasp.handleLnurlPayreq(call))
    }

    post("/api/uma/payreq/{uuid}") {
        call.debugLog(receivingVasp.handleUmaPayreq(call))
    }

    post("/api/uma/create_invoice") {
        call.debugLog(receivingVasp.createInvoice(call));
    }

    post("/api/uma/create_and_send_invoice") {
        call.debugLog(receivingVasp.createAndSendInvoice(call))
    }
}

private data class PayeeProfile(
    val name: String?,
    val email: String?,
    val identifier: String,
)
