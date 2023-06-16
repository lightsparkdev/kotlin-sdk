package com.lightspark.sdk.core.requester

import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException
import io.ktor.client.plugins.websocket.ClientWebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.send
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

const val CONNECTION_INIT_TIMEOUT = 10_000L

/**
 * An implementation of https://github.com/enisdenjo/graphql-ws/blob/master/PROTOCOL.md for use with a Ktor client.
 * It can carry queries in addition to subscriptions over the websocket
 */
internal class GraphQLWebsocketProtocol(
    private val webSocketSession: ClientWebSocketSession,
    private val listener: GraphQLWebsocketListener,
    private val jsonSerialFormat: Json,
    private val connectionPayload: suspend () -> JsonObject? = { null },
) {
    suspend fun connectionInit() {
        val payload = connectionPayload()

        sendMessage {
            add("type", "connection_init")
            payload?.let { add("payload", it) }
        }
        waitForConnectionAck()
    }

    suspend fun run() {
        // TODO(Jeremy): Consider adding client-side ping.
        webSocketSession.incoming
            .receiveAsFlow()
            .catch { cause ->
                listener.onError(cause)
            }
            .map {
                jsonSerialFormat.decodeFromString(JsonObject.serializer(), it.data.decodeToString())
            }.collect {
                handleServerMessage(it)
            }
    }

    suspend fun <T> sendQuery(id: String, query: Query<T>) {
        val operationNameRegex =
            Regex("^\\s*(query|mutation|subscription)\\s+(\\w+)", RegexOption.IGNORE_CASE)
        val operationMatch = operationNameRegex.find(query.queryPayload)
        if (operationMatch == null || operationMatch.groupValues.size < 3) {
            throw LightsparkException("Invalid query payload", LightsparkErrorCode.INVALID_QUERY)
        }
        val operation = operationMatch.groupValues[2]
        // TODO(Jeremy): Handle the signing node ID.
        sendMessage {
            add("id", id)
            add("type", "subscribe")
            val payload = buildJsonObject(jsonSerialFormat) {
                add("query", query.queryPayload)
                add("variables", buildJsonObject(jsonSerialFormat, query.variableBuilder))
                add("operationName", operation)
            }
            add("payload", payload)
        }
    }

    suspend fun stopQuery(id: String) {
        sendMessage {
            add("id", id)
            add("type", "complete")
        }
    }

    suspend fun close() {
        webSocketSession.close()
    }

    private suspend fun waitForConnectionAck() {
        withTimeout(CONNECTION_INIT_TIMEOUT) {
            while (true) {
                val received = webSocketSession.incoming.receive()
                try {
                    val receivedText = received.data.decodeToString()
                    val receivedJson = jsonSerialFormat.decodeFromString<JsonObject>(
                        receivedText,
                    )
                    when (val type = receivedJson["type"]?.jsonPrimitive?.content) {
                        "connection_ack" -> return@withTimeout
                        "ping" -> sendPong()
                        else -> {
                            listener.onError(Exception("Unexpected message type: $type"))
                            return@withTimeout
                        }
                    }
                } catch (e: Exception) {
                    listener.onError(e)
                    return@withTimeout
                }
            }
        }
    }

    private suspend fun handleServerMessage(messageJson: JsonObject) {
        when (messageJson["type"]?.jsonPrimitive?.content) {
            "next" -> {
                val payload = messageJson["payload"]?.jsonObject
                val id = messageJson["id"]?.jsonPrimitive?.content
                if (id != null && payload != null) {
                    listener.onOperationMessage(id, payload)
                }
            }

            "error" -> {
                val payload = messageJson["payload"]?.jsonObject
                val id = messageJson["id"]?.jsonPrimitive?.content
                val errors = payload?.get("errors")?.jsonObject
                if (id != null && errors != null) {
                    listener.onOperationError(id, errors)
                }
            }

            "complete" -> {
                messageJson["id"]?.jsonPrimitive?.content?.let {
                    listener.operationComplete(it)
                }
            }

            "ping" -> sendPong()
            "pong" -> Unit // Nothing to do, the server acknowledged one of our pings
            else -> Unit // Unknown message
        }
    }

    private suspend fun sendPong() {
        sendMessage {
            add("type", "pong")
        }
    }

    private suspend fun sendMessage(payloadBuilder: JsonObjectBuilder.() -> Unit) {
        val jsonObjectBuilder = buildJsonObject(jsonSerialFormat, payloadBuilder)
        webSocketSession.send(jsonObjectBuilder.toString())
    }
}

internal interface GraphQLWebsocketListener {
    fun onOperationMessage(id: String, payload: JsonObject)
    fun onOperationError(id: String, payload: JsonObject)
    fun operationComplete(id: String)
    fun onError(error: Throwable)
    fun onClose(code: Int, reason: String)
}

@Suppress("unused")
enum class CloseCode(val code: Int) {
    NormalClosure(1000),
    GoingAway(1001),
    AbnormalClosure(1006),
    NoStatusReceived(1005),
    ServiceRestart(1012),
    TryAgainLater(1013),
    BadGateway(1013),


    InternalServerError(4500),
    InternalClientError(4005),
    BadRequest(4400),
    BadResponse(4004),
    Unauthorized(4401),
    Forbidden(4403),
    SubprotocolNotAcceptable(4406),
    ConnectionInitialisationTimeout(4408),
    ConnectionAcknowledgementTimeout(4504),
    SubscriberAlreadyExists(4409),
    TooManyInitialisationRequests(4429),

    Terminated(4499),
}
