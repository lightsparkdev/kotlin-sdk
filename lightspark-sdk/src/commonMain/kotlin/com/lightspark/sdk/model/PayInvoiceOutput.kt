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
@SerialName("PayInvoiceOutput")
data class PayInvoiceOutput(

    @SerialName("pay_invoice_output_payment")
    val paymentId: EntityId,
) {

    companion object {

        const val FRAGMENT = """
fragment PayInvoiceOutputFragment on PayInvoiceOutput {
    type: __typename
    pay_invoice_output_payment: payment {
        id
    }
}"""
    }
}
