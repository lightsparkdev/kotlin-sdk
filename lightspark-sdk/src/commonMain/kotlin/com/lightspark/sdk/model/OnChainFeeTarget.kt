// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = OnChainFeeTargetSerializer::class)
enum class OnChainFeeTarget(
    val rawValue: String,
) {
    /** Transaction expected to be confirmed within 2 blocks. **/
    HIGH("HIGH"),

    /** Transaction expected to be confirmed within 6 blocks. **/
    MEDIUM("MEDIUM"),

    /** Transaction expected to be confirmed within 18 blocks. **/
    LOW("LOW"),

    /** Transaction expected to be confirmed within 50 blocks. **/
    BACKGROUND("BACKGROUND"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object OnChainFeeTargetSerializer :
    EnumSerializer<OnChainFeeTarget>(
        OnChainFeeTarget::class,
        { rawValue ->
            OnChainFeeTarget.values().firstOrNull { it.rawValue == rawValue } ?: OnChainFeeTarget.FUTURE_VALUE
        },
    )
