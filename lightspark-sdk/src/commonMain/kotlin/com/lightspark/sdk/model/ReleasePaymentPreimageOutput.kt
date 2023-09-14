// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("ReleasePaymentPreimageOutput")
data class ReleasePaymentPreimageOutput(
    @SerialName("release_payment_preimage_output_invoice")
    val invoiceId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment ReleasePaymentPreimageOutputFragment on ReleasePaymentPreimageOutput {
    type: __typename
    release_payment_preimage_output_invoice: invoice {
        id
    }
}"""
    }
}
