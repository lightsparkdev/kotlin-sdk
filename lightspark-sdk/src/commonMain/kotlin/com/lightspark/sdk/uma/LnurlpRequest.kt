package com.lightspark.sdk.uma

import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol

/**
 * The first request in the UMA protocol.
 *
 * @param receiverAddress The address of the user at VASP2 that is receiving the payment.
 * @param nonce A random string that is used to prevent replay attacks.
 * @param signature The base64-encoded signature of sha256(ReceiverAddress|Nonce|Timestamp).
 * @param trStatus Indicates whether the VASP1 is a financial institution that requires travel rule information.
 * @param vaspDomain The domain of the VASP that is sending the payment. It will be used by VASP2 to fetch the public keys of VASP1.
 * @param timestamp The unix timestamp in seconds of the moment when the request was sent. Used in the signature.
 * @throws IllegalArgumentException if the receiverAddress is not in the format of "user@domain".
 */
data class LnurlpRequest(
    val receiverAddress: String,
    val nonce: String,
    val signature: String,
    val trStatus: Boolean,
    val vaspDomain: String,
    val timestamp: Long,
) {
    fun encodeToUrl(): String {
        val receiverAddressParts = receiverAddress.split("@")
        if (receiverAddressParts.size != 2) {
            throw IllegalArgumentException("Invalid receiverAddress: $receiverAddress")
        }
        val scheme = if (receiverAddressParts[1].startsWith("localhost:")) URLProtocol.HTTP else URLProtocol.HTTPS
        val url = URLBuilder(
            protocol = scheme,
            host = receiverAddressParts[1],
            pathSegments = "/.well-known/lnurlp/${receiverAddressParts[0]}".split("/"),
            parameters = Parameters.build {
                append("vaspDomain", vaspDomain)
                append("nonce", nonce)
                append("signature", signature)
                append("trStatus", trStatus.toString())
                append("timestamp", timestamp.toString())
            },
        ).build()
        return url.toString()
    }

    fun signablePayload() = "$receiverAddress|$nonce|$timestamp".encodeToByteArray()

    companion object {
        fun decodeFromUrl(url: String): LnurlpRequest {
            val urlBuilder = URLBuilder(url)
            val receiverAddress = "${urlBuilder.host}@${urlBuilder.pathSegments[2]}"
            val vaspDomain = urlBuilder.parameters["vaspDomain"]
            val nonce = urlBuilder.parameters["nonce"]
            val signature = urlBuilder.parameters["signature"]
            val trStatus = urlBuilder.parameters["trStatus"]?.toBoolean()
            val timestamp = urlBuilder.parameters["timestamp"]?.toLong()
            if (vaspDomain == null || nonce == null || signature == null || trStatus == null || timestamp == null) {
                throw IllegalArgumentException("Invalid URL: $url")
            }
            return LnurlpRequest(receiverAddress, nonce, signature, trStatus, vaspDomain, timestamp)
        }
    }
}

