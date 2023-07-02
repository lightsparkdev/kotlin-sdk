// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param bitcoinAddress The bitcoin address where the withdrawal should be sent.
 * @param amountSats The amount you want to withdraw from this node in Satoshis. Use the special value -1 to withdrawal all funds from this node.
 */
@Serializable
@SerialName("RequestWithdrawalInput")
data class RequestWithdrawalInput(

    val bitcoinAddress: String,

    val amountSats: Long,
) {

    companion object {
    }
}
