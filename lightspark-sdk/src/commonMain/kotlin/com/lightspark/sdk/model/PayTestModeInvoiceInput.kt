// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param nodeId The node from where you want to send the payment.
 * @param encodedInvoice The invoice you want to pay (as defined by the BOLT11 standard).
 * @param timeoutSecs The timeout in seconds that we will try to make the payment.
 * @param maximumFeesMsats The maximum amount of fees that you want to pay for this payment to be sent, expressed in msats.
 * @param failureReason The failure reason to trigger for the payment. If not set, pay_invoice will be called.
 * @param amountMsats The amount you will pay for this invoice, expressed in msats. It should ONLY be set when the invoice amount is zero.
 * @param idempotencyKey The idempotency key of the request. The same result will be returned for the same idempotency key.
 */
@Serializable
@SerialName("PayTestModeInvoiceInput")
data class PayTestModeInvoiceInput(
    val nodeId: String,
    val encodedInvoice: String,
    val timeoutSecs: Int,
    val maximumFeesMsats: Long,
    val failureReason: PaymentFailureReason? = null,
    val amountMsats: Long? = null,
    val idempotencyKey: String? = null,
) {
    companion object {
    }
}
