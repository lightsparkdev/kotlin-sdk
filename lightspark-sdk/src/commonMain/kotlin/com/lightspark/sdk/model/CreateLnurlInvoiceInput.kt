// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param nodeId The node from which to create the invoice.
 * @param amountMsats The amount for which the invoice should be created, in millisatoshis.
 * @param metadataHash The SHA256 hash of the LNURL metadata payload. This will be present in the h-tag (SHA256 purpose of payment) of the resulting Bolt 11 invoice.
 */
@Serializable
@SerialName("CreateLnurlInvoiceInput")
data class CreateLnurlInvoiceInput(

    val nodeId: String,

    val amountMsats: Long,

    val metadataHash: String,
) {

    companion object {
    }
}
