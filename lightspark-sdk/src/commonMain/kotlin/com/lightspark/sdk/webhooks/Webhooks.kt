package com.lightspark.sdk.webhooks

import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.model.WebhookEventType
import com.lightspark.sdk.util.serializerFormat
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
data class WebhookEvent(
    @SerialName("event_id")
    val eventId: String,
    @SerialName("event_type")
    val eventType: WebhookEventType,
    @SerialName("timestamp")
    val timestamp: Instant,
    @SerialName("entity_id")
    val entityId: String,
    @SerialName("wallet_id")
    val walletId: String? = null,
    @SerialName("data")
    val data: JsonObject? = null,
)

@OptIn(ExperimentalStdlibApi::class)
@Throws(LightsparkException::class)
fun verifyAndParseWebhook(
    data: ByteArray,
    hexDigest: String,
    webhookSecret: String,
): WebhookEvent {
    val hmac = Mac.getInstance("HmacSHA256")
    val secretKey = SecretKeySpec(webhookSecret.encodeToByteArray(), "HmacSHA256")
    hmac.init(secretKey)
    hmac.update(data)
    val signature = hmac.doFinal()
    val verified = signature.contentEquals(hexDigest.hexToByteArray())
    if (!verified) {
        throw LightsparkException("Webhook signature verification failed", "webhook_signature_verification_failed")
    }
    return parseWebhook(data)
}

fun parseWebhook(data: ByteArray) =
    serializerFormat.decodeFromJsonElement<WebhookEvent>(Json.parseToJsonElement(data.decodeToString()))
