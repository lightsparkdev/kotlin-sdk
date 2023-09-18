package com.lightspark

import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.model.WebhookEventType
import com.lightspark.sdk.remotesigning.handleRemoteSigningEvent
import com.lightspark.sdk.uma.PubKeyResponse
import com.lightspark.sdk.webhooks.SIGNATURE_HEADER
import com.lightspark.sdk.webhooks.verifyAndParseWebhook
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond

suspend fun handleWebhookRequest(
    call: ApplicationCall,
    config: RemoteSigningConfig,
    client: LightsparkCoroutinesClient
): String {
    val signature = call.request.headers[SIGNATURE_HEADER] ?: run {
        call.respond(HttpStatusCode.BadRequest, "Missing signature header.")
        return "Missing signature header."
    }
    val webhookEvent = try {
        val bodyBytes = call.receiveText().toByteArray()
        verifyAndParseWebhook(bodyBytes, signature, config.webhookSecret)
    } catch (e: Exception) {
        call.respond(HttpStatusCode.BadRequest, "Invalid webhook request.")
        return "Invalid webhook request or bad signature."
    }

    val response = when (webhookEvent.eventType) {
        WebhookEventType.REMOTE_SIGNING -> try {
            handleRemoteSigningEvent(client, webhookEvent, config.masterSeed)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Error handling remote signing event.")
            return "Error handling remote signing event."
        }
        else -> {
            call.respond(HttpStatusCode.OK)
            return "Unhandled webhook event type ${webhookEvent.eventType}."
        }
    }

    call.respond(HttpStatusCode.OK)

    return response
}
