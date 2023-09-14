// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("UpdateFundsRecoveryKitInput")
data class UpdateFundsRecoveryKitInput(
    val s3BucketUrl: String,
    val bitcoinWalletAddress: String,
) {
    companion object {
    }
}
