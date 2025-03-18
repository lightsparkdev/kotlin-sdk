// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param nodeId The ID of the node that will be sending the payment.
 * @param encodedOffer The Bech32 offer you want to pay (as defined by the BOLT12 standard).
 * @param timeoutSecs The timeout in seconds that we will try to make the payment.
 * @param maximumFeesMsats The maximum amount of fees that you want to pay for this payment to be sent, expressed in msats.
 * @param amountMsats The amount you will pay for this offer, expressed in msats. It should ONLY be set when the offer amount is zero.
 * @param idempotencyKey An idempotency key for this payment. If provided, it will be used to create a payment with the same idempotency key. If not provided, a new idempotency key will be generated.
 */
@Serializable
@SerialName("PayOfferInput")
data class PayOfferInput(
    val nodeId: String,
    val encodedOffer: String,
    val timeoutSecs: Int,
    val maximumFeesMsats: Long,
    val amountMsats: Long? = null,
    val idempotencyKey: String? = null,
) {
    companion object {
    }
}
