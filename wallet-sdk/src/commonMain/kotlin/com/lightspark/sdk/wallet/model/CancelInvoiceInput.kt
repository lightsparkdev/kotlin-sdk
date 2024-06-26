// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The unique identifier of the Invoice that should be cancelled. The invoice is supposed to be open, not settled and not expired.
 */
@Serializable
@SerialName("CancelInvoiceInput")
data class CancelInvoiceInput(
    val invoiceId: String,
) {
    companion object {
    }
}
