// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("InvoiceForPaymentHashOutput")
data class InvoiceForPaymentHashOutput(
    @SerialName("invoice_for_payment_hash_output_invoice")
    val invoiceId: EntityId? = null,
) {
    companion object {
        const val FRAGMENT = """
fragment InvoiceForPaymentHashOutputFragment on InvoiceForPaymentHashOutput {
    type: __typename
    invoice_for_payment_hash_output_invoice: invoice {
        id
    }
}"""
    }
}
