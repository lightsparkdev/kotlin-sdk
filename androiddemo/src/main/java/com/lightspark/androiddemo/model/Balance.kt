package com.lightspark.androiddemo.model

import com.lightspark.androiddemo.util.asShortString
import com.lightspark.androiddemo.util.shortName
import com.lightspark.api.type.CurrencyUnit

data class Balance(
    val balance: Long,
    val unit: CurrencyUnit,
) {
    override fun toString(): String {
        return "${balance.asShortString()} ${unit.shortName()}"
    }
}

fun Any.parseAsBalanceLong(): Long? {
    return when (this) {
        is String -> this.toLongOrNull()
        is Int -> this.toLong()
        is Long -> this
        else -> null
    }
}
