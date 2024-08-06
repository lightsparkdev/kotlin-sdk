// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("IncomingPaymentsForPaymentHashQueryOutput")
data class IncomingPaymentsForPaymentHashQueryOutput(
    @SerialName("incoming_payments_for_payment_hash_query_output_payments")
    val payments: List<IncomingPayment>,
) {
    companion object {
        const val FRAGMENT = """
fragment IncomingPaymentsForPaymentHashQueryOutputFragment on IncomingPaymentsForPaymentHashQueryOutput {
    type: __typename
    incoming_payments_for_payment_hash_query_output_payments: payments {
        id
    }
}"""
    }
}
