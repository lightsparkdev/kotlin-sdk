package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param code The currency code (e.g., "USD", "BTC").
 * @param symbol The currency symbol (e.g., "$", "₿").
 * @param name The full name of the currency (e.g., "US Dollar").
 * @param decimals The number of decimal places for the currency.
 */
@Serializable
@SerialName("PaymentCurrencyInput")
data class PaymentCurrencyInput(
    val code: String,
    val symbol: String,
    val name: String,
    val decimals: Int
) 