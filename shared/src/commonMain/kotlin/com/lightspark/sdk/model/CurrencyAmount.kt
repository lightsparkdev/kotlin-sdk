package com.lightspark.sdk.model

import com.lightspark.api.type.CurrencyUnit

data class CurrencyAmount(
    val balance: Long,
    val unit: CurrencyUnit,
)

fun Any.parseAsBalanceLong(): Long? {
    return when (this) {
        is String -> this.toLongOrNull()
        is Int -> this.toLong()
        is Long -> this
        else -> null
    }
}
