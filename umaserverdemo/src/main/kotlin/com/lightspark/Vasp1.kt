package com.lightspark

import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.uma.LnurlpResponse
import com.lightspark.sdk.uma.UmaProtocolHelper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class Vasp1(
    private val config: UmaConfig,
    private val uma: UmaProtocolHelper,
    private val lightsparkClient: LightsparkCoroutinesClient,
) {
    private val httpClient = HttpClient()

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

        // TODO(Jeremy): Save the request info to cache.

        call.respond(
            mapOf(
                "currencies" to lnurlpResponse.currencies,
                "minSendSats" to lnurlpResponse.minSendable,
                "maxSendSats" to lnurlpResponse.maxSendable,
                "callbackUuid" to "TODO",
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
        val currencyValid = uma.getSupportedCurrencies().any { it.code == currencyCode }
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

        return "OK"
    }

    suspend fun handleClientSendPayment(call: ApplicationCall): String {
        return "OK"
    }
}
