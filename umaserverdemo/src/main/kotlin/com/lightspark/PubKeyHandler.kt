package com.lightspark

import com.lightspark.sdk.uma.PubKeyResponse
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

suspend fun handlePubKeyRequest(call: ApplicationCall, config: UmaConfig): String {
    val twoWeeksFromNowMs = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14

    val response = PubKeyResponse(
        signingPubKey = config.umaSigningPubKey,
        encryptionPubKey = config.umaEncryptionPubKey,
        expirationTimestamp = twoWeeksFromNowMs / 1000,
    )

    call.respond(response)

    return "OK"
}
