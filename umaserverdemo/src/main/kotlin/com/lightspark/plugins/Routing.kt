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
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import me.uma.InMemoryNonceCache
import me.uma.InMemoryPublicKeyCache
import me.uma.UmaProtocolHelper
import kotlinx.serialization.json.JsonObject

fun Application.configureRouting(
    config: UmaConfig,
    uma: UmaProtocolHelper = UmaProtocolHelper(InMemoryPublicKeyCache()),
) {
    val client = LightsparkCoroutinesClient(
        ClientConfig(
            serverUrl = config.clientBaseURL ?: "api.lightspark.com",
            authProvider = AccountApiTokenAuthProvider(config.apiClientID, config.apiClientSecret),
        ),
    )
    val twoHoursAgoSec = System.currentTimeMillis() / 1000 - 7200
    val nonceCache = InMemoryNonceCache(twoHoursAgoSec)
    val vasp1 = Vasp1(config, uma, client, nonceCache)
    val vasp2 = Vasp2(config, uma, client, nonceCache)

    routing {
        registerVasp1Routes(vasp1)
        registerVasp2Routes(vasp2)

        get("/.well-known/lnurlpubkey") {
            call.debugLog(handlePubKeyRequest(call, config))
        }

        get("/api/uma/utxoCallback") {
            val request = try {
                call.receive<JsonObject>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid utxo callback.")
                return@get
            }

            call.debugLog("Received UTXO callback: $request")
            call.respond(HttpStatusCode.OK)
        }
    }
}
