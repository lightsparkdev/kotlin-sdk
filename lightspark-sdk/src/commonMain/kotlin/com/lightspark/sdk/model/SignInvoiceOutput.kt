// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param invoiceId  The signed invoice object.
 */
@Serializable
@SerialName("SignInvoiceOutput")
data class SignInvoiceOutput(
    @SerialName("sign_invoice_output_invoice")
    val invoiceId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment SignInvoiceOutputFragment on SignInvoiceOutput {
    type: __typename
    sign_invoice_output_invoice: invoice {
        id
    }
}"""
    }
}
