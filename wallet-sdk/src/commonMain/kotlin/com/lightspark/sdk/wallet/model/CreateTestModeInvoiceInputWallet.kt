// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateTestModeInvoiceInputWallet")
data class CreateTestModeInvoiceInputWallet(
    val amountMsats: Long,
    val memo: String? = null,
    val invoiceType: InvoiceType? = null,
) {
    companion object {
    }
}
