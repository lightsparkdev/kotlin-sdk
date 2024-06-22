// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum indicating the direction of the payment. **/
@Serializable(with = PaymentDirectionSerializer::class)
enum class PaymentDirection(
    val rawValue: String,
) {
    SENT("SENT"),

    RECEIVED("RECEIVED"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object PaymentDirectionSerializer :
    EnumSerializer<PaymentDirection>(
        PaymentDirection::class,
        { rawValue ->
            PaymentDirection.values().firstOrNull { it.rawValue == rawValue } ?: PaymentDirection.FUTURE_VALUE
        },
    )
