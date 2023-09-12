// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = LightsparkNodeStatusSerializer::class)
enum class LightsparkNodeStatus(val rawValue: String) {
    CREATED("CREATED"),

    DEPLOYED("DEPLOYED"),

    STARTED("STARTED"),

    SYNCING("SYNCING"),

    READY("READY"),

    STOPPED("STOPPED"),

    TERMINATED("TERMINATED"),

    TERMINATING("TERMINATING"),

    WALLET_LOCKED("WALLET_LOCKED"),

    FAILED_TO_DEPLOY("FAILED_TO_DEPLOY"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object LightsparkNodeStatusSerializer :
    EnumSerializer<LightsparkNodeStatus>(
        LightsparkNodeStatus::class,
        { rawValue ->
            LightsparkNodeStatus.values().firstOrNull { it.rawValue == rawValue } ?: LightsparkNodeStatus.FUTURE_VALUE
        },
    )
