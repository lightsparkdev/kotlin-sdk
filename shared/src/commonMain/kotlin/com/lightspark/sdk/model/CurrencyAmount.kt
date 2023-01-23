package com.lightspark.sdk.model

import com.lightspark.api.type.CurrencyUnit

/**
 * A currency amount including the unit.
 */
data class CurrencyAmount(
    val amount: Long,
    val unit: CurrencyUnit,
)
