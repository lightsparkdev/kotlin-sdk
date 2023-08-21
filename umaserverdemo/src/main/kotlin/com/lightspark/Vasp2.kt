package com.lightspark

import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.model.Invoice
import com.lightspark.sdk.uma.Currency
import com.lightspark.sdk.uma.LnurlInvoiceCreator
import com.lightspark.sdk.uma.PayRequest
import com.lightspark.sdk.uma.PayerDataOptions
import com.lightspark.sdk.uma.PubKeyResponse
import com.lightspark.sdk.uma.UmaProtocolHelper
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.origin
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.host
import io.ktor.server.request.receive
import io.ktor.server.request.uri
import io.ktor.server.response.respond
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Vasp2(
    private val config: UmaConfig,
    private val uma: UmaProtocolHelper,
) {
    suspend fun handleLnurlp(call: ApplicationCall): String {
        val username = call.parameters["username"]

        if (username == null) {
            call.respond(HttpStatusCode.BadRequest, "Username not provided.")
            return "Username not provided."
        }

        if (username != config.username) {
            call.respond(HttpStatusCode.NotFound, "Username not found.")
            return "Username not found."
        }

        val requestUrl = call.request.fullUrl()
        if (uma.isUmaLnurlpQuery(requestUrl)) {
            return handleUmaLnurlp(call)
        }
        return "Hello World!"
    }

    private suspend fun handleUmaLnurlp(call: ApplicationCall): String {
        val requestUrl = call.request.fullUrl()
        val request = try {
            uma.parseLnurlpRequest(requestUrl)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid lnurlp request.")
            return "Invalid lnurlp request."
        }

        val pubKeys = try {
            uma.fetchPublicKeysForVasp(request.vaspDomain)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Failed to fetch public keys.")
            return "Failed to fetch public keys."
        }

        try {
            require(uma.verifyUmaLnurlpQuerySignture(request, pubKeys))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid lnurlp signature.")
            return "Invalid lnurlp signature."
        }

        val response = try {
            uma.getLnurlpResponse(
                query = request,
                privateKeyBytes = config.umaSigningPrivKey,
                requiresTravelRuleInfo = true,
                callback = getCallback(call),
                encodedMetadata = getEncodedMetadata(),
                minSendableSats = 1,
                maxSendableSats = 100_000_000,
                payerDataOptions = PayerDataOptions(nameRequired = false, emailRequired = false, complianceRequired = true),
                currencyOptions = listOf(
                    Currency(
                        code = "USD",
                        name = "US Dollar",
                        symbol = "$",
                        millisatoshiPerUnit = 34_150,
                        minSendable = 1,
                        maxSendable = 10_000_000,
                    ),
                ),
                isReceiverKYCd = true,
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

        val amountParam = call.request.queryParameters["amount"]
        val amountMsats = amountParam?.toLongOrNull()
        if (amountMsats == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid or missing amount.")
            return "Invalid or missing amount."
        }

        val client = LightsparkCoroutinesClient(
            ClientConfig(serverUrl = config.clientBaseURL ?: "api.lightspark.com"),
        )
        val invoice = try {
            client.createLnurlInvoice(config.nodeID, amountMsats, getEncodedMetadata())
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
            call.respond(HttpStatusCode.BadRequest, "Invalid pay request.")
            return "Invalid pay request."
        }

        val conversionRate = 34_150L // In real life, this would come from some actual exchange rate API.
        val client = LightsparkCoroutinesClient(
            ClientConfig(serverUrl = config.clientBaseURL ?: "api.lightspark.com"),
        )

        val response = try {
            uma.getPayReqResponse(
                query = request,
                invoiceCreator = object : LnurlInvoiceCreator {
                    override suspend fun createLnurlInvoice(amountMsats: Long, metadata: String): Invoice {
                        return client.createLnurlInvoice(config.nodeID, amountMsats, metadata)
                    }
                },
                metadata = getEncodedMetadata(),
                currencyCode = "USD",
                conversionRate = conversionRate,
                // TODO(Jeremy): Actually get the UTXOs from the request.
                receiverChannelUtxos = emptyList(),
                utxoCallback = "/api/lnurl/utxocallback?txid=1234",
            )
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to create payreq response.")
            return "Failed to create payreq response."
        }

        call.respond(response)

        return "OK"
    }

    suspend fun handlePubKeyRequest(call: ApplicationCall): String {
        val twoWeeksFromNowMs = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14

        val response = PubKeyResponse(
            signingPubKey = config.umaSigningPubKey,
            encryptionPubKey = config.umaEncryptionPubKey,
            expirationTimestamp = twoWeeksFromNowMs / 1000,
        )

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

    private fun getCallback(call: ApplicationCall): String {
        val protocol = call.request.origin.scheme
        val host = call.request.host()
        val path = "/api/uma/payreq/${config.userID}"
        return "$protocol://$host$path"
    }

    private fun ApplicationRequest.fullUrl(): String {
        val host = host()
        val protocol = origin.scheme
        val path = uri
        return "$protocol://$host$path"
    }
}
