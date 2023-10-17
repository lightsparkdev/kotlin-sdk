package com.lightspark

import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.execute
import com.lightspark.sdk.model.OutgoingPayment
import com.lightspark.sdk.model.TransactionStatus
import com.lightspark.sdk.util.toMilliSats
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.plugins.origin
import io.ktor.server.request.host
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import me.uma.UmaProtocolHelper
import me.uma.protocol.KycStatus
import me.uma.protocol.LnurlpResponse
import me.uma.protocol.PayReqResponse
import me.uma.protocol.PayerDataOptions
import me.uma.protocol.UtxoWithAmount
import me.uma.selectHighestSupportedVersion
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray

class Vasp1(
    private val config: UmaConfig,
    private val uma: UmaProtocolHelper,
    private val lightsparkClient: LightsparkCoroutinesClient,
) {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                },
            )
        }
    }
    private val requestDataCache = Vasp1RequestCache()

    suspend fun handleClientUmaLookup(call: ApplicationCall): String {
        val receiverAddress = call.parameters["receiver"]
        if (receiverAddress == null) {
            call.respond(HttpStatusCode.BadRequest, "Receiver not provided.")
            return "Receiver not provided."
        }

        val addressParts = receiverAddress.split("@")
        if (addressParts.size != 2) {
            call.respond(HttpStatusCode.BadRequest, "Invalid receiver address.")
            return "Invalid receiver address."
        }
        val receiverId = addressParts[0]
        val receiverVasp = addressParts[1]
        val signingKey = config.umaSigningPrivKey

        val lnurlpRequest = uma.getSignedLnurlpRequestUrl(
            signingPrivateKey = signingKey,
            receiverAddress = receiverAddress,
            // TODO: This should be configurable.
            senderVaspDomain = "localhost:8080",
            isSubjectToTravelRule = true,
        )

        var response = try {
            httpClient.get(lnurlpRequest)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.FailedDependency, "Failed to fetch lnurlp response.")
            return "Failed to fetch lnurlp response."
        }

        if (response.status == HttpStatusCode.PreconditionFailed) {
            val responseBody = response.body<JsonObject>()
            val supportedMajorVersions = responseBody["supportedMajorVersions"]?.jsonArray?.mapNotNull {
                it.jsonPrimitive.int
            } ?: emptyList()
            if (supportedMajorVersions.isEmpty()) {
                call.respond(HttpStatusCode.FailedDependency, "Failed to fetch lnurlp response.")
                return "Failed to fetch lnurlp response."
            }
            val newSupportedVersion = selectHighestSupportedVersion(supportedMajorVersions) ?: run {
                call.respond(HttpStatusCode.FailedDependency, "No matching UMA version compatible with receiving VASP.")
                return "No matching UMA version compatible with receiving VASP."
            }

            val retryLnurlpRequest = uma.getSignedLnurlpRequestUrl(
                signingPrivateKey = signingKey,
                receiverAddress = receiverAddress,
                // TODO: This should be configurable.
                senderVaspDomain = "localhost:8080",
                isSubjectToTravelRule = true,
                umaVersion = newSupportedVersion,
            )
            response = try {
                httpClient.get(retryLnurlpRequest)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.FailedDependency, "Failed to fetch lnurlp response.")
                return "Failed to fetch lnurlp response."
            }
        }

        if (response.status != HttpStatusCode.OK) {
            call.respond(HttpStatusCode.FailedDependency, "Failed to fetch lnurlp response. Status: ${response.status}")
            return "Failed to fetch lnurlp response."
        }

        val lnurlpResponse = try {
            response.body<LnurlpResponse>()
        } catch (e: Exception) {
            call.application.environment.log.error("Failed to parse lnurlp response", e)
            call.respond(HttpStatusCode.FailedDependency, "Failed to parse lnurlp response.")
            return "Failed to parse lnurlp response."
        }

        val vasp2PubKeys = try {
            uma.fetchPublicKeysForVasp(receiverVasp)
        } catch (e: Exception) {
            call.application.environment.log.error("Failed to fetch pubkeys", e)
            call.respond(HttpStatusCode.FailedDependency, "Failed to fetch public keys.")
            return "Failed to fetch public keys."
        }

        try {
            uma.verifyLnurlpResponseSignature(lnurlpResponse, vasp2PubKeys)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Failed to verify lnurlp response signature.")
            return "Failed to verify lnurlp response signature."
        }

        val callbackUuid = requestDataCache.saveLnurlpResponseData(lnurlpResponse, receiverId, receiverVasp)

        call.respond(
            buildJsonObject {
                putJsonArray("currencies") {
                    addAll(lnurlpResponse.currencies.map { Json.encodeToJsonElement(it) })
                }
                put("minSendSats", lnurlpResponse.minSendable)
                put("maxSendSats", lnurlpResponse.maxSendable)
                put("callbackUuid", callbackUuid)
                // You might not actually send this to a client in practice.
                put("receiverKycStatus", lnurlpResponse.compliance.kycStatus.rawValue)
            },
        )

        return "OK"
    }

    suspend fun handleClientUmaPayReq(call: ApplicationCall): String {
        val callbackUuid = call.parameters["callbackUuid"] ?: run {
            call.respond(HttpStatusCode.BadRequest, "Callback UUID not provided.")
            return "Callback UUID not provided."
        }
        val initialRequestData = requestDataCache.getLnurlpResponseData(callbackUuid) ?: run {
            call.respond(HttpStatusCode.BadRequest, "Callback UUID not found.")
            return "Callback UUID not found."
        }

        val amount = call.request.queryParameters["amount"]?.toLongOrNull()
        if (amount == null || amount <= 0) {
            call.respond(HttpStatusCode.BadRequest, "Amount invalid or not provided.")
            return "Amount invalid or not provided."
        }

        val currencyCode = call.request.queryParameters["currencyCode"]
        if (currencyCode == null) {
            call.respond(HttpStatusCode.BadRequest, "Currency code not provided.")
            return "Currency code not provided."
        }
        val currencyValid = initialRequestData.lnurlpResponse.currencies.any { it.code == currencyCode }
        if (!currencyValid) {
            call.respond(HttpStatusCode.BadRequest, "Currency code not supported.")
            return "Currency code not supported."
        }

        val vasp2PubKeys = try {
            uma.fetchPublicKeysForVasp(initialRequestData.vasp2Domain)
        } catch (e: Exception) {
            call.application.environment.log.error("Failed to fetch pubkeys", e)
            call.respond(HttpStatusCode.FailedDependency, "Failed to fetch public keys.")
            return "Failed to fetch public keys."
        }

        val payer = getPayerProfile(initialRequestData.lnurlpResponse.requiredPayerData)
        val trInfo = "Here is some fake travel rule info. It's up to you to actually implement this if needed."
        val payerUtxos = emptyList<String>()

        val payReq = try {
            uma.getPayRequest(
                receiverEncryptionPubKey = vasp2PubKeys.encryptionPubKey,
                sendingVaspPrivateKey = config.umaSigningPrivKey,
                currencyCode = currencyCode,
                amount = amount,
                payerIdentifier = payer.identifier,
                payerKycStatus = KycStatus.VERIFIED,
                utxoCallback = getUtxoCallback(call, "1234abc"),
                travelRuleInfo = trInfo,
                payerUtxos = payerUtxos,
                payerName = payer.name,
                payerEmail = payer.email,
            )
        } catch (e: Exception) {
            call.application.environment.log.error("Failed to generate payreq", e)
            call.respond(HttpStatusCode.InternalServerError, "Failed to generate payreq.")
            return "Failed to generate payreq."
        }

        val response = httpClient.post(initialRequestData.lnurlpResponse.callback) {
            contentType(ContentType.Application.Json)
            setBody(payReq)
        }

        if (response.status != HttpStatusCode.OK) {
            call.respond(HttpStatusCode.InternalServerError, "Payreq to vasp2 failed: ${response.status}")
            return "Payreq to vasp2 failed: ${response.status}"
        }

        val payReqResponse = try {
            response.body<PayReqResponse>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to parse payreq response.")
            return "Failed to parse payreq response."
        }

        // TODO(Yun): Pre-screen the UTXOs from payreqResponse.compliance.utxos

        val invoice = try {
            lightsparkClient.decodeInvoice(payReqResponse.encodedInvoice)
        } catch (e: Exception) {
            call.application.environment.log.error("Failed to decode invoice", e)
            call.respond(HttpStatusCode.InternalServerError, "Failed to decode invoice.")
            return "Failed to decode invoice."
        }

        val newCallbackId = requestDataCache.savePayReqData(
            encodedInvoice = payReqResponse.encodedInvoice,
            utxoCallback = getUtxoCallback(call, "1234abc"),
            invoiceData = invoice,
        )

        call.respond(
            buildJsonObject {
                put("encodedInvoice", payReqResponse.encodedInvoice)
                put("callbackUuid", newCallbackId)
                put("amount", Json.encodeToJsonElement(invoice.amount))
                put("conversionRate", payReqResponse.paymentInfo.multiplier)
                put("currencyCode", payReqResponse.paymentInfo.currencyCode)
            },
        )

        return "OK"
    }

    /**
     * NOTE: In a real application, you'd want to use the authentication context to pull out this information. It's not
     * actually always Alice sending the money ;-).
     */
    private fun getPayerProfile(requiredPayerData: PayerDataOptions) = PayerProfile(
        name = if (requiredPayerData.nameRequired) "Alice FakeName" else null,
        email = if (requiredPayerData.emailRequired) "alice@vasp1.com" else null,
        // Note: This is making an assumption that this is running on localhost. We should make it configurable.
        identifier = "\$alice@localhost:${System.getenv("PORT")?.toInt() ?: 8080}",
    )

    private fun getUtxoCallback(call: ApplicationCall, txId: String): String {
        val protocol = call.request.origin.scheme
        val host = call.request.host()
        val path = "/api/uma/utxoCallback?txId=${txId}"
        return "$protocol://$host$path"
    }

    suspend fun handleClientSendPayment(call: ApplicationCall): String {
        val callbackUuid = call.parameters["callbackUuid"] ?: run {
            call.respond(HttpStatusCode.BadRequest, "Callback UUID not provided.")
            return "Callback UUID not provided."
        }
        val payReqData = requestDataCache.getPayReqData(callbackUuid) ?: run {
            call.respond(HttpStatusCode.BadRequest, "Callback UUID not found.")
            return "Callback UUID not found."
        }

        if (payReqData.invoiceData.expiresAt < Clock.System.now()) {
            call.respond(HttpStatusCode.BadRequest, "Invoice expired.")
            return "Invoice expired."
        }

        if (payReqData.invoiceData.amount.originalValue <= 0) {
            call.respond(HttpStatusCode.BadRequest, "Invoice amount invalid. Uma requires positive amounts.")
            return "Invoice amount invalid."
        }

        val payment = try {
            val pendingPayment = lightsparkClient.payUmaInvoice(
                config.nodeID,
                payReqData.encodedInvoice,
                maxFeesMsats = 1_000_000L,
            )
            waitForPaymentCompletion(pendingPayment)
        } catch (e: Exception) {
            call.application.environment.log.error("Failed to pay invoice", e)
            call.respond(HttpStatusCode.InternalServerError, "Failed to pay invoice.")
            return "Failed to pay invoice."
        }

        sendPostTransactionCallback(payment, payReqData, call)

        call.respond(
            buildJsonObject {
                put("didSucceed", (payment.status == TransactionStatus.SUCCESS))
                put("paymentId", payment.id)
            },
        )

        return "OK"
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun sendPostTransactionCallback(
        payment: OutgoingPayment,
        payReqData: Vasp1PayReqData,
        call: ApplicationCall,
    ) {
        val utxos = payment.umaPostTransactionData?.map {
            UtxoWithAmount(it.utxo, it.amount.toMilliSats())
        } ?: emptyList()
        val postTxHookResponse = try {
            httpClient.post(payReqData.utxoCallback) {
                contentType(ContentType.Application.Json)
                setBody(
                    buildJsonObject {
                        putJsonArray("utxos") {
                            addAll(utxos.map { Json.encodeToJsonElement(it) })
                        }
                    },
                )
            }
        } catch (e: Exception) {
            call.errorLog("Failed to post tx hook", e)
            null
        }
        if (postTxHookResponse?.status != HttpStatusCode.OK) {
            call.errorLog("Failed to post tx hook: ${postTxHookResponse?.status}")
        }
    }

    // TODO(Jeremy): Expose payInvoiceAndAwaitCompletion in the lightspark-sdk instead.
    private suspend fun waitForPaymentCompletion(pendingPayment: OutgoingPayment): OutgoingPayment {
        var attemptsLeft = 40
        var payment = pendingPayment
        while (payment.status == TransactionStatus.PENDING && attemptsLeft-- > 0) {
            delay(250)
            payment = OutgoingPayment.getOutgoingPaymentQuery(payment.id).execute(lightsparkClient)
                ?: throw Exception("Payment not found.")
        }
        if (payment.status == TransactionStatus.PENDING) {
            throw Exception("Payment timed out.")
        }
        return payment
    }
}

fun Routing.registerVasp1Routes(vasp1: Vasp1) {
    get("/api/umalookup/{receiver}") {
        call.debugLog(vasp1.handleClientUmaLookup(call))
    }

    get("/api/umapayreq/{callbackUuid}") {
        call.debugLog(vasp1.handleClientUmaPayReq(call))
    }

    post("/api/sendpayment/{callbackUuid}") {
        call.debugLog(vasp1.handleClientSendPayment(call))
    }
}

private data class PayerProfile(
    val name: String?,
    val email: String?,
    val identifier: String,
)
