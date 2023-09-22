@file:OptIn(ExperimentalStdlibApi::class)
@file:JvmName("RemoteSigning")

package com.lightspark.sdk.remotesigning

import com.lightspark.sdk.LightsparkCoroutinesClient
import com.lightspark.sdk.LightsparkSyncClient
import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.crypto.RemoteSigning
import com.lightspark.sdk.crypto.internal.Network
import com.lightspark.sdk.graphql.DeclineToSignMessagesMutation
import com.lightspark.sdk.graphql.ReleaseChannelPerCommitmentSecretMutation
import com.lightspark.sdk.graphql.ReleasePaymentPreimageMutation
import com.lightspark.sdk.graphql.SetInvoicePaymentHashMutation
import com.lightspark.sdk.graphql.SignInvoiceMutation
import com.lightspark.sdk.graphql.SignMessagesMutation
import com.lightspark.sdk.graphql.UpdateChannelPerCommitmentPointMutation
import com.lightspark.sdk.graphql.UpdateNodeSharedSecretMutation
import com.lightspark.sdk.model.DeclineToSignMessagesOutput
import com.lightspark.sdk.model.ReleaseChannelPerCommitmentSecretOutput
import com.lightspark.sdk.model.ReleasePaymentPreimageOutput
import com.lightspark.sdk.model.RemoteSigningSubEventType
import com.lightspark.sdk.model.SetInvoicePaymentHashOutput
import com.lightspark.sdk.model.SignInvoiceOutput
import com.lightspark.sdk.model.SignMessagesOutput
import com.lightspark.sdk.model.UpdateChannelPerCommitmentPointOutput
import com.lightspark.sdk.model.UpdateNodeSharedSecretOutput
import com.lightspark.sdk.model.WebhookEventType
import com.lightspark.sdk.util.serializerFormat
import com.lightspark.sdk.webhooks.WebhookEvent
import java.util.concurrent.CompletableFuture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

/**
 * Handle a remote signing webhook event.
 *
 * @param client The Lightspark client to use for executing GraphQL queries when responding to the event.
 * @param event The webhook event to handle.
 * @param seedBytes The seed bytes of the node that needs to sign something. This is used to derive
 *    private keys if needed that will be used to sign the message. It will never be sent to Lightspark
 *    or leave the local machine.
 * @param validator The validator to use to determine whether to sign or reject the event.
 * @return A result string that can be used to log the result of the event handling.
 * @throws RemoteSigningException If there is an error handling the event.
 */
@JvmOverloads
@Throws(RemoteSigningException::class)
suspend fun handleRemoteSigningEvent(
    client: LightsparkCoroutinesClient,
    event: WebhookEvent,
    seedBytes: ByteArray,
    validator: Validator = AlwaysSignValidator,
): String {
    val executor = CoroutineExecutor(client)
    return (handleRemoteSigningEvent(executor, event, seedBytes, validator) as FlowResult<String>).result.single()
}

/**
 * Handle a remote signing webhook event.
 *
 * @param client The Lightspark client to use for executing GraphQL queries when responding to the event.
 * @param event The webhook event to handle.
 * @param seedBytes The seed bytes of the node that needs to sign something. This is used to derive
 *    private keys if needed that will be used to sign the message. It will never be sent to Lightspark
 *    or leave the local machine.
 * @param validator The validator to use to determine whether to sign or reject the event.
 * @return A result string that can be used to log the result of the event handling.
 * @throws RemoteSigningException If there is an error handling the event.
 */
@JvmOverloads
@Throws(RemoteSigningException::class)
fun handleRemoteSigningEventSync(
    client: LightsparkSyncClient,
    event: WebhookEvent,
    seedBytes: ByteArray,
    validator: Validator = AlwaysSignValidator,
): String =
    (handleRemoteSigningEvent(SyncExecutor(client), event, seedBytes, validator) as SyncResult<String>).result

internal sealed class QueryResult<T>
internal data class SyncResult<T>(val result: T) : QueryResult<T>()
internal data class FlowResult<T>(val result: Flow<T>) : QueryResult<T>()
internal data class FutureResult<T>(val result: CompletableFuture<T>) : QueryResult<T>()

private fun <T> QueryResult<T>.then(transformer: (T) -> String): QueryResult<String> = when (this) {
    is SyncResult -> SyncResult(transformer(result))
    is FlowResult -> FlowResult(result.map { transformer(it) })
    is FutureResult -> FutureResult(result.thenApply { transformer(it) })
}

private fun <T> QueryResult<T>.mapException(transformer: (Throwable) -> Exception): QueryResult<T> = when (this) {
    is SyncResult -> try {
        SyncResult(result)
    } catch (e: Exception) {
        throw transformer(e)
    }

    is FlowResult -> FlowResult(result.catch { throw transformer(it) })
    is FutureResult -> FutureResult(result.exceptionally { throw transformer(it) })
}

internal interface QueryExecutor {
    fun <T> executeQuery(query: Query<T>): QueryResult<T>
}

private class SyncExecutor(private val client: LightsparkSyncClient) : QueryExecutor {
    override fun <T> executeQuery(query: Query<T>): QueryResult<T> = SyncResult(client.executeQuery(query))
}

private class CoroutineExecutor(private val client: LightsparkCoroutinesClient) : QueryExecutor {
    override fun <T> executeQuery(query: Query<T>): QueryResult<T> = FlowResult(
        flow {
            emit(client.executeQuery(query))
        },
    )
}

internal fun handleRemoteSigningEvent(
    executor: QueryExecutor,
    event: WebhookEvent,
    seedBytes: ByteArray,
    validator: Validator = AlwaysSignValidator,
): QueryResult<String> {
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

    if (!validator.shouldSign(event)) {
        return declineToSignMessages(executor, event)
    }

    return when (subEventType) {
        RemoteSigningSubEventType.ECDH -> handleEcdh(executor, event, seedBytes)
        RemoteSigningSubEventType.GET_PER_COMMITMENT_POINT -> handleGetPerCommitmentPoint(executor, event, seedBytes)
        RemoteSigningSubEventType.RELEASE_PER_COMMITMENT_SECRET ->
            releaseChannelPerCommitmentSecret(executor, event, seedBytes)

        RemoteSigningSubEventType.SIGN_INVOICE -> handleSignInvoice(executor, event, seedBytes)
        RemoteSigningSubEventType.DERIVE_KEY_AND_SIGN -> handleDeriveKeyAndSign(executor, event, seedBytes)
        RemoteSigningSubEventType.RELEASE_PAYMENT_PREIMAGE -> handleReleasePaymentPreimage(executor, event, seedBytes)
        RemoteSigningSubEventType.REQUEST_INVOICE_PAYMENT_HASH ->
            handleRequestInvoicePaymentHash(executor, event, seedBytes)

        RemoteSigningSubEventType.FUTURE_VALUE -> when (executor) {
            is SyncExecutor -> SyncResult("unsupported sub_event_type: $subEventType")
            is CoroutineExecutor -> FlowResult(flowOf("unsupported sub_event_type: $subEventType"))
            else -> throw RemoteSigningException("Unsupported executor type: ${executor::class.simpleName}")
        }
    }
}

private fun declineToSignMessages(executor: QueryExecutor, event: WebhookEvent): QueryResult<String> {
    val eventData = event.data ?: throw RemoteSigningException("Webhook event is missing data")
    val signingJobs: List<SigningJob> = eventData["signing_jobs"]?.jsonArray?.let {
        serializerFormat.decodeFromJsonElement(it)
    } ?: throw RemoteSigningException("Webhook event is missing signing_jobs")

    val payloadIds = signingJobs.map { it.id }
    val wrappedResult = try {
        executor.executeQuery(
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

    fun validateResult(output: DeclineToSignMessagesOutput) {
        if (output.declinedPayloads.size != payloadIds.size) {
            throw RemoteSigningException("Invalid response for signature rejection")
        }
    }
    when (wrappedResult) {
        is SyncResult -> validateResult(wrappedResult.result)
        is FlowResult -> wrappedResult.result.onEach { validateResult(it) }
        is FutureResult -> wrappedResult.result.thenAccept { validateResult(it) }
    }

    return wrappedResult.then { "rejected signing" }.mapException { e ->
        RemoteSigningException("Error declining to sign messages", cause = e)
    }
}

private fun handleEcdh(executor: QueryExecutor, event: WebhookEvent, seedBytes: ByteArray): QueryResult<String> {
    event.assertSubEventType(RemoteSigningSubEventType.ECDH)
    val peerPubKey = event.data?.get("peer_pub_key")?.jsonPrimitive?.content
        ?: throw RemoteSigningException("Webhook event is missing peer_pub_key")

    val sharedSecret = try {
        RemoteSigning.ecdh(seedBytes, event.bitcoinNetwork(), peerPubKey).toHexString()
    } catch (e: Exception) {
        throw RemoteSigningException("Error computing ECDH shared secret", cause = e)
    }

    return executor.executeQuery(
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
    ).then { "updated shared secret for ${it.nodeId}" }.mapException { e ->
        RemoteSigningException("Error updating shared secret", cause = e)
    }
}

private fun handleGetPerCommitmentPoint(
    executor: QueryExecutor,
    event: WebhookEvent,
    seedBytes: ByteArray,
): QueryResult<String> {
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

    return try {
        executor.executeQuery(
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
    }.then { "updated per-commitment point for ${it.channelId}" }.mapException { e ->
        RemoteSigningException("Error updating per-commitment point", cause = e)
    }
}

private fun releaseChannelPerCommitmentSecret(
    executor: QueryExecutor,
    event: WebhookEvent,
    seedBytes: ByteArray,
): QueryResult<String> {
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

    return try {
        executor.executeQuery(
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
    }.then { "released per-commitment secret for ${it.channelId}" }.mapException { e ->
        RemoteSigningException("Error releasing per-commitment secret", cause = e)
    }
}

private fun handleSignInvoice(
    executor: QueryExecutor,
    event: WebhookEvent,
    seedBytes: ByteArray,
): QueryResult<String> {
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

    return try {
        executor.executeQuery(
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
    }.then { "updated invoice signature for ${it.invoiceId}" }.mapException {
        RemoteSigningException("Error updating invoice signature", cause = it)
    }
}

private fun handleDeriveKeyAndSign(
    executor: QueryExecutor,
    event: WebhookEvent,
    seedBytes: ByteArray,
): QueryResult<String> {
    event.assertSubEventType(RemoteSigningSubEventType.DERIVE_KEY_AND_SIGN)
    val signingJobs: List<SigningJob> = event.data?.get("signing_jobs")?.jsonArray?.let {
        serializerFormat.decodeFromJsonElement(it)
    } ?: throw RemoteSigningException("Webhook event is missing signing_jobs")

    val signatures = signingJobs.map { signingJob ->
        val signature = signMessage(signingJob, seedBytes, event.bitcoinNetwork())
        SignatureResponse(signingJob.id, signature)
    }

    return try {
        executor.executeQuery(
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
    }.then { result ->
        "signed messages: ${result.signedPayloads.map { it.id }}"
    }.mapException {
        RemoteSigningException("Error updating message signatures", cause = it)
    }
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

private fun handleReleasePaymentPreimage(
    executor: QueryExecutor,
    event: WebhookEvent,
    seedBytes: ByteArray,
): QueryResult<String> {
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

    return try {
        executor.executeQuery(
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
    }.then { "released payment preimage for ${it.invoiceId}" }.mapException { e ->
        RemoteSigningException("Error releasing payment preimage", cause = e)
    }
}

private fun handleRequestInvoicePaymentHash(
    executor: QueryExecutor,
    event: WebhookEvent,
    seedBytes: ByteArray,
): QueryResult<String> {
    event.assertSubEventType(RemoteSigningSubEventType.REQUEST_INVOICE_PAYMENT_HASH)
    val invoiceId = event.data?.get("invoice_id")?.jsonPrimitive?.content
        ?: throw RemoteSigningException("Webhook event is missing invoice_id")

    val preimageNonce = try {
        RemoteSigning.generatePreimageNonce(seedBytes, event.bitcoinNetwork())
    } catch (e: Exception) {
        throw RemoteSigningException("Error generating preimage nonce", cause = e)
    }

    val preimageHash = try {
        RemoteSigning.generatePreimageHash(seedBytes, event.bitcoinNetwork(), preimageNonce).toHexString()
    } catch (e: Exception) {
        throw RemoteSigningException("Error generating preimage hash", cause = e)
    }

    return try {
        executor.executeQuery(
            Query(
                SetInvoicePaymentHashMutation,
                {
                    add("invoice_id", invoiceId)
                    add("payment_hash", preimageHash)
                    add("preimage_nonce", preimageNonce.toHexString())
                },
            ) {
                val setInvoicePaymentHashJson =
                    requireNotNull(it["set_invoice_payment_hash"]) { "Invalid response for invoice payment hash update" }
                serializerFormat.decodeFromJsonElement<SetInvoicePaymentHashOutput>(setInvoicePaymentHashJson)
            },
        )
    } catch (e: Exception) {
        throw RemoteSigningException("Error setting invoice payment hash", cause = e)
    }.then { "updated invoice payment hash for ${it.invoiceId}" }.mapException {
        RemoteSigningException("Error setting invoice payment hash", cause = it)
    }
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
