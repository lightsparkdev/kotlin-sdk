// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = RoutingTransactionFailureReasonSerializer::class)
enum class RoutingTransactionFailureReason(val rawValue: String) {

    INCOMING_LINK_FAILURE("INCOMING_LINK_FAILURE"),

    OUTGOING_LINK_FAILURE("OUTGOING_LINK_FAILURE"),

    FORWARDING_FAILURE("FORWARDING_FAILURE"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object RoutingTransactionFailureReasonSerializer :
    EnumSerializer<RoutingTransactionFailureReason>(
        RoutingTransactionFailureReason::class,
        { rawValue ->
            RoutingTransactionFailureReason.values().firstOrNull {
                it.rawValue == rawValue
            } ?: RoutingTransactionFailureReason.FUTURE_VALUE
        },
    )
