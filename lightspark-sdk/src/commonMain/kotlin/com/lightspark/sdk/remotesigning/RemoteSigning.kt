@file:OptIn(ExperimentalStdlibApi::class)

package com.lightspark.sdk.remotesigning

import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.crypto.RemoteSigning
import com.lightspark.sdk.crypto.internal.Network
import com.lightspark.sdk.graphql.DeclineToSignMessagesMutation
import com.lightspark.sdk.graphql.ReleaseChannelPerCommitmentSecretMutation
import com.lightspark.sdk.graphql.ReleasePaymentPreimageMutation
import com.lightspark.sdk.graphql.SignInvoiceMutation
import com.lightspark.sdk.graphql.SignMessagesMutation
import com.lightspark.sdk.graphql.UpdateChannelPerCommitmentPointMutation
import com.lightspark.sdk.graphql.UpdateNodeSharedSecretMutation
import com.lightspark.sdk.model.DeclineToSignMessagesOutput
import com.lightspark.sdk.model.ReleaseChannelPerCommitmentSecretOutput
import com.lightspark.sdk.model.ReleasePaymentPreimageOutput
import com.lightspark.sdk.model.RemoteSigningSubEventType
import com.lightspark.sdk.model.SignInvoiceOutput
import com.lightspark.sdk.model.SignMessagesOutput
import com.lightspark.sdk.model.UpdateChannelPerCommitmentPointOutput
import com.lightspark.sdk.model.UpdateNodeSharedSecretOutput
import com.lightspark.sdk.model.WebhookEventType
import com.lightspark.sdk.util.serializerFormat
import com.lightspark.sdk.webhooks.WebhookEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

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
        RemoteSigningSubEventType.GET_PER_COMMITMENT_POINT -> handleGetPerCommitmentPoint(client, event, seedBytes)
        RemoteSigningSubEventType.RELEASE_PER_COMMITMENT_SECRET ->
            releaseChannelPerCommitmentSecret(client, event, seedBytes)

        RemoteSigningSubEventType.SIGN_INVOICE -> handleSignInvoice(client, event, seedBytes)
        RemoteSigningSubEventType.DERIVE_KEY_AND_SIGN -> handleDeriveKeyAndSign(client, event, seedBytes)
        RemoteSigningSubEventType.RELEASE_PAYMENT_PREIMAGE -> handleReleasePaymentPreimage(client, event, seedBytes)
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

private suspend fun handleGetPerCommitmentPoint(
    client: LightsparkCoroutinesClient,
    event: WebhookEvent,
    seedBytes: ByteArray,
): String {
    event.assertSubEventType(RemoteSigningSubEventType.GET_PER_COMMITMENT_POINT)
    val perCommitmentPointIdx = event.data?.get("per_commitment_point_idx")?.jsonPrimitive?.longOrNull
        ?: throw RemoteSigningException("Webhook event is missing per_commitment_point_idx")
    val derivationPath = event.data["derivation_path"]?.jsonPrimitive?.content
        ?: throw RemoteSigningException("Webhook event is missing derivation_path")

    val perCommitmentPoint = try {
        RemoteSigning.getPerCommitmentPoint(
            seedBytes,
            event.bitcoinNetwork(),
            derivationPath,
            perCommitmentPointIdx,
        ).toHexString()
    } catch (e: Exception) {
        throw RemoteSigningException("Error computing per-commitment point", cause = e)
    }

    val result = try {
        client.executeQuery(
            Query(
                UpdateChannelPerCommitmentPointMutation,
                {
                    add("channel_id", event.entityId)
                    add("per_commitment_point", perCommitmentPoint)
                    add("per_commitment_point_index", perCommitmentPointIdx)
                },
            ) {
                val updateChannelPerCommitmentPointJson =
                    requireNotNull(it["update_channel_per_commitment_point"]) {
                        "Invalid response for per-commitment point update"
                    }
                serializerFormat.decodeFromJsonElement<UpdateChannelPerCommitmentPointOutput>(
                    updateChannelPerCommitmentPointJson,
                )
            },
        )
    } catch (e: Exception) {
        throw RemoteSigningException("Error updating per-commitment point", cause = e)
    }

    return "updated per-commitment point for ${result.channelId}"
}

private suspend fun releaseChannelPerCommitmentSecret(
    client: LightsparkCoroutinesClient,
    event: WebhookEvent,
    seedBytes: ByteArray,
): String {
    event.assertSubEventType(RemoteSigningSubEventType.RELEASE_PER_COMMITMENT_SECRET)
    val perCommitmentIdx = event.data?.get("per_commitment_point_idx")?.jsonPrimitive?.longOrNull
        ?: throw RemoteSigningException("Webhook event is missing per_commitment_secret_idx")
    val derivationPath = event.data["derivation_path"]?.jsonPrimitive?.content
        ?: throw RemoteSigningException("Webhook event is missing derivation_path")

    val perCommitmentSecret = try {
        RemoteSigning.releasePerCommitmentSecret(
            seedBytes,
            event.bitcoinNetwork(),
            derivationPath,
            perCommitmentIdx,
        ).toHexString()
    } catch (e: Exception) {
        throw RemoteSigningException("Error computing per-commitment secret", cause = e)
    }

    val result = try {
        client.executeQuery(
            Query(
                ReleaseChannelPerCommitmentSecretMutation,
                {
                    add("channel_id", event.entityId)
                    add("per_commitment_secret", perCommitmentSecret)
                    add("per_commitment_index", perCommitmentIdx)
                },
            ) {
                val releaseChannelPerCommitmentSecretJson =
                    requireNotNull(it["release_channel_per_commitment_secret"]) {
                        "Invalid response for per-commitment secret release"
                    }
                serializerFormat.decodeFromJsonElement<ReleaseChannelPerCommitmentSecretOutput>(
                    releaseChannelPerCommitmentSecretJson,
                )
            },
        )
    } catch (e: Exception) {
        throw RemoteSigningException("Error releasing per-commitment secret", cause = e)
    }

    return "released per-commitment secret for ${result.channelId}"
}

private suspend fun handleSignInvoice(
    client: LightsparkCoroutinesClient,
    event: WebhookEvent,
    seedBytes: ByteArray,
): String {
    event.assertSubEventType(RemoteSigningSubEventType.SIGN_INVOICE)
    val invoiceId = event.data?.get("invoice_id")?.jsonPrimitive?.content
        ?: throw RemoteSigningException("Webhook event is missing invoice_id")
    val payreqHash = event.data["payreq_hash"]?.jsonPrimitive?.content?.hexToByteArray()
        ?: throw RemoteSigningException("Webhook event is missing payreq_hash")

    val invoiceSignature = try {
        RemoteSigning.signInvoiceHash(seedBytes, event.bitcoinNetwork(), payreqHash)
    } catch (e: Exception) {
        throw RemoteSigningException("Error signing invoice", cause = e)
    }

    val result = try {
        client.executeQuery(
            Query(
                SignInvoiceMutation,
                {
                    add("invoice_id", invoiceId)
                    add("signature", invoiceSignature.signature.toHexString())
                    add("recovery_id", invoiceSignature.recoveryId)
                },
            ) {
                val signInvoiceOutputJson =
                    requireNotNull(it["sign_invoice"]) { "Invalid response for invoice signature" }
                serializerFormat.decodeFromJsonElement<SignInvoiceOutput>(signInvoiceOutputJson)
            },
        )
    } catch (e: Exception) {
        throw RemoteSigningException("Error updating invoice signature", cause = e)
    }

    return "updated invoice signature for ${result.invoiceId}"
}

private suspend fun handleDeriveKeyAndSign(
    client: LightsparkCoroutinesClient,
    event: WebhookEvent,
    seedBytes: ByteArray,
): String {
    event.assertSubEventType(RemoteSigningSubEventType.DERIVE_KEY_AND_SIGN)
    val signingJobs: List<SigningJob> = event.data?.get("signing_jobs")?.jsonArray?.let {
        serializerFormat.decodeFromJsonElement(it)
    } ?: throw RemoteSigningException("Webhook event is missing signing_jobs")

    val signatures = signingJobs.map { signingJob ->
        val signature = signMessage(signingJob, seedBytes, event.bitcoinNetwork())
        SignatureResponse(signingJob.id, signature)
    }

    val result = try {
        client.executeQuery(
            Query(
                SignMessagesMutation,
                {
                    add("signatures", serializerFormat.encodeToString(signatures))
                },
            ) {
                val signMessagesOutputJson =
                    requireNotNull(it["sign_messages"]) { "Invalid response for message signatures" }
                serializerFormat.decodeFromJsonElement<SignMessagesOutput>(signMessagesOutputJson)
            },
        )
    } catch (e: Exception) {
        throw RemoteSigningException("Error updating message signatures", cause = e)
    }

    return "signed message: ${result.signedPayloads.map { it.id }}"
}

private fun signMessage(signingJob: SigningJob, seedBytes: ByteArray, bitcoinNetwork: Network): String {
    val signature = try {
        RemoteSigning.signMessage(
            signingJob.message.hexToByteArray(),
            seedBytes,
            bitcoinNetwork,
            signingJob.derivationPath,
            signingJob.isRaw,
            signingJob.mulTweak?.hexToByteArray(),
            signingJob.addTweak?.hexToByteArray(),
        )
    } catch (e: Exception) {
        throw RemoteSigningException("Error signing message", cause = e)
    }

    return signature.toHexString()
}

private suspend fun handleReleasePaymentPreimage(
    client: LightsparkCoroutinesClient,
    event: WebhookEvent,
    seedBytes: ByteArray,
): String {
    event.assertSubEventType(RemoteSigningSubEventType.RELEASE_PAYMENT_PREIMAGE)
    val invoiceId = event.data?.get("invoice_id")?.jsonPrimitive?.content
        ?: throw RemoteSigningException("Webhook event is missing invoice_id")
    val preimageNonce = event.data["preimage_nonce"]?.jsonPrimitive?.content?.hexToByteArray()
        ?: throw RemoteSigningException("Webhook event is missing preimage_nonce")

    val preimageHex = try {
        RemoteSigning.generatePreimage(seedBytes, event.bitcoinNetwork(), preimageNonce).toHexString()
    } catch (e: Exception) {
        throw RemoteSigningException("Error generating payment preimage", cause = e)
    }

    val result = try {
        client.executeQuery(
            Query(
                ReleasePaymentPreimageMutation,
                {
                    add("invoice_id", invoiceId)
                    add("payment_preimage", preimageHex)
                },
            ) {
                val releasePaymentPreimageJson =
                    requireNotNull(it["release_payment_preimage"]) { "Invalid response for payment preimage release" }
                serializerFormat.decodeFromJsonElement<ReleasePaymentPreimageOutput>(releasePaymentPreimageJson)
            },
        )
    } catch (e: Exception) {
        throw RemoteSigningException("Error releasing payment preimage", cause = e)
    }

    return "released payment preimage for ${result.invoiceId}"
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
