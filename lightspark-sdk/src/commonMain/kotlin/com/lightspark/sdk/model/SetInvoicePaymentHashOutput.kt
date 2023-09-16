// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("SetInvoicePaymentHashOutput")
data class SetInvoicePaymentHashOutput(
    @SerialName("set_invoice_payment_hash_output_invoice")
    val invoiceId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment SetInvoicePaymentHashOutputFragment on SetInvoicePaymentHashOutput {
    type: __typename
    set_invoice_payment_hash_output_invoice: invoice {
        id
    }
}"""
    }
}
