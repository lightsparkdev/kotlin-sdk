// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param date The date for which this forecast was generated.
 * @param direction The direction for which this forecast was generated.
 * @param amount The value of the forecast. It represents the amount of msats that we think will be moved for that specified direction, for that node, on that date.
 */
@Serializable
@SerialName("DailyLiquidityForecast")
data class DailyLiquidityForecast(
    @SerialName("daily_liquidity_forecast_date")
    val date: LocalDate,
    @SerialName("daily_liquidity_forecast_direction")
    val direction: LightningPaymentDirection,
    @SerialName("daily_liquidity_forecast_amount")
    val amount: CurrencyAmount,
) {
    companion object {
        const val FRAGMENT = """
fragment DailyLiquidityForecastFragment on DailyLiquidityForecast {
    type: __typename
    daily_liquidity_forecast_date: date
    daily_liquidity_forecast_direction: direction
    daily_liquidity_forecast_amount: amount {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
}"""
    }
}
