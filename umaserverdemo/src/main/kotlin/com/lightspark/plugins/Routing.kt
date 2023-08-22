package com.lightspark.plugins

import com.lightspark.UmaConfig
import com.lightspark.Vasp1
import com.lightspark.Vasp2
import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.uma.InMemoryPublicKeyCache
import com.lightspark.sdk.uma.UmaProtocolHelper
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRouting(config: UmaConfig) {
    val pubKeyCache = InMemoryPublicKeyCache()
    val uma = UmaProtocolHelper(pubKeyCache)
    val client = LightsparkCoroutinesClient(
        ClientConfig(serverUrl = config.clientBaseURL ?: "api.lightspark.com"),
    )
    val vasp1 = Vasp1(config, uma, client)
    val vasp2 = Vasp2(config, uma)

    routing {
        // VASP1 Routes:
        get("/api/umalookup/:receiver") {
            call.debugLog(vasp1.handleClientUmaLookup(call))
        }

        get("/api/umapayreq/:callbackUuid") {
            call.debugLog(vasp1.handleClientUmaPayReq(call))
        }

        post("/api/sendpayment/:callbackUuid") {
            call.debugLog(vasp1.handleClientSendPayment(call))
        }

        // End VASP1 Routes

        // VASP2 Routes:
        get("/.well-known/lnurlp/:username") {
            call.debugLog(vasp2.handleLnurlp(call))
        }

        get("/api/uma/payreq/:uuid") {
            call.debugLog(vasp2.handleLnurlPayreq(call))
        }

        post("/api/uma/payreq/:uuid") {
            call.debugLog(vasp2.handleUmaPayreq(call))
        }
        // End VASP2 Routes

        // Shared:

        get("/.well-known/lnurlpubkey") {
            // It doesn't matter which vasp protocol handles this since they share a config and cache.
            call.debugLog(vasp2.handlePubKeyRequest(call))
        }
    }
}

private fun ApplicationCall.debugLog(message: String) {
    application.environment.log.debug(message)
}
