// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = PaymentRequestStatusSerializer::class)
enum class PaymentRequestStatus(val rawValue: String) {

    OPEN("OPEN"),

    CLOSED("CLOSED"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object PaymentRequestStatusSerializer :
    EnumSerializer<PaymentRequestStatus>(
        PaymentRequestStatus::class,
        { rawValue ->
            PaymentRequestStatus.values().firstOrNull { it.rawValue == rawValue } ?: PaymentRequestStatus.FUTURE_VALUE
        },
    )
