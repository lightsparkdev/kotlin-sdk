// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("FailHtlcsOutput")
data class FailHtlcsOutput(
    @SerialName("fail_htlcs_output_invoice")
    val invoiceId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment FailHtlcsOutputFragment on FailHtlcsOutput {
    type: __typename
    fail_htlcs_output_invoice: invoice {
        id
    }
}"""
    }
}
