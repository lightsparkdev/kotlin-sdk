package com.lightspark.sdk.core.requester

import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.wss
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

/**
 * Handles the connection to the GraphQL WebSocket endpoint.
 *
 * This class is responsible for:
 * - Establishing the connection and managing its lifecycle
 * - Dealing with the protocol handshake
 * - Sending and receiving messages via the correct protocol
 * - Reconnecting when the connection is lost
 * - Dispatching events to the correct subscription flows
 *
 * It is based partially on Apollo's wonderful [WebSocketNetworkTransport](https://github.com/apollographql/apollo-kotlin/blob/main/libraries/apollo-runtime/src/commonMain/kotlin/com/apollographql/apollo3/network/ws/WebSocketNetworkTransport.kt),
 * but has been modified to fit with the Lightspark SDKs.
 */
@OptIn(ExperimentalCoroutinesApi::class)
internal class WebsocketConnectionHandler(
    private val httpClient: HttpClient,
    private val url: String,
    private val jsonSerialFormat: Json,
    private val connectionPayload: suspend () -> JsonObject? = { null },
    private val extraHeaders: Map<String, String> = emptyMap(),
    private val idleTimeoutMillis: Long = 60_000L,
    private val reopenWhen: (suspend (Throwable, attempt: Long) -> Boolean)? = null,
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO.limitedParallelism(1))

    init {
        coroutineScope.launch {
            supervise(this)
        }
    }

    /**
     * The message queue read by the supervisor.
     *
     * SubscriptionFlows write [Command]s
     * The WebSocket coroutine writes [Event]s
     *
     * Use unlimited buffers so that we never have to suspend when writing a command or an event,
     * and we avoid deadlocks. This might be overkill but is most likely never going to be a problem in practice.
     */
    private val messages = Channel<Message>(Channel.UNLIMITED)

    /**
     * The SharedFlow read by SubscriptionFlows
     *
     * The Supervisor coroutine writes [Event]s
     */
    private val mutableEvents = MutableSharedFlow<Event>(0, Int.MAX_VALUE, BufferOverflow.SUSPEND)
    private val events = mutableEvents.asSharedFlow()

    private val listener = object : GraphQLWebsocketListener {
        override fun onOperationMessage(id: String, payload: JsonObject) {
            messages.trySend(OperationResponse(id, payload))
        }

        override fun onOperationError(id: String, payload: JsonObject) {
            messages.trySend(OperationError(id, payload))
        }

        override fun operationComplete(id: String) {
            messages.trySend(OperationComplete(id))
        }

        override fun onError(error: Throwable) {
            messages.trySend(NetworkError(error))
        }

        override fun onClose(code: Int, reason: String) {
            messages.trySend(Dispose)
        }
    }

    private suspend fun supervise(scope: CoroutineScope) {
        var idleJob: Job? = null
        var connectionJob: Job? = null
        var protocol: GraphQLWebsocketProtocol? = null
        var reopenAttemptCount = 0L
        val activeMessages = mutableMapOf<String, StartOperation<*>>()

        /**
         * This happens:
         * - when this coroutine receives a [Dispose] message
         * - when the idleJob completes
         * - when there is an error reading the WebSocket and this coroutine receives a [NetworkError] message
         */
        suspend fun closeProtocol() {
            protocol?.close()
            protocol = null
            connectionJob?.cancel()
            connectionJob = null
            idleJob?.cancel()
            idleJob = null
        }

        supervisorLoop@ while (true) {
            when (val message = messages.receive()) {
                is Event -> {
                    if (message is NetworkError) {
                        closeProtocol()

                        if (reopenWhen?.invoke(message.cause, reopenAttemptCount) == true) {
                            reopenAttemptCount++
                            messages.send(RestartConnection)
                        } else {
                            reopenAttemptCount = 0L
                            // forward the NetworkError downstream. Active flows will throw
                            mutableEvents.tryEmit(message)
                        }
                    } else if (message is ConnectionReEstablished) {
                        reopenAttemptCount = 0L
                        activeMessages.values.forEach {
                            // Re-queue all start messages
                            // This will restart the websocket
                            messages.trySend(it)
                        }
                    } else {
                        reopenAttemptCount = 0L
                        mutableEvents.tryEmit(message)
                    }
                }

                is Command -> {
                    if (message is Dispose) {
                        closeProtocol()
                        // Exit the loop and the coroutine scope
                        return
                    }

                    if (protocol == null) {
                        if (message is StopOperation) {
                            // A stop was received, but we don't have a connection. Ignore it
                            activeMessages.remove(message.id)
                            continue
                        }

                        try {
                            connectionJob = coroutineScope.launch {
                                httpClient.wss(
                                    urlString = url,
                                    request = {
                                        extraHeaders.forEach { (key, value) ->
                                            headers.append(key, value)
                                        }
                                        headers.append("Sec-WebSocket-Protocol", "graphql-transport-ws")
                                    },
                                ) {
                                    try {
                                        protocol = GraphQLWebsocketProtocol(
                                            webSocketSession = this,
                                            connectionPayload = connectionPayload,
                                            listener = listener,
                                            jsonSerialFormat = jsonSerialFormat,
                                        ).apply { connectionInit() }
                                    } catch (e: Exception) {
                                        protocol = null
                                        messages.send(NetworkError(e))
                                        return@wss
                                    }
                                    protocol?.run()
                                    closeProtocol()
                                }
                            }
                        } catch (e: Exception) {
                            // Error opening the websocket
                            messages.send(NetworkError(e))
                            continue
                        }
                    }

                    // Note: This is a bit of a hack. We need to wait for the protocol to be initialized by the websocket
                    // coroutine before we can send messages to it.
                    val protocolWaitInterval = 50L
                    val protocolWaitTimeLimit = 10_000L
                    var protocolWaitCount = 0L
                    while (protocol == null) {
                        if (protocolWaitCount >= protocolWaitTimeLimit) {
                            messages.send(NetworkError(Exception("Timed out waiting for protocol to be initialized")))
                            continue@supervisorLoop
                        }
                        delay(protocolWaitInterval)
                        protocolWaitCount += protocolWaitInterval
                    }

                    when (message) {
                        is StartOperation<*> -> {
                            activeMessages[message.id] = message
                            protocol!!.sendQuery(message.id, message.query)
                        }

                        is StopOperation -> {
                            activeMessages.remove(message.id)
                            protocol!!.stopQuery(message.id)
                        }

                        is RestartConnection -> {
                            messages.send(ConnectionReEstablished())
                        }

                        else -> {
                            // Other cases have been handled above
                        }
                    }

                    idleJob = if (activeMessages.isEmpty()) {
                        scope.launch {
                            delay(idleTimeoutMillis)
                            closeProtocol()
                        }
                    } else {
                        idleJob?.cancel()
                        null
                    }
                }
            }
        }
    }

    /**
     * NOTE: Intentionally avoiding doing the deserialization in this function to avoid issues with reified types and
     * inline functions. See https://kotlinlang.org/docs/inline-functions.html#restrictions-for-public-api-inline-functions.
     */
    fun execute(query: Query<*>): Flow<Lce<JsonObject>> {
        return events.onSubscription {
            messages.send(StartOperation(query.id, query))
        }.filter {
            it.id == query.id || it.id == null
        }.transformWhile {
            when (it) {
                is OperationComplete -> {
                    false
                }

                is ConnectionReEstablished -> {
                    // means we are in the process of restarting the connection
                    false
                }

                is NetworkError -> {
                    emit(it)
                    false
                }

                else -> {
                    emit(it)
                    true
                }
            }
        }.map { response ->
            when (response) {
                is OperationResponse -> {
                    val responsePayload = response.payload
                    Lce.Content(responsePayload)
                }

                is OperationError -> Lce.Error(
                    LightsparkException("Request ${response.id} failed", LightsparkErrorCode.REQUEST_FAILED),
                )
                is NetworkError -> Lce.Error(
                    LightsparkException(
                        "Network error while executing ${response.id}",
                        LightsparkErrorCode.REQUEST_FAILED,
                        response.cause,
                    ),
                )

                // Cannot happen as these events are filtered out upstream
                is ConnectionReEstablished, is OperationComplete -> error("Unexpected event $response")
            }
        }.onCompletion {
            messages.send(StopOperation(query.id))
        }
    }
}
