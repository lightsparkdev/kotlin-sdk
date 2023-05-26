// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateTestModeInvoiceOutput")
data class CreateTestModeInvoiceOutput(

    @SerialName("create_test_mode_invoice_output_encoded_payment_request")
    val encodedPaymentRequest: String,
) {

    companion object {

        const val FRAGMENT = """
fragment CreateTestModeInvoiceOutputFragment on CreateTestModeInvoiceOutput {
    type: __typename
    create_test_mode_invoice_output_encoded_payment_request: encoded_payment_request
}"""
    }
}
