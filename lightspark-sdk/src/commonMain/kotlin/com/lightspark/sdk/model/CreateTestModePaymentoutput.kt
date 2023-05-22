// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param paymentId The payment that has been sent.
 */
@Serializable
@SerialName("CreateTestModePaymentoutput")
data class CreateTestModePaymentoutput(

    @SerialName("create_test_mode_paymentoutput_payment")
    val paymentId: EntityId,
) {

    companion object {

        const val FRAGMENT = """
fragment CreateTestModePaymentoutputFragment on CreateTestModePaymentoutput {
    type: __typename
    create_test_mode_paymentoutput_payment: payment {
        id
    }
}"""
    }
}
