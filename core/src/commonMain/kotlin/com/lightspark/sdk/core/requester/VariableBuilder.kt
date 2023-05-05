package com.lightspark.sdk.core.requester

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement

class VariableBuilder(val jsonSerialFormat: Json) {
    val variables = mutableMapOf<String, JsonElement>()

    fun add(name: String, value: JsonElement) {
        variables[name] = value
    }

    fun add(name: String, value: String) {
        variables[name] = JsonPrimitive(value)
    }

    fun add(name: String, value: Boolean) {
        variables[name] = JsonPrimitive(value)
    }

    fun add(name: String, value: Int) {
        variables[name] = JsonPrimitive(value)
    }

    fun add(name: String, value: Long) {
        variables[name] = JsonPrimitive(value)
    }

    inline fun <reified T> add(name: String, value: T) {
        variables[name] = jsonSerialFormat.encodeToJsonElement(value)
    }

    fun build(): JsonObject {
        return JsonObject(variables)
    }
}

fun variables(jsonSerialFormat: Json, builder: VariableBuilder.() -> Unit): JsonObject {
    val variableBuilder = VariableBuilder(jsonSerialFormat)
    variableBuilder.builder()
    return variableBuilder.build()
}
