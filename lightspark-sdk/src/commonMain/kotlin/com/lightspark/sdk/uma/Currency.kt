package com.lightspark.sdk.uma

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
    @SerialName("multiplier")
    val millisatoshiPerUnit: Long,
    val minSendable: Long,
    val maxSendable: Long,
)
