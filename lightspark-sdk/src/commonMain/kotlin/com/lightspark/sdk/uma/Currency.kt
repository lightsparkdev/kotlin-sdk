package com.lightspark.sdk.uma

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
    @SerialName("multiplier")
    val millisatoshiPerUnit: Long,
    val minSendable: Long,
    val maxSendable: Long,
) {
    fun toJson() = Json.encodeToString(this)
}
