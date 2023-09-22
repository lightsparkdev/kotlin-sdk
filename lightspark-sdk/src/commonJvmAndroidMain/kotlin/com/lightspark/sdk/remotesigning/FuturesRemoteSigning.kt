@file:JvmName("FuturesRemoteSigning")

package com.lightspark.sdk.remotesigning

import com.lightspark.sdk.LightsparkFuturesClient
import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.webhooks.WebhookEvent
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.cancellation.CancellationException

private class FuturesExecutor(private val client: LightsparkFuturesClient) : QueryExecutor {
    override fun <T> executeQuery(query: Query<T>): QueryResult<T> = FutureResult(client.executeQuery(query))
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
@Throws(RemoteSigningException::class, CancellationException::class)
fun handleRemoteSigningEvent(
    client: LightsparkFuturesClient,
    event: WebhookEvent,
    seedBytes: ByteArray,
    validator: Validator = AlwaysSignValidator,
): CompletableFuture<String> {
    val executor = FuturesExecutor(client)
    return (handleRemoteSigningEvent(executor, event, seedBytes, validator) as FutureResult<String>).result
}
