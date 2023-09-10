// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("OutgoingPaymentsForInvoiceQueryOutput")
data class OutgoingPaymentsForInvoiceQueryOutput(
    @SerialName("outgoing_payments_for_invoice_query_output_payments")
    val payments: List<OutgoingPayment>,
) {
    companion object {
        const val FRAGMENT = """
fragment OutgoingPaymentsForInvoiceQueryOutputFragment on OutgoingPaymentsForInvoiceQueryOutput {
    type: __typename
    outgoing_payments_for_invoice_query_output_payments: payments {
        id
    }
}"""
    }
}
