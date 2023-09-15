package com.lightspark.sdk.util

import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.model.CurrencyUnit

fun CurrencyAmount.toMilliSats() = when (originalUnit) {
    CurrencyUnit.SATOSHI -> originalValue * 1000L
    CurrencyUnit.MILLISATOSHI -> originalValue
    CurrencyUnit.BITCOIN -> originalValue * 100_000_000_000L
    CurrencyUnit.MICROBITCOIN -> originalValue * 100_000L
    CurrencyUnit.MILLIBITCOIN -> originalValue * 100_000_000L
    CurrencyUnit.NANOBITCOIN -> originalValue * 100L
    else -> throw IllegalArgumentException("Unsupported currency unit: $originalUnit")
}
