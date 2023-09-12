// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum of the potential statuses that a Withdrawal can take. **/
@Serializable(with = WithdrawalRequestStatusSerializer::class)
enum class WithdrawalRequestStatus(val rawValue: String) {
    FAILED("FAILED"),

    IN_PROGRESS("IN_PROGRESS"),

    SUCCESSFUL("SUCCESSFUL"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object WithdrawalRequestStatusSerializer :
    EnumSerializer<WithdrawalRequestStatus>(
        WithdrawalRequestStatus::class,
        { rawValue ->
            WithdrawalRequestStatus.values().firstOrNull { it.rawValue == rawValue } ?: WithdrawalRequestStatus.FUTURE_VALUE
        },
    )
