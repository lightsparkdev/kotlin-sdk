// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** An enum that enumerates all possible types of addresses of a node on the Lightning Network. **/
@Serializable(with = NodeAddressTypeSerializer::class)
enum class NodeAddressType(val rawValue: String) {

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
