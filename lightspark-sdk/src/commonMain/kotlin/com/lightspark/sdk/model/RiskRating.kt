// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum of the potential risk ratings related to a transaction made over the Lightning Network. These risk ratings are returned from the CryptoSanctionScreeningProvider. **/
@Serializable(with = RiskRatingSerializer::class)
enum class RiskRating(val rawValue: String) {
    HIGH_RISK("HIGH_RISK"),

    LOW_RISK("LOW_RISK"),

    UNKNOWN("UNKNOWN"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object RiskRatingSerializer :
    EnumSerializer<RiskRating>(
        RiskRating::class,
        { rawValue ->
            RiskRating.values().firstOrNull { it.rawValue == rawValue } ?: RiskRating.FUTURE_VALUE
        },
    )
