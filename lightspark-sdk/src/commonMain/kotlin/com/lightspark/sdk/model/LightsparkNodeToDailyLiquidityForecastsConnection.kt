// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param entities The daily liquidity forecasts for the current page of this connection.
 */
@Serializable
@SerialName("LightsparkNodeToDailyLiquidityForecastsConnection")
data class LightsparkNodeToDailyLiquidityForecastsConnection(
    @SerialName("lightspark_node_to_daily_liquidity_forecasts_connection_from_date")
    val fromDate: LocalDate,
    @SerialName("lightspark_node_to_daily_liquidity_forecasts_connection_to_date")
    val toDate: LocalDate,
    @SerialName("lightspark_node_to_daily_liquidity_forecasts_connection_direction")
    val direction: LightningPaymentDirection,
    @SerialName("lightspark_node_to_daily_liquidity_forecasts_connection_entities")
    val entities: List<DailyLiquidityForecast>,
) {
    companion object {
        const val FRAGMENT = """
fragment LightsparkNodeToDailyLiquidityForecastsConnectionFragment on LightsparkNodeToDailyLiquidityForecastsConnection {
    type: __typename
    lightspark_node_to_daily_liquidity_forecasts_connection_from_date: from_date
    lightspark_node_to_daily_liquidity_forecasts_connection_to_date: to_date
    lightspark_node_to_daily_liquidity_forecasts_connection_direction: direction
    lightspark_node_to_daily_liquidity_forecasts_connection_entities: entities {
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
    }
}"""
    }
}
