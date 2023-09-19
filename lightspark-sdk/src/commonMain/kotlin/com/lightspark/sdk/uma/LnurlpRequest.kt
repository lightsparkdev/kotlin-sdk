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
 * @param isSubjectToTravelRule Indicates whether the VASP1 is a financial institution that requires travel rule information.
 * @param vaspDomain The domain of the VASP that is sending the payment. It will be used by VASP2 to fetch the public keys of VASP1.
 * @param timestamp The unix timestamp in seconds of the moment when the request was sent. Used in the signature.
 * @param umaVersion  The version of the UMA protocol that VASP1 prefers to use for this transaction. For the version
 *     negotiation flow, see https://static.swimlanes.io/87f5d188e080cb8e0494e46f80f2ae74.png
 */
data class LnurlpRequest(
    val receiverAddress: String,
    val nonce: String,
    val signature: String,
    val isSubjectToTravelRule: Boolean,
    val vaspDomain: String,
    val timestamp: Long,
    val umaVersion: String,
) {
    /**
     * Encodes the request to a URL that can be used to send the request to VASP2.
     * @throws IllegalArgumentException if the receiverAddress is not in the format of "$user@domain.com".
     */
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
                append("isSubjectToTravelRule", isSubjectToTravelRule.toString())
                append("timestamp", timestamp.toString())
                append("umaVersion", umaVersion)
            },
        ).build()
        return url.toString()
    }

    fun signedWith(signature: String) = copy(signature = signature)

    fun signablePayload() = "$receiverAddress|$nonce|$timestamp".encodeToByteArray()

    companion object {
        fun decodeFromUrl(url: String): LnurlpRequest {
            val urlBuilder = URLBuilder(url)
            if (urlBuilder.protocol != URLProtocol.HTTP && urlBuilder.protocol != URLProtocol.HTTPS) {
                throw IllegalArgumentException("Invalid URL schema: $url")
            }
            if (urlBuilder.pathSegments.size != 4
                || urlBuilder.pathSegments[1] != ".well-known"
                || urlBuilder.pathSegments[2] != "lnurlp"
            ) {
                throw IllegalArgumentException("Invalid uma request path: $url")
            }
            val port = if (urlBuilder.host == "localhost") ":${urlBuilder.port}" else ""
            val receiverAddress = "${urlBuilder.pathSegments[3]}@${urlBuilder.host}$port"
            val vaspDomain = urlBuilder.parameters["vaspDomain"]
            val nonce = urlBuilder.parameters["nonce"]
            val signature = urlBuilder.parameters["signature"]
            val isSubjectToTravelRule = urlBuilder.parameters["isSubjectToTravelRule"]?.toBoolean()
            val timestamp = urlBuilder.parameters["timestamp"]?.toLong()
            val umaVersion = urlBuilder.parameters["umaVersion"]

            if (vaspDomain == null ||
                nonce == null ||
                signature == null ||
                isSubjectToTravelRule == null ||
                timestamp == null ||
                umaVersion == null
            ) {
                throw IllegalArgumentException("Invalid URL. Missing param: $url")
            }

            if (!isVersionSupported(umaVersion)) {
                throw UnsupportedVersionException(umaVersion)
            }

            return LnurlpRequest(
                receiverAddress, nonce, signature, isSubjectToTravelRule, vaspDomain, timestamp, umaVersion,
            )
        }
    }
}

