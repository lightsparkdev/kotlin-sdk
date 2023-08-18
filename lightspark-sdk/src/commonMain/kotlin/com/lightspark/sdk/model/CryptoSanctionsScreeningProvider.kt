// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum identifying a type of crypto sanctions screening provider. **/
@Serializable(with = CryptoSanctionsScreeningProviderSerializer::class)
enum class CryptoSanctionsScreeningProvider(val rawValue: String) {

    CHAINALYSIS("CHAINALYSIS"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object CryptoSanctionsScreeningProviderSerializer :
    EnumSerializer<CryptoSanctionsScreeningProvider>(
        CryptoSanctionsScreeningProvider::class,
        { rawValue ->
            CryptoSanctionsScreeningProvider.values().firstOrNull {
                it.rawValue == rawValue
            } ?: CryptoSanctionsScreeningProvider.FUTURE_VALUE
        },
    )
