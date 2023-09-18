@file:OptIn(ExperimentalStdlibApi::class)

package com.lightspark.sdk.remotesigning

import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.crypto.RemoteSigning
import com.lightspark.sdk.crypto.internal.Network
import com.lightspark.sdk.graphql.DeclineToSignMessagesMutation
import com.lightspark.sdk.graphql.UpdateNodeSharedSecretMutation
import com.lightspark.sdk.model.DeclineToSignMessagesOutput
import com.lightspark.sdk.model.RemoteSigningSubEventType
import com.lightspark.sdk.model.UpdateNodeSharedSecretOutput
import com.lightspark.sdk.model.WebhookEventType
import com.lightspark.sdk.util.serializerFormat
import com.lightspark.sdk.webhooks.WebhookEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

suspend fun handleRemoteSigningEvent(
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

    return when (subEventType) {
        RemoteSigningSubEventType.ECDH -> handleEcdh(client, event, seedBytes)
        RemoteSigningSubEventType.GET_PER_COMMITMENT_POINT -> TODO()
        RemoteSigningSubEventType.RELEASE_PER_COMMITMENT_SECRET -> TODO()
        RemoteSigningSubEventType.SIGN_INVOICE -> TODO()
        RemoteSigningSubEventType.DERIVE_KEY_AND_SIGN -> TODO()
        RemoteSigningSubEventType.RELEASE_PAYMENT_PREIMAGE -> TODO()
        RemoteSigningSubEventType.REQUEST_INVOICE_PAYMENT_HASH -> TODO()
        RemoteSigningSubEventType.FUTURE_VALUE -> return "unsupported sub_event_type: $subEventType"
    }
}

private suspend fun declineToSignMessages(client: LightsparkCoroutinesClient, event: WebhookEvent): String {
    val eventData = event.data ?: throw RemoteSigningException("Webhook event is missing data")
    val signingJobs: List<SigningJob> = eventData["signing_jobs"]?.jsonArray?.let {
        serializerFormat.decodeFromJsonElement(it)
    } ?: throw RemoteSigningException("Webhook event is missing signing_jobs")

    val payloadIds = signingJobs.map { it.id }
    val result = try {
        client.executeQuery(
            Query(
                DeclineToSignMessagesMutation,
                {
                    add("payload_ids", serializerFormat.encodeToString(payloadIds))
                },
            ) {
                val declineToSignJson =
                    requireNotNull(it["decline_to_sign_messages"]) { "Invalid response for signature rejection" }
                serializerFormat.decodeFromJsonElement<DeclineToSignMessagesOutput>(declineToSignJson)
            },
        )
    } catch (e: Exception) {
        throw RemoteSigningException("Error declining to sign messages", cause = e)
    }

    if (result.declinedPayloads.size != payloadIds.size) {
        throw RemoteSigningException("Invalid response for signature rejection")
    }

    return "rejected signing"
}

private suspend fun handleEcdh(client: LightsparkCoroutinesClient, event: WebhookEvent, seedBytes: ByteArray): String {
    event.assertSubEventType(RemoteSigningSubEventType.ECDH)
    val peerPubKey = event.data?.get("peer_pub_key")?.jsonPrimitive?.content
        ?: throw RemoteSigningException("Webhook event is missing peer_pub_key")

    val sharedSecret = try {
        RemoteSigning.ecdh(seedBytes, event.bitcoinNetwork(), peerPubKey).toHexString()
    } catch (e: Exception) {
        throw RemoteSigningException("Error computing ECDH shared secret", cause = e)
    }

    val result = client.executeQuery(
        Query(
            UpdateNodeSharedSecretMutation,
            {
                add("node_id", event.entityId)
                add("shared_secret", sharedSecret)
            },
        ) {
            val updateNodeSharedSecretJson =
                requireNotNull(it["update_node_shared_secret"]) { "Invalid response for shared secret update" }
            serializerFormat.decodeFromJsonElement<UpdateNodeSharedSecretOutput>(updateNodeSharedSecretJson)
        },
    )

    return "updated shared secret for ${result.nodeId}"
}

private fun WebhookEvent.assertSubEventType(expectedSubEventType: RemoteSigningSubEventType) {
    if (eventType != WebhookEventType.REMOTE_SIGNING) {
        throw RemoteSigningException("Webhook event is not for remote signing: $eventType")
    }
    val subEventTypeString = data?.get("sub_event_type")
        ?: throw RemoteSigningException("Webhook event is missing sub_event_type")
    val subEventType = try {
        serializerFormat.decodeFromJsonElement<RemoteSigningSubEventType>(subEventTypeString)
    } catch (e: Exception) {
        throw RemoteSigningException("Webhook event has invalid sub_event_type: $subEventTypeString", cause = e)
    }
    if (subEventType != expectedSubEventType) {
        throw RemoteSigningException(
            "Webhook event has unexpected sub_event_type: $subEventTypeString. Expected: $expectedSubEventType",
        )
    }
}

private fun WebhookEvent.bitcoinNetwork(): Network {
    val bitcoinNetworkString = data?.get("bitcoin_network")
        ?: throw RemoteSigningException("Webhook event is missing bitcoin_network")
    return try {
        serializerFormat.decodeFromJsonElement<Network>(bitcoinNetworkString)
    } catch (e: Exception) {
        throw RemoteSigningException("Webhook event has invalid bitcoin_network: $bitcoinNetworkString", cause = e)
    }
}
