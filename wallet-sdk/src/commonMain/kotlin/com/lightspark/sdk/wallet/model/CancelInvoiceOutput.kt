// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The Invoice that was cancelled. If the invoice was already cancelled, the same invoice is returned.
 */
@Serializable
@SerialName("CancelInvoiceOutput")
data class CancelInvoiceOutput(
    @SerialName("cancel_invoice_output_invoice")
    val invoiceId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment CancelInvoiceOutputFragment on CancelInvoiceOutput {
    type: __typename
    cancel_invoice_output_invoice: invoice {
        id
    }
}"""
    }
}
