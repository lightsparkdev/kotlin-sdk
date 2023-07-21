// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param encodedInvoice The invoice you want to be paid (as defined by the BOLT11 standard).
 * @param amountMsats The amount you will be paid for this invoice, expressed in msats. It should ONLY be set when the invoice amount is zero.
 */
@Serializable
@SerialName("CreateTestModePaymentInputWallet")
data class CreateTestModePaymentInputWallet(

    val encodedInvoice: String,

    val amountMsats: Long? = null,
) {

    companion object {
    }
}
