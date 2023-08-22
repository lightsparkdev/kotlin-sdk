package com.lightspark

import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.uma.LnurlpResponse
import com.lightspark.sdk.uma.PayReqResponse
import com.lightspark.sdk.uma.PayerDataOptions
import com.lightspark.sdk.uma.UmaProtocolHelper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class Vasp1(
    private val config: UmaConfig,
    private val uma: UmaProtocolHelper,
    private val lightsparkClient: LightsparkCoroutinesClient,
) {
    private val httpClient = HttpClient()
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
            trStatus = true,
        )

        val response = httpClient.get(lnurlpRequest)
        if (response == null) {
            call.respond(HttpStatusCode.FailedDependency, "Failed to fetch lnurlp response.")
            return "Failed to fetch lnurlp response."
        }

        if (response.status != HttpStatusCode.OK) {
            call.respond(HttpStatusCode.FailedDependency, "Failed to fetch lnurlp response.")
            return "Failed to fetch lnurlp response."
        }

        val lnurlpResponse = try {
            response.body<LnurlpResponse>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.FailedDependency, "Failed to parse lnurlp response.")
            return "Failed to parse lnurlp response."
        }

        val vasp2PubKey = try {
            uma.fetchPublicKeysForVasp(receiverVasp)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.FailedDependency, "Failed to fetch public keys.")
            return "Failed to fetch public keys."
        }

        try {
            uma.verifyLnurlpResponseSignature(lnurlpResponse, vasp2PubKey)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Failed to verify lnurlp response signature.")
            return "Failed to verify lnurlp response signature."
        }

        val callbackUuid = requestDataCache.saveLnurlpResponseData(lnurlpResponse, receiverId, receiverVasp)

        call.respond(
            mapOf(
                "currencies" to lnurlpResponse.currencies,
                "minSendSats" to lnurlpResponse.minSendable,
                "maxSendSats" to lnurlpResponse.maxSendable,
                "callbackUuid" to callbackUuid,
                // You might not actually send this to a client in practice.
                "isReceiverKYCd" to lnurlpResponse.compliance.isKYCd,
            ),
        )

        return "OK"
    }

    suspend fun handleClientUmaPayReq(call: ApplicationCall): String {
        val callbackUuid = call.parameters["callbackUuid"]
        if (callbackUuid == null) {
            call.respond(HttpStatusCode.BadRequest, "Callback UUID not provided.")
            return "Callback UUID not provided."
        }
        val initialRequestData = requestDataCache.getLnurlpResponseData(callbackUuid) ?: run {
            call.respond(HttpStatusCode.BadRequest, "Callback UUID not found.")
            return "Callback UUID not found."
        }

        val amount = call.request.queryParameters["amount"]?.let { it.toLongOrNull() }
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
            call.respond(HttpStatusCode.FailedDependency, "Failed to fetch public keys.")
            return "Failed to fetch public keys."
        }

        val payer = getPayerProfile(initialRequestData.lnurlpResponse.requiredPayerData)
        val trInfo = "Here is some fake travel rule info. It's up to you to actually implement this if needed."
        val payerUtxos = emptyList<String>()
        val utxoCallback = "/api/lnurl/utxocallback?txid=1234"

        val payReq = try {
            uma.getPayRequest(
                receiverEncryptionPubKey = vasp2PubKeys.encryptionPubKey,
                sendingVaspPrivateKey = config.umaSigningPrivKey,
                currencyCode = currencyCode,
                amount = amount,
                payerIdentifier = payer.identifier,
                isPayerKYCd = true,
                utxoCallback = utxoCallback,
                payerUtxos = payerUtxos,
                payerName = payer.name,
                payerEmail = payer.email,
            )
        } catch (e: Exception) {
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

        // TODO(Yun): Pre-screen the UTXOs from payreqResponse.compliance.ytxos

        val invoice = try {
            lightsparkClient.decodeInvoice(payReqResponse.encodedInvoice)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to decode invoice.")
            return "Failed to decode invoice."
        }

        val newCallbackId = requestDataCache.savePayReqData(
            encodedInvoice = payReqResponse.encodedInvoice,
            utxoCallback = utxoCallback,
            invoiceData = invoice,
        )

        call.respond(
            mapOf(
                "encodedInvoice" to payReqResponse.encodedInvoice,
                "callbackUuid" to newCallbackId,
                "amount" to invoice.amount,
                "conversionRate" to payReqResponse.paymentInfo.multiplier,
                "currencyCode" to payReqResponse.paymentInfo.currencyCode,
            ),
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
        identifier = "alice",
    )

    suspend fun handleClientSendPayment(call: ApplicationCall): String {
        return "OK"
    }
}

private data class PayerProfile(
    val name: String?,
    val email: String?,
    val identifier: String,
)
