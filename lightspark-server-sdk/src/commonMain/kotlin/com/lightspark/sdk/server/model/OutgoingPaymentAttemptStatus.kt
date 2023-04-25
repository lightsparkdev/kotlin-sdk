// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** Enum that enumerates all the possible status of an outgoing payment attempt. **/
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
