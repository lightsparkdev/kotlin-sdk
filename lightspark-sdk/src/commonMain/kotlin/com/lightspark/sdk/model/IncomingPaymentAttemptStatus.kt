// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum that enumerates all potential statuses for an incoming payment attempt. **/
@Serializable(with = IncomingPaymentAttemptStatusSerializer::class)
enum class IncomingPaymentAttemptStatus(val rawValue: String) {

    ACCEPTED("ACCEPTED"),

    SETTLED("SETTLED"),

    CANCELED("CANCELED"),

    UNKNOWN("UNKNOWN"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object IncomingPaymentAttemptStatusSerializer :
    EnumSerializer<IncomingPaymentAttemptStatus>(
        IncomingPaymentAttemptStatus::class,
        { rawValue ->
            IncomingPaymentAttemptStatus.values().firstOrNull {
                it.rawValue == rawValue
            } ?: IncomingPaymentAttemptStatus.FUTURE_VALUE
        },
    )
