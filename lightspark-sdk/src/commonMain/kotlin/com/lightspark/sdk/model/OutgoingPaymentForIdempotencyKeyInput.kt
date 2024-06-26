// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("OutgoingPaymentForIdempotencyKeyInput")
data class OutgoingPaymentForIdempotencyKeyInput(
    val idempotencyKey: String,
) {
    companion object {
    }
}
