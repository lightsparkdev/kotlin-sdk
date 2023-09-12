// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param nodeId The node from where you want to send the payment.
 * @param destinationPublicKey The public key of the destination node.
 * @param timeoutSecs The timeout in seconds that we will try to make the payment.
 * @param amountMsats The amount you will send to the destination node, expressed in msats.
 * @param maximumFeesMsats The maximum amount of fees that you want to pay for this payment to be sent, expressed in msats.
 */
@Serializable
@SerialName("SendPaymentInput")
data class SendPaymentInput(
    val nodeId: String,
    val destinationPublicKey: String,
    val timeoutSecs: Int,
    val amountMsats: Long,
    val maximumFeesMsats: Long,
) {
    companion object {
    }
}
