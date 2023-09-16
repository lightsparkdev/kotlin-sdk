// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param invoiceId The invoice the preimage belongs to.
 * @param paymentPreimage The preimage to release.
 */
@Serializable
@SerialName("ReleasePaymentPreimageInput")
data class ReleasePaymentPreimageInput(
    val invoiceId: String,
    val paymentPreimage: String,
) {
    companion object {
    }
}
