// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.server.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

/**
 * This is a node on the Lightning Network, managed by a third party. The information about this node is public data that has been obtained by observing the Lightning Network.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param bitcoinNetwork The Bitcoin Network this node is deployed in.
 * @param displayName The name of this node in the network. It will be the most human-readable option possible, depending on the data available for this node.
 * @param alias A name that identifies the node. It has no importance in terms of operating the node, it is just a way to identify and search for commercial services or popular nodes. This alias can be changed at any time by the node operator.
 * @param color A hexadecimal string that describes a color. For example "#000000" is black, "#FFFFFF" is white. It has no importance in terms of operating the node, it is just a way to visually differentiate nodes. That color can be changed at any time by the node operator.
 * @param conductivity A summary metric used to capture how well positioned a node is to send, receive, or route transactions efficiently. Maximizing a node's conductivity helps a node’s transactions to be capital efficient. The value is an integer ranging between 0 and 10 (bounds included).
 * @param publicKey The public key of this node. It acts as a unique identifier of this node in the Lightning Network.
 */
@Serializable
@SerialName("GraphNode")
data class GraphNode(

    @SerialName("graph_node_id")
    override val id: String,
    @SerialName("graph_node_created_at")
    override val createdAt: Instant,
    @SerialName("graph_node_updated_at")
    override val updatedAt: Instant,
    @SerialName("graph_node_bitcoin_network")
    override val bitcoinNetwork: BitcoinNetwork,
    @SerialName("graph_node_display_name")
    override val displayName: String,
    @SerialName("graph_node_alias")
    override val alias: String? = null,
    @SerialName("graph_node_color")
    override val color: String? = null,
    @SerialName("graph_node_conductivity")
    override val conductivity: Int? = null,
    @SerialName("graph_node_public_key")
    override val publicKey: String? = null,
) : Node, Entity {

    override fun getAddressesQuery(first: Int?, types: List<NodeAddressType>?): Query<NodeToAddressesConnection> {
        return Query(
            queryPayload = """
query FetchNodeToAddressesConnection(${'$'}entity_id: ID!, ${'$'}first: Int, ${'$'}types: [NodeAddressType!]) {
    entity(id: ${'$'}entity_id) {
        ... on GraphNode {
            addresses(, first: ${'$'}first, types: ${'$'}types) {
                type: __typename
                node_to_addresses_connection_count: count
                node_to_addresses_connection_entities: entities {
                    type: __typename
                    node_address_address: address
                    node_address_type: type
                }
            }
        }
    }
}
""",
            variableBuilder = {
                add("entity_id", id)
                add("first", first)
                add("types", types)
            },
        ) {
            val connection = requireNotNull(it["entity"]?.jsonObject?.get("addresses")) { "addresses not found" }
            return@Query serializerFormat.decodeFromJsonElement<NodeToAddressesConnection>(connection)
        }
    }

    companion object {
        @JvmStatic
        fun getGraphNodeQuery(id: String): Query<GraphNode> {
            return Query(
                queryPayload = """
query GetGraphNode(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on GraphNode {
            ...GraphNodeFragment
        }
    }
}

$FRAGMENT
""",
                variableBuilder = { add("id", id) },
            ) {
                val entity = requireNotNull(it["entity"]) { "Entity not found" }
                serializerFormat.decodeFromJsonElement(entity)
            }
        }

        const val FRAGMENT = """
fragment GraphNodeFragment on GraphNode {
    type: __typename
    graph_node_id: id
    graph_node_created_at: created_at
    graph_node_updated_at: updated_at
    graph_node_alias: alias
    graph_node_bitcoin_network: bitcoin_network
    graph_node_color: color
    graph_node_conductivity: conductivity
    graph_node_display_name: display_name
    graph_node_public_key: public_key
}"""
    }
}
