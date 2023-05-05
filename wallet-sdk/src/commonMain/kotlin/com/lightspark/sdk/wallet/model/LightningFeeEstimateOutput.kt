// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param feeEstimate The estimated fees for the payment.
 */
@Serializable
@SerialName("LightningFeeEstimateOutput")
data class LightningFeeEstimateOutput(

    @SerialName("lightning_fee_estimate_output_fee_estimate")
    val feeEstimate: CurrencyAmount,
) {

    companion object {

        const val FRAGMENT = """
fragment LightningFeeEstimateOutputFragment on LightningFeeEstimateOutput {
    type: __typename
    lightning_fee_estimate_output_fee_estimate: fee_estimate {
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
