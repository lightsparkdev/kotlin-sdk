// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param feeEstimate The estimated fee for the withdrawal.
 */
@Serializable
@SerialName("WithdrawalFeeEstimateOutput")
data class WithdrawalFeeEstimateOutput(
    @SerialName("withdrawal_fee_estimate_output_fee_estimate")
    val feeEstimate: CurrencyAmount,
) {
    companion object {
        const val FRAGMENT = """
fragment WithdrawalFeeEstimateOutputFragment on WithdrawalFeeEstimateOutput {
    type: __typename
    withdrawal_fee_estimate_output_fee_estimate: fee_estimate {
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
