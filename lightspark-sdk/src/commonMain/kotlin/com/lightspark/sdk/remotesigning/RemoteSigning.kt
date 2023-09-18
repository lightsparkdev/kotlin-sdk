package com.lightspark.sdk.remotesigning

import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.model.RemoteSigningSubEventType
import com.lightspark.sdk.model.WebhookEventType
import com.lightspark.sdk.util.serializerFormat
import com.lightspark.sdk.webhooks.WebhookEvent
import kotlinx.serialization.json.decodeFromJsonElement

fun handleRemoteSigningEvent(
    client: LightsparkCoroutinesClient,
    event: WebhookEvent,
    seedBytes: ByteArray,
    validator: Validator = AlwaysSignValidator,
): String {
    if (event.eventType != WebhookEventType.REMOTE_SIGNING) {
        throw RemoteSigningException("Webhook event is not for remote signing: ${event.eventType}")
    }

    val subEventTypeString = event.data?.get("sub_event_type")
        ?: throw RemoteSigningException("Webhook event is missing sub_event_type")
    val subEventType = try {
        serializerFormat.decodeFromJsonElement<RemoteSigningSubEventType>(subEventTypeString)
    } catch (e: Exception) {
        throw RemoteSigningException("Webhook event has invalid sub_event_type: $subEventTypeString", cause = e)
    }

    if (validator.shouldSign(event)) {
        return declineToSignMessages(client, event)
    }

    when (subEventType) {
        RemoteSigningSubEventType.ECDH -> TODO()
        RemoteSigningSubEventType.GET_PER_COMMITMENT_POINT -> TODO()
        RemoteSigningSubEventType.RELEASE_PER_COMMITMENT_SECRET -> TODO()
        RemoteSigningSubEventType.SIGN_INVOICE -> TODO()
        RemoteSigningSubEventType.DERIVE_KEY_AND_SIGN -> TODO()
        RemoteSigningSubEventType.RELEASE_PAYMENT_PREIMAGE -> TODO()
        RemoteSigningSubEventType.REQUEST_INVOICE_PAYMENT_HASH -> TODO()
        RemoteSigningSubEventType.FUTURE_VALUE -> TODO()
    }
}

private fun declineToSignMessages(client: LightsparkCoroutinesClient, event: WebhookEvent): String {
    return "rejected signing"
}
