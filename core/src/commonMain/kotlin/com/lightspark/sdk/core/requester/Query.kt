package com.lightspark.sdk.core.requester

import java.util.UUID
import kotlin.jvm.JvmOverloads
import kotlinx.serialization.json.JsonObject

interface StringDeserializer<T> {
    fun deserialize(json: String): T
}

data class Query<T>(
    val queryPayload: String,
    val variableBuilder: JsonObjectBuilder.() -> Unit,
    val signingNodeId: String? = null,
    val deserializer: (JsonObject) -> T,
) {
    val id = UUID.randomUUID().toString()

    /**
     * This constructor is for convenience when calling from Java rather than Kotlin. The primary constructor is
     * simpler to use from Kotlin if possible.
     *
     * @param queryPayload The GraphQL query payload
     * @param variables The variables to be passed to the query
     * @param signingNodeId The node ID of the node that should sign the request or null if not needed.
     * @param deserializer A function that deserializes the JSON response into the desired type.
     */
    @JvmOverloads
    constructor(
        queryPayload: String,
        variables: Map<String, String>,
        deserializer: StringDeserializer<T>,
        signingNodeId: String? = null,
    ) : this(
        queryPayload,
        { variables.forEach { (name, value) -> add(name, value) } },
        signingNodeId,
        { deserializer.deserialize(it.toString()) },
    )
}
