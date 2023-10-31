// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param invoiceId The unique identifier of the invoice to be signed.
 * @param signature The cryptographic signature for the invoice.
 * @param recoveryId The recovery identifier for the signature.
 */
@Serializable
@SerialName("SignInvoiceInput")
data class SignInvoiceInput(
    val invoiceId: String,
    val signature: String,
    val recoveryId: Int,
) {
    companion object {
    }
}
