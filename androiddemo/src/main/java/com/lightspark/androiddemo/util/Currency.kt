package com.lightspark.androiddemo.util

import com.lightspark.sdk.server.model.CurrencyAmount
import com.lightspark.sdk.server.model.CurrencyUnit
import kotlin.math.roundToInt

data class CurrencyAmountArg(
    val value: Long,
    val unit: CurrencyUnit,
) {
    fun toMilliSats() = when (unit) {
        CurrencyUnit.SATOSHI -> value * 1000L
        CurrencyUnit.MILLISATOSHI -> value
        CurrencyUnit.BITCOIN -> value * 100_000_000_000L
        else -> throw IllegalArgumentException("Unsupported currency unit: $unit")
    }
}

fun CurrencyAmount.displayString(): String {
    return "${preferredCurrencyValueApprox.asShortString()} ${preferredCurrencyUnit.shortName()}"
}

fun CurrencyAmountArg.displayString(): String {
    return "${value.asShortString()} ${unit.shortName()}"
}

fun zeroCurrencyAmount() = currencyAmountSats(0)

fun zeroCurrencyAmountArg() = CurrencyAmountArg(
    0,
    CurrencyUnit.SATOSHI,
)

fun currencyAmountSats(sats: Long) = CurrencyAmount(
    sats,
    CurrencyUnit.SATOSHI,
    CurrencyUnit.SATOSHI,
    sats,
    sats.toFloat(),
)

fun Long.asShortString(): String {
    // Round to a maximum of 4 decimal places
    val roundedToFourDecimals = (this * 10000).toDouble().roundToInt() / 10000.0
    return when {
        this < 1000 -> roundedToFourDecimals.toString()
        this < 1_000_000 -> "${String.format("%.1f", this / 1000.0)}K"
        this < 1_000_000_000 -> "${String.format("%.1f", this / 1000000.0)}M"
        else -> "${String.format("%.1f", this / 1_000_000_000.0)}B"
    }
}

fun Float.asShortString(): String {
    val roundedToFourDecimals = (this * 10000).toDouble().roundToInt() / 10000.0f
    return when {
        this < 1000 -> roundedToFourDecimals.toString()
        this < 1_000_000 -> "${String.format("%.1f", this / 1000.0)}K"
        this < 1_000_000_000 -> "${String.format("%.1f", this / 1000000.0)}M"
        else -> "${String.format("%.1f", this / 1_000_000_000.0)}B"
    }
}

fun CurrencyUnit.shortName() = when (this) {
    CurrencyUnit.SATOSHI -> "SAT"
    CurrencyUnit.BITCOIN -> "BTC"
    CurrencyUnit.MILLISATOSHI -> "mSAT"
    CurrencyUnit.NANOBITCOIN -> "nBTC"
    CurrencyUnit.MICROBITCOIN -> "uBTC"
    CurrencyUnit.MILLIBITCOIN -> "mBTC"
    CurrencyUnit.USD -> "USD"
    else -> "???"
}
