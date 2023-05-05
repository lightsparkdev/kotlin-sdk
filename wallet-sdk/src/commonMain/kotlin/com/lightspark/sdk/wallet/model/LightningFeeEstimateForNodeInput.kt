// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param destinationNodePublicKey The public key of the node that you want to pay.
 * @param amountMsats The payment amount expressed in msats.
 */
@Serializable
@SerialName("LightningFeeEstimateForNodeInput")
data class LightningFeeEstimateForNodeInput(

    val destinationNodePublicKey: String,

    val amountMsats: Long,
) {

    companion object {
    }
}
