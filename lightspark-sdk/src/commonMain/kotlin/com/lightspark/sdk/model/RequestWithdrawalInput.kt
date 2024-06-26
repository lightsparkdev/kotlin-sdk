// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param nodeId The node from which you'd like to make the withdrawal.
 * @param bitcoinAddress The bitcoin address where the withdrawal should be sent.
 * @param amountSats The amount you want to withdraw from this node in Satoshis. Use the special value -1 to withdrawal all funds from this node.
 * @param withdrawalMode The strategy that should be used to withdraw the funds from this node.
 * @param idempotencyKey The idempotency key of the request. The same result will be returned for the same idempotency key.
 * @param feeTarget The target of the fee that should be used when crafting the L1 transaction. You should only set `fee_target` or `sats_per_vbyte`. If neither of them is set, default value of MEDIUM will be used as `fee_target`.
 * @param satsPerVbyte A manual fee rate set in sat/vbyte that should be used when crafting the L1 transaction. You should only set `fee_target` or `sats_per_vbyte`
 */
@Serializable
@SerialName("RequestWithdrawalInput")
data class RequestWithdrawalInput(
    val nodeId: String,
    val bitcoinAddress: String,
    val amountSats: Long,
    val withdrawalMode: WithdrawalMode,
    val idempotencyKey: String? = null,
    val feeTarget: OnChainFeeTarget? = null,
    val satsPerVbyte: Int? = null,
) {
    companion object {
    }
}
