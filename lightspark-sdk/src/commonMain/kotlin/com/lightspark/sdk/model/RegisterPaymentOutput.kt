// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("RegisterPaymentOutput")
data class RegisterPaymentOutput(
    @SerialName("register_payment_output_payment")
    val paymentId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment RegisterPaymentOutputFragment on RegisterPaymentOutput {
    type: __typename
    register_payment_output_payment: payment {
        id
    }
}"""
    }
}
