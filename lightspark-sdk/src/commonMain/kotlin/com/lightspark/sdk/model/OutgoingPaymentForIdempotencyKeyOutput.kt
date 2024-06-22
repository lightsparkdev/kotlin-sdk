// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("OutgoingPaymentForIdempotencyKeyOutput")
data class OutgoingPaymentForIdempotencyKeyOutput(
    @SerialName("outgoing_payment_for_idempotency_key_output_payment")
    val paymentId: EntityId? = null,
) {
    companion object {
        const val FRAGMENT = """
fragment OutgoingPaymentForIdempotencyKeyOutputFragment on OutgoingPaymentForIdempotencyKeyOutput {
    type: __typename
    outgoing_payment_for_idempotency_key_output_payment: payment {
        id
    }
}"""
    }
}
