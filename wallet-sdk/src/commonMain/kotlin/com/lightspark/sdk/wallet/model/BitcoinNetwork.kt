// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum identifying a particular Bitcoin Network. **/
@Serializable(with = BitcoinNetworkSerializer::class)
enum class BitcoinNetwork(val rawValue: String) {
    /** The production version of the Bitcoin Blockchain. **/
    MAINNET("MAINNET"),

    /** A test version of the Bitcoin Blockchain, maintained by Lightspark. **/
    REGTEST("REGTEST"),

    /** A test version of the Bitcoin Blockchain, maintained by a centralized organization. Not in use at Lightspark. **/
    SIGNET("SIGNET"),

    /** A test version of the Bitcoin Blockchain, publicly available. **/
    TESTNET("TESTNET"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object BitcoinNetworkSerializer :
    EnumSerializer<BitcoinNetwork>(
        BitcoinNetwork::class,
        { rawValue ->
            BitcoinNetwork.values().firstOrNull { it.rawValue == rawValue } ?: BitcoinNetwork.FUTURE_VALUE
        },
    )
