package com.lightspark

import me.uma.protocol.Currency
import me.uma.protocol.getCurrency

// In real life, this would come from some actual exchange rate API.
private const val MSATS_PER_USD_CENT = 22883.56

fun getSatsCurrency(senderVersion: String): Currency {
    return getCurrency(
        code = "SAT",
        name = "Satoshis",
        symbol = "SAT",
        millisatoshiPerUnit = 1.0,
        minSendable = 1,
        maxSendable = 100_000_000_000, // 1 BTC
        decimals = 0,
        senderUmaVersion = senderVersion,
    )
}

fun getReceivingCurrencies(senderUmaVersion: String): List<Currency> {
    val satsCurrency = getSatsCurrency(senderUmaVersion)
    return listOf(
        getCurrency(
            code = "USD",
            name = "US Dollar",
            symbol = "$",
            millisatoshiPerUnit = MSATS_PER_USD_CENT,
            minSendable = 1,
            maxSendable = 1_000_000,
            decimals = 2,
            senderUmaVersion = senderUmaVersion,
        ),
        satsCurrency,
    )
}
