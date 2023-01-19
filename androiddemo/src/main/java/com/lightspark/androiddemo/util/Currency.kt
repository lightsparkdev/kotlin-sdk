package com.lightspark.androiddemo.util

import com.lightspark.api.type.CurrencyUnit

fun Long.asShortString(): String {
    return when {
        this < 1000 -> this.toString()
        this < 1_000_000 -> "${String.format("%.1f", this / 1000.0)}K"
        this < 1_000_000_000 -> "${String.format("%.1f", this / 1000000.0)}M"
        else -> "${String.format("%.1f", this / 1_000_000_000.0)}B"
    }
}

fun CurrencyUnit.shortName() = when(this) {
    CurrencyUnit.SATOSHI -> "SAT"
    CurrencyUnit.BITCOIN -> "BTC"
    CurrencyUnit.MILLISATOSHI -> "mSAT"
    CurrencyUnit.NANOBITCOIN -> "nBTC"
    CurrencyUnit.MICROBITCOIN -> "uBTC"
    CurrencyUnit.MILLIBITCOIN -> "mBTC"
    CurrencyUnit.UNKNOWN__ -> "???"
}