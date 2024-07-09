// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param paymentHash The 32-byte hash of the payment preimage for which to fetch an invoice.
 */
@Serializable
@SerialName("InvoiceForPaymentHashInput")
data class InvoiceForPaymentHashInput(
    val paymentHash: String,
) {
    companion object {
    }
}
