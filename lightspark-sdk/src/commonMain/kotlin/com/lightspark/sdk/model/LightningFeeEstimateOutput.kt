// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param feeEstimateMsat The estimated fees for the payment expressed in msats.
 */
@Serializable
@SerialName("LightningFeeEstimateOutput")
data class LightningFeeEstimateOutput(

    @SerialName("lightning_fee_estimate_output_fee_estimate_msat")
    val feeEstimateMsat: Int,
) {

    companion object {
        const val FRAGMENT = """
fragment LightningFeeEstimateOutputFragment on LightningFeeEstimateOutput {
    type: __typename
    lightning_fee_estimate_output_fee_estimate_msat: fee_estimate_msat
}"""
    }
}
