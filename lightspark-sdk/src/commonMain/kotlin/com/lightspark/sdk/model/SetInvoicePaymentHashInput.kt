// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param invoiceId The invoice that needs to be updated.
 * @param paymentHash The 32-byte hash of the payment preimage.
 * @param preimageNonce The 32-byte nonce used to generate the invoice preimage if applicable. It will later be included in RELEASE_PAYMENT_PREIMAGE webhook to help recover the raw preimage.
 */
@Serializable
@SerialName("SetInvoicePaymentHashInput")
data class SetInvoicePaymentHashInput(
    val invoiceId: String,
    val paymentHash: String,
    val preimageNonce: String? = null,
) {
    companion object {
    }
}
