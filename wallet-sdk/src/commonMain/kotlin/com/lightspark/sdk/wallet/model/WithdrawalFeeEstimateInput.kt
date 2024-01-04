// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param amountSats The amount you want to withdraw from this node in Satoshis. Use the special value -1 to withdrawal all funds from this node.
 * @param withdrawalMode The strategy that should be used to withdraw the funds from this node.
 */
@Serializable
@SerialName("WithdrawalFeeEstimateInput")
data class WithdrawalFeeEstimateInput(
    val amountSats: Long,
    val withdrawalMode: WithdrawalMode,
) {
    companion object {
    }
}
