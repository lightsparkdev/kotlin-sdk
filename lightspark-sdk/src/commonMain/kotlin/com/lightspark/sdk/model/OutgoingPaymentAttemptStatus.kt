// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum of all potential statuses of a payment attempt made from a Lightspark Node. **/
@Serializable(with = OutgoingPaymentAttemptStatusSerializer::class)
enum class OutgoingPaymentAttemptStatus(val rawValue: String) {
    IN_FLIGHT("IN_FLIGHT"),

    SUCCEEDED("SUCCEEDED"),

    FAILED("FAILED"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object OutgoingPaymentAttemptStatusSerializer :
    EnumSerializer<OutgoingPaymentAttemptStatus>(
        OutgoingPaymentAttemptStatus::class,
        { rawValue ->
            OutgoingPaymentAttemptStatus.values().firstOrNull {
                it.rawValue == rawValue
            } ?: OutgoingPaymentAttemptStatus.FUTURE_VALUE
        },
    )
