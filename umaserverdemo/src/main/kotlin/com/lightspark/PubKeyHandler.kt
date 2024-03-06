package com.lightspark

import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import me.uma.protocol.PubKeyResponse

suspend fun handlePubKeyRequest(call: ApplicationCall, config: UmaConfig): String {
    val twoWeeksFromNowMs = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14

    val response = PubKeyResponse(
        signingCert = config.umaSigningCertificate,
        encryptionCert = config.umaEncryptionCertificate,
        expirationTs = twoWeeksFromNowMs / 1000,
    )

    call.respond(response)

    return "OK"
}
