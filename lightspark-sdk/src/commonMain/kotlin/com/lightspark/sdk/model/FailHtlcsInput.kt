// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param invoiceId The id of invoice which the pending HTLCs that need to be failed are paying for.
 * @param cancelInvoice Whether the invoice needs to be canceled after failing the htlcs. If yes, the invoice cannot be paid anymore.
 */
@Serializable
@SerialName("FailHtlcsInput")
data class FailHtlcsInput(
    val invoiceId: String,
    val cancelInvoice: Boolean,
) {
    companion object {
    }
}
