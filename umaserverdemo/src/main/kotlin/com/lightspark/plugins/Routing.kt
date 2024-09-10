package com.lightspark.plugins

import com.lightspark.UmaConfig
import com.lightspark.Vasp1
import com.lightspark.Vasp2
import com.lightspark.debugLog
import com.lightspark.handlePubKeyRequest
import com.lightspark.isDomainLocalhost
import com.lightspark.originWithPort
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
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.datetime.Clock
import kotlinx.serialization.json.JsonObject
import me.uma.InMemoryNonceCache
import me.uma.InMemoryPublicKeyCache
import me.uma.UmaProtocolHelper
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

fun Application.configureRouting(
    config: UmaConfig,
    uma: UmaProtocolHelper = UmaProtocolHelper(InMemoryPublicKeyCache()),
    lightsparkClient: LightsparkCoroutinesClient? = null,
) {
    val client = lightsparkClient ?: LightsparkCoroutinesClient(
        ClientConfig(
            serverUrl = config.clientBaseURL ?: "api.lightspark.com",
            authProvider = AccountApiTokenAuthProvider(config.apiClientID, config.apiClientSecret),
        ),
    )
    val vasp1 = Vasp1(config, uma, client)
    val vasp2 = Vasp2(config, uma, client)

    routing {
        registerVasp1Routes(vasp1)
        registerVasp2Routes(vasp2)

        get("/.well-known/lnurlpubkey") {
            call.debugLog(handlePubKeyRequest(call, config))
        }

        get("/.well-known/uma-configuration") {
            val domain = config.vaspDomain ?: call.originWithPort()
            val scheme = if (isDomainLocalhost(domain)) "http" else "https"
            call.respond(
                HttpStatusCode.OK,
                buildJsonObject {
                    put("uma_request_endpoint", "$scheme://$domain/api/uma/request_pay_invoice")
                    put(
                        "uma_major_versions",
                        buildJsonArray {
                            add(0)
                            add(1)
                        },
                    )
                },
            )
        }

        post("/api/uma/utxoCallback") {
            val postTransactionCallback = try {
                uma.parseAsPostTransactionCallback(call.receiveText())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid utxo callback.")
                return@post
            }

            val pubKeys = try {
                uma.fetchPublicKeysForVasp(postTransactionCallback.vaspDomain)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Failed to fetch public keys. ${e.message}")
                return@post
            }

            val nonceCache = InMemoryNonceCache(Clock.System.now().epochSeconds)
            try {
                uma.verifyPostTransactionCallbackSignature(postTransactionCallback, pubKeys, nonceCache)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Failed to verify post transaction callback signature.")
                return@post
            }

            call.debugLog("Received UTXO callback: $postTransactionCallback")
            call.respond(HttpStatusCode.OK)
        }
    }
}
