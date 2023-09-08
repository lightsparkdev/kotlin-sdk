package com.lightspark.sdk.uma

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = KycStatusSerializer::class)
enum class KycStatus(val rawValue: String) {

    UNKNOWN("UNKNOWN"),

    NOT_VERIFIED("NOT_VERIFIED"),

    PENDING("PENDING"),

    VERIFIED("VERIFIED"),
}

object KycStatusSerializer :
    EnumSerializer<KycStatus>(
        KycStatus::class,
        { rawValue ->
            KycStatus.values().firstOrNull { it.rawValue == rawValue } ?: KycStatus.UNKNOWN
        },
    )
