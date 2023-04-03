package com.lightspark.sdk.requester

import com.lightspark.sdk.util.serializerFormat
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement

class VariableBuilder {
    private val variables = mutableMapOf<String, JsonElement>()

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

    internal inline fun <reified T> add(name: String, value: T) {
        variables[name] = serializerFormat.encodeToJsonElement(value)
    }

    fun build(): JsonObject {
        return JsonObject(variables)
    }
}

fun variables(builder: VariableBuilder.() -> Unit): JsonObject {
    val variableBuilder = VariableBuilder()
    variableBuilder.builder()
    return variableBuilder.build()
}
