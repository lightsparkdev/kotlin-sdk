package com.lightspark.sdk.model

import com.lightspark.api.type.CurrencyUnit

data class CurrencyAmount(
    val balance: Long,
    val unit: CurrencyUnit,
)
