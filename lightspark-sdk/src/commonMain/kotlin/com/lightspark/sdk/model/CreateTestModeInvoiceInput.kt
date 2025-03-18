// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param localNodeId The local node from which to create the invoice.
 * @param amountMsats The amount for which the invoice should be created, in millisatoshis. Setting the amount to 0 will allow the payer to specify an amount.
 * @param memo An optional memo to include in the invoice.
 * @param invoiceType The type of invoice to create.
 */
@Serializable
@SerialName("CreateTestModeInvoiceInput")
data class CreateTestModeInvoiceInput(
    val localNodeId: String,
    val amountMsats: Long,
    val memo: String? = null,
    val invoiceType: InvoiceType? = null,
) {
    companion object {
    }
}
