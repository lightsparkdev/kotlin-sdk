// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param provider The compliance provider that is going to screen the node. You need to be a customer of the selected provider and store the API key on the Lightspark account setting page.
 * @param paymentId The Lightspark ID of the lightning payment you want to register. It can be the id of either an OutgoingPayment or an IncomingPayment.
 * @param nodePubkey The public key of the counterparty lightning node, which would be the public key of the recipient node if it is to register an outgoing payment, or the public key of the sender node if it is to register an incoming payment.
 * @param direction Indicates whether this payment is an OutgoingPayment or an IncomingPayment.
 */
@Serializable
@SerialName("RegisterPaymentInput")
data class RegisterPaymentInput(
    val provider: ComplianceProvider,
    val paymentId: String,
    val nodePubkey: String,
    val direction: PaymentDirection,
) {
    companion object {
    }
}
