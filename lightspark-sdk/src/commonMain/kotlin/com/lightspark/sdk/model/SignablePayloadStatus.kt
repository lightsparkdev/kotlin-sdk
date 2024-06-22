// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = SignablePayloadStatusSerializer::class)
enum class SignablePayloadStatus(
    val rawValue: String,
) {
    CREATED("CREATED"),

    SIGNED("SIGNED"),

    VALIDATION_FAILED("VALIDATION_FAILED"),

    INVALID_SIGNATURE("INVALID_SIGNATURE"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object SignablePayloadStatusSerializer :
    EnumSerializer<SignablePayloadStatus>(
        SignablePayloadStatus::class,
        { rawValue ->
            SignablePayloadStatus.values().firstOrNull { it.rawValue == rawValue } ?: SignablePayloadStatus.FUTURE_VALUE
        },
    )
