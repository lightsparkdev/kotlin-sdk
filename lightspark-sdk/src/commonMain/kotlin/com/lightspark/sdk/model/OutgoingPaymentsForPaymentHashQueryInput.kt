// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param paymentHash The 32-byte hash of the payment preimage for which to fetch payments
 * @param statuses An optional filter to only query outgoing payments of given statuses.
 */
@Serializable
@SerialName("OutgoingPaymentsForPaymentHashQueryInput")
data class OutgoingPaymentsForPaymentHashQueryInput(
    val paymentHash: String,
    val statuses: List<TransactionStatus>? = null,
) {
    companion object {
    }
}
