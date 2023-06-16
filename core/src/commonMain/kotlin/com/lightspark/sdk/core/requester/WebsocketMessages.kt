package com.lightspark.sdk.core.requester

import kotlinx.serialization.json.JsonObject

internal sealed interface Message

internal sealed interface Command : Message
internal class StartOperation<T>(val id: String, val query: Query<T>) : Command
internal class StopOperation(val id: String) : Command
internal object RestartConnection : Command
internal object Dispose : Command

internal sealed interface Event : Message {
    /**
     * the id of the operation
     * Might be null for general errors or network errors that are broadcast to all listeners
     */
    val id: String?
}

internal class OperationResponse(override val id: String?, val payload: JsonObject) : Event
internal class OperationError(override val id: String?, val payload: Map<String, Any?>?) : Event
internal class OperationComplete(override val id: String?) : Event
internal class ConnectionReEstablished : Event {
    override val id: String? = null
}

internal class NetworkError(val cause: Throwable) : Event {
    override val id: String? = null
}
