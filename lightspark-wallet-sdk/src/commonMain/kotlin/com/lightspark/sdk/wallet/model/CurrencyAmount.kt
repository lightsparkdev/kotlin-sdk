// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the value and unit for an amount of currency.
 * @param originalValue The original numeric value for this CurrencyAmount.
 * @param originalUnit The original unit of currency for this CurrencyAmount.
 * @param preferredCurrencyUnit The unit of user's preferred currency.
 * @param preferredCurrencyValueRounded The rounded numeric value for this CurrencyAmount in the very base level of user's preferred currency. For example, for USD, the value will be in cents.
 * @param preferredCurrencyValueApprox The approximate float value for this CurrencyAmount in the very base level of user's preferred currency. For example, for USD, the value will be in cents.
 */
@Serializable
@SerialName("CurrencyAmount")
data class CurrencyAmount(

    @SerialName("currency_amount_original_value")
    val originalValue: Long,
    @SerialName("currency_amount_original_unit")
    val originalUnit: CurrencyUnit,
    @SerialName("currency_amount_preferred_currency_unit")
    val preferredCurrencyUnit: CurrencyUnit,
    @SerialName("currency_amount_preferred_currency_value_rounded")
    val preferredCurrencyValueRounded: Long,
    @SerialName("currency_amount_preferred_currency_value_approx")
    val preferredCurrencyValueApprox: Float,
) {

    companion object {

        const val FRAGMENT = """
fragment CurrencyAmountFragment on CurrencyAmount {
    type: __typename
    currency_amount_original_value: original_value
    currency_amount_original_unit: original_unit
    currency_amount_preferred_currency_unit: preferred_currency_unit
    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
}"""
    }
}
