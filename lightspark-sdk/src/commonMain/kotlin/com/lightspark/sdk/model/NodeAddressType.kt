// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum of the potential types of addresses that a node on the Lightning Network can have. **/
@Serializable(with = NodeAddressTypeSerializer::class)
enum class NodeAddressType(
    val rawValue: String,
) {
    IPV4("IPV4"),

    IPV6("IPV6"),

    TOR("TOR"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object NodeAddressTypeSerializer :
    EnumSerializer<NodeAddressType>(
        NodeAddressType::class,
        { rawValue ->
            NodeAddressType.values().firstOrNull { it.rawValue == rawValue } ?: NodeAddressType.FUTURE_VALUE
        },
    )
