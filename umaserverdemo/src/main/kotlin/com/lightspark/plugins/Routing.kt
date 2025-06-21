package com.lightspark.plugins

import com.lightspark.ReceivingVasp
import com.lightspark.SendingVasp
import com.lightspark.UmaConfig
import com.lightspark.debugLog
import com.lightspark.handlePubKeyRequest
import com.lightspark.isDomainLocalhost
import com.lightspark.originWithPort
import com.lightspark.registerReceivingVaspRoutes
import com.lightspark.registerSendingVaspRoutes
import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.datetime.Clock
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import me.uma.InMemoryNonceCache
import me.uma.InMemoryPublicKeyCache
import me.uma.UmaException
import me.uma.UmaProtocolHelper
import me.uma.generated.ErrorCode

fun Application.configureRouting(
    config: UmaConfig,
    uma: UmaProtocolHelper = UmaProtocolHelper(InMemoryPublicKeyCache()),
    lightsparkClient: LightsparkCoroutinesClient? = null,
) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.debugLog("Responding to exception: ${cause.message}")
            when (cause) {
                is UmaException -> {
                    call.respond(HttpStatusCode.fromValue(cause.toHttpStatusCode()), cause.toJSON())
                }
                else -> {
                    val umaException = UmaException(
                        "Internal server error: ${cause.message}",
                        ErrorCode.INTERNAL_ERROR,
                        cause,
                    )
                    call.respond(HttpStatusCode.fromValue(umaException.toHttpStatusCode()), umaException.toJSON())
                }
            }
        }
    }

    val client = lightsparkClient ?: LightsparkCoroutinesClient(
        ClientConfig(
            serverUrl = config.clientBaseURL ?: "api.lightspark.com",
            authProvider = AccountApiTokenAuthProvider(config.apiClientID, config.apiClientSecret),
        ),
    )
    val sendingVasp = SendingVasp(config, uma, client)
    val receivingVasp = ReceivingVasp(config, uma, client)

    routing {
        registerSendingVaspRoutes(sendingVasp)
        registerReceivingVaspRoutes(receivingVasp)

        get("/.well-known/lnurlpubkey") {
            call.debugLog(handlePubKeyRequest(call, config))
        }

        get("/.well-known/uma-configuration") {
            val domain = config.vaspDomain ?: call.originWithPort()
            val scheme = if (isDomainLocalhost(domain)) "http" else "https"
            call.respond(
                HttpStatusCode.OK,
                buildJsonObject {
                    put("uma_request_endpoint", "$scheme://$domain/api/uma/request_invoice_payment")
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
                throw UmaException("Failed to parse post transaction callback", ErrorCode.PARSE_UTXO_CALLBACK_ERROR, e)
            }

            val pubKeys = try {
                uma.fetchPublicKeysForVasp(postTransactionCallback.vaspDomain)
            } catch (e: Exception) {
                throw UmaException("Failed to fetch public keys", ErrorCode.COUNTERPARTY_PUBKEY_FETCH_ERROR, e)
            }

            val nonceCache = InMemoryNonceCache(Clock.System.now().epochSeconds)
            try {
                if (!uma.verifyPostTransactionCallbackSignature(postTransactionCallback, pubKeys, nonceCache)) {
                    throw UmaException("Invalid post transaction callback signature", ErrorCode.INVALID_SIGNATURE)
                }
            } catch (e: Exception) {
                if (e is UmaException) throw e
                throw UmaException(
                    "Failed to verify post transaction callback signature",
                    ErrorCode.INVALID_SIGNATURE,
                    e,
                )
            }

            call.debugLog("Received UTXO callback: $postTransactionCallback")
            call.respond(HttpStatusCode.OK)
        }
    }
}
