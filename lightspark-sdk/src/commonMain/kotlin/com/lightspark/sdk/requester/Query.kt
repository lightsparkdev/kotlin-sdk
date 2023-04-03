package com.lightspark.sdk.requester

import kotlinx.serialization.json.JsonObject

data class Query<T>(
    val queryPayload: String,
    val variableBuilder: VariableBuilder.() -> Unit,
    val signingNodeId: String? = null,
    val deserializer: (JsonObject) -> T,
)
