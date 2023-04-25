// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("ChannelFees")
data class ChannelFees(

    @SerialName("channel_fees_base_fee")
    val baseFee: CurrencyAmount? = null,
    @SerialName("channel_fees_fee_rate_per_mil")
    val feeRatePerMil: Int? = null,
) {

    companion object {

        const val FRAGMENT = """
fragment ChannelFeesFragment on ChannelFees {
    type: __typename
    channel_fees_base_fee: base_fee {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    channel_fees_fee_rate_per_mil: fee_rate_per_mil
}"""
    }
}
