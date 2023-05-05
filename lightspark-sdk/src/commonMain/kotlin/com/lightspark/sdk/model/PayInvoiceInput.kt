// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param nodeId The node from where you want to send the payment.
 * @param encodedInvoice The invoice you want to pay (as defined by the BOLT11 standard).
 * @param timeoutSecs The timeout in seconds that we will try to make the payment.
 * @param maximumFeesMsats The maximum amount of fees that you want to pay for this payment to be sent, expressed in msats.
 * @param amountMsats The amount you will pay for this invoice, expressed in msats. It should ONLY be set when the invoice amount is zero.
 */
@Serializable
@SerialName("PayInvoiceInput")
data class PayInvoiceInput(

    val nodeId: String,

    val encodedInvoice: String,

    val timeoutSecs: Int,

    val maximumFeesMsats: Long,

    val amountMsats: Long? = null,
) {

    companion object {
    }
}
