// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = RequestInitiatorSerializer::class)
enum class RequestInitiator(
    val rawValue: String,
) {
    CUSTOMER("CUSTOMER"),

    LIGHTSPARK("LIGHTSPARK"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object RequestInitiatorSerializer :
    EnumSerializer<RequestInitiator>(
        RequestInitiator::class,
        { rawValue ->
            RequestInitiator.values().firstOrNull { it.rawValue == rawValue } ?: RequestInitiator.FUTURE_VALUE
        },
    )
