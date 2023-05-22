// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A connection between a node and the addresses it has announced for itself on Lightning Network.
 * @param count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @param entities The addresses for the current page of this connection.
 */
@Serializable
@SerialName("NodeToAddressesConnection")
data class NodeToAddressesConnection(

    @SerialName("node_to_addresses_connection_count")
    val count: Int,
    @SerialName("node_to_addresses_connection_entities")
    val entities: List<NodeAddress>,
) {

    companion object {

        const val FRAGMENT = """
fragment NodeToAddressesConnectionFragment on NodeToAddressesConnection {
    type: __typename
    node_to_addresses_connection_count: count
    node_to_addresses_connection_entities: entities {
        type: __typename
        node_address_address: address
        node_address_type: type
    }
}"""
    }
}
