// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
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
