package com.lightspark.plugins

import com.lightspark.UmaConfig
import com.lightspark.Vasp1
import com.lightspark.Vasp2
import com.lightspark.debugLog
import com.lightspark.handlePubKeyRequest
import com.lightspark.registerVasp1Routes
import com.lightspark.registerVasp2Routes
import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.uma.InMemoryPublicKeyCache
import com.lightspark.sdk.uma.UmaProtocolHelper
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.routing.get
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
        registerVasp1Routes(vasp1)
        registerVasp2Routes(vasp2)

        get("/.well-known/lnurlpubkey") {
            call.debugLog(handlePubKeyRequest(call, config))
        }
    }
}
