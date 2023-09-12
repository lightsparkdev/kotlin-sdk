// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param paymentId The payment that has been sent.
 */
@Serializable
@SerialName("SendPaymentOutput")
data class SendPaymentOutput(
    @SerialName("send_payment_output_payment")
    val paymentId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment SendPaymentOutputFragment on SendPaymentOutput {
    type: __typename
    send_payment_output_payment: payment {
        id
    }
}"""
    }
}
