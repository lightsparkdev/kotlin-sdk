// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param nodeId The node from which to create the invoice.
 * @param amountMsats The amount for which the invoice should be created, in millisatoshis. Setting the amount to 0 will allow the payer to specify an amount.
 * @param expirySecs The expiry of the invoice in seconds. Default value is 86400 (1 day) for AMP invoice, or 3600 (1 hour) for STANDARD invoice.
 * @param paymentHash The payment hash of the invoice. It should only be set if your node is a remote signing node. If not set, it will be requested through REMOTE_SIGNING webhooks with sub event type REQUEST_INVOICE_PAYMENT_HASH.
 * @param preimageNonce The 32-byte nonce used to generate the invoice preimage if applicable. It will later be included in RELEASE_PAYMENT_PREIMAGE webhook to help recover the raw preimage. This can only be specified when `payment_hash` is specified.
 */
@Serializable
@SerialName("CreateInvoiceInput")
data class CreateInvoiceInput(
    val nodeId: String,
    val amountMsats: Long,
    val memo: String? = null,
    val invoiceType: InvoiceType? = null,
    val expirySecs: Int? = null,
    val paymentHash: String? = null,
    val preimageNonce: String? = null,
) {
    companion object {
    }
}
