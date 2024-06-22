// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("OutgoingPaymentsForPaymentHashQueryOutput")
data class OutgoingPaymentsForPaymentHashQueryOutput(
    @SerialName("outgoing_payments_for_payment_hash_query_output_payments")
    val payments: List<OutgoingPayment>,
) {
    companion object {
        const val FRAGMENT = """
fragment OutgoingPaymentsForPaymentHashQueryOutputFragment on OutgoingPaymentsForPaymentHashQueryOutput {
    type: __typename
    outgoing_payments_for_payment_hash_query_output_payments: payments {
        id
    }
}"""
    }
}
