// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateInvoiceInput")
data class CreateInvoiceInput(

    val amountMsats: Long,

    val memo: String? = null,

    val invoiceType: InvoiceType? = null,
) {

    companion object {
    }
}
