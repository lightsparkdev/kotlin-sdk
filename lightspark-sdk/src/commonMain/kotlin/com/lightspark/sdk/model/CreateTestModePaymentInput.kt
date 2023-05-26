// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param localNodeId The node to where you want to send the payment.
 * @param encodedInvoice The invoice you want to be paid (as defined by the BOLT11 standard).
 * @param amountMsats The amount you will be paid for this invoice, expressed in msats. It should ONLY be set when the invoice amount is zero.
 */
@Serializable
@SerialName("CreateTestModePaymentInput")
data class CreateTestModePaymentInput(

    val localNodeId: String,

    val encodedInvoice: String,

    val amountMsats: Long? = null,
) {

    companion object {
    }
}
