// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param senderHash An optional, monthly-rotated, unique hashed identifier corresponding to the sender of the payment.
 */
@Serializable
@SerialName("PayUmaInvoiceInput")
data class PayUmaInvoiceInput(
    val nodeId: String,
    val encodedInvoice: String,
    val timeoutSecs: Int,
    val maximumFeesMsats: Long,
    val amountMsats: Long? = null,
    val idempotencyKey: String? = null,
    val senderHash: String? = null,
) {
    companion object {
    }
}
