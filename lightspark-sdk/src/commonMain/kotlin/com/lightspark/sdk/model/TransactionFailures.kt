// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This object represents payment failures associated with your Lightspark Node.
 */
@Serializable
@SerialName("TransactionFailures")
data class TransactionFailures(
    val paymentFailures: List<PaymentFailureReason>? = null,
    val routingTransactionFailures: List<RoutingTransactionFailureReason>? = null,
) {
    companion object {
    }
}
