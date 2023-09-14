// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("RegisterPaymentInput")
data class RegisterPaymentInput(
    val provider: ComplianceProvider,
    val paymentId: String,
    val nodePubkey: String,
    val direction: PaymentDirection,
) {
    companion object {
    }
}
