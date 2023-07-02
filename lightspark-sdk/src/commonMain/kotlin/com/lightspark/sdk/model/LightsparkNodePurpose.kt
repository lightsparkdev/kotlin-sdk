// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = LightsparkNodePurposeSerializer::class)
enum class LightsparkNodePurpose(val rawValue: String) {

    SEND("SEND"),

    RECEIVE("RECEIVE"),

    ROUTING("ROUTING"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object LightsparkNodePurposeSerializer :
    EnumSerializer<LightsparkNodePurpose>(
        LightsparkNodePurpose::class,
        { rawValue ->
            LightsparkNodePurpose.values().firstOrNull { it.rawValue == rawValue } ?: LightsparkNodePurpose.FUTURE_VALUE
        },
    )
