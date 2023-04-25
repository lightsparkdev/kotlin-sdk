// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateInvoiceOutput")
data class CreateInvoiceOutput(

    @SerialName("create_invoice_output_invoice")
    val invoiceId: EntityId,
) {

    companion object {

        const val FRAGMENT = """
fragment CreateInvoiceOutputFragment on CreateInvoiceOutput {
    type: __typename
    create_invoice_output_invoice: invoice {
        id
    }
}"""
    }
}
