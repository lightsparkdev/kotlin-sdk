package com.lightspark.plugins

import com.lightspark.RemoteSigningConfig
import com.lightspark.debugLog
import com.lightspark.handleWebhookRequest
import com.lightspark.sdk.ClientConfig
import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.auth.AccountApiTokenAuthProvider
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting(
    config: RemoteSigningConfig,
    client: LightsparkCoroutinesClient = LightsparkCoroutinesClient(
        ClientConfig(
            serverUrl = config.clientBaseURL ?: "api.lightspark.com",
            authProvider = AccountApiTokenAuthProvider(config.apiClientID, config.apiClientSecret),
        ),
    ),
) {
    routing {
        get("/ping") {
            call.respond(HttpStatusCode.NoContent)
        }

        get("/ln/webhooks") {
            val response = handleWebhookRequest(call, config, client)
            call.debugLog("Handled webhook event: $response")
        }
    }
}
