// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
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
