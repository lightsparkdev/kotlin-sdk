// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param encodedPaymentRequest The invoice you want to pay (as defined by the BOLT11 standard).
 * @param amountMsats If the invoice does not specify a payment amount, then the amount that you wish to pay, expressed in msats.
 */
@Serializable
@SerialName("LightningFeeEstimateForInvoiceInput")
data class LightningFeeEstimateForInvoiceInput(

    val encodedPaymentRequest: String,

    val amountMsats: Long? = null,
) {

    companion object {
    }
}
