package com.lightspark

import me.uma.protocol.Currency
import me.uma.protocol.CurrencyConvertible

// In real life, this would come from some actual exchange rate API.
private const val MSATS_PER_USD_CENT = 22883.56

val SATS_CURRENCY = Currency(
    code = "SAT",
    name = "Satoshis",
    symbol = "SAT",
    millisatoshiPerUnit = 1.0,
    convertible = CurrencyConvertible(
        min = 1,
        max = 100_000_000_000, // 1 BTC
    ),
    decimals = 0,
)

val RECEIVING_CURRENCIES = listOf(
    Currency(
        code = "USD",
        name = "US Dollar",
        symbol = "$",
        millisatoshiPerUnit = MSATS_PER_USD_CENT,
        convertible = CurrencyConvertible(
            min = 1,
            max = 1_000_000,
        ),
        decimals = 2,
    ),
    SATS_CURRENCY,
)
