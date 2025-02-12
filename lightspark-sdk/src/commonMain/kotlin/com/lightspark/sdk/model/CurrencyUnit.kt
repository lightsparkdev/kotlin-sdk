// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This enum identifies the unit of currency associated with a CurrencyAmount. **/
@Serializable(with = CurrencyUnitSerializer::class)
enum class CurrencyUnit(
    val rawValue: String,
) {
    /** Bitcoin is the cryptocurrency native to the Bitcoin network. It is used as the native medium for value transfer for the Lightning Network. **/
    BITCOIN("BITCOIN"),

    /** 0.00000001 (10e-8) Bitcoin or one hundred millionth of a Bitcoin. This is the unit most commonly used in Lightning transactions. **/
    SATOSHI("SATOSHI"),

    /** 0.001 Satoshi, or 10e-11 Bitcoin. We recommend using the Satoshi unit instead when possible. **/
    MILLISATOSHI("MILLISATOSHI"),

    /** United States Dollar. **/
    USD("USD"),

    /** Mexican Peso. **/
    MXN("MXN"),

    /** 0.000000001 (10e-9) Bitcoin or a billionth of a Bitcoin. We recommend using the Satoshi unit instead when possible. **/
    NANOBITCOIN("NANOBITCOIN"),

    /** 0.000001 (10e-6) Bitcoin or a millionth of a Bitcoin. We recommend using the Satoshi unit instead when possible. **/
    MICROBITCOIN("MICROBITCOIN"),

    /** 0.001 (10e-3) Bitcoin or a thousandth of a Bitcoin. We recommend using the Satoshi unit instead when possible. **/
    MILLIBITCOIN("MILLIBITCOIN"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object CurrencyUnitSerializer :
    EnumSerializer<CurrencyUnit>(
        CurrencyUnit::class,
        { rawValue ->
            CurrencyUnit.values().firstOrNull { it.rawValue == rawValue } ?: CurrencyUnit.FUTURE_VALUE
        },
    )
