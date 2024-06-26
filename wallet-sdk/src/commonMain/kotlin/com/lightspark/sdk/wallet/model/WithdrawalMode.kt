// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum of the potential modes that your Bitcoin withdrawal can take. **/
@Serializable(with = WithdrawalModeSerializer::class)
enum class WithdrawalMode(
    val rawValue: String,
) {
    WALLET_ONLY("WALLET_ONLY"),

    WALLET_THEN_CHANNELS("WALLET_THEN_CHANNELS"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object WithdrawalModeSerializer :
    EnumSerializer<WithdrawalMode>(
        WithdrawalMode::class,
        { rawValue ->
            WithdrawalMode.values().firstOrNull { it.rawValue == rawValue } ?: WithdrawalMode.FUTURE_VALUE
        },
    )
