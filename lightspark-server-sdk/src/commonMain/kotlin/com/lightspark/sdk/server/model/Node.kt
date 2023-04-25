// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.server.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

/**
 * This interface represents a lightning node that can be connected to the Lightning Network to send and receive transactions.
 * @property id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @property createdAt The date and time when the entity was first created.
 * @property updatedAt The date and time when the entity was last updated.
 * @property bitcoinNetwork The Bitcoin Network this node is deployed in.
 * @property displayName The name of this node in the network. It will be the most human-readable option possible, depending on the data available for this node.
 * @property alias A name that identifies the node. It has no importance in terms of operating the node, it is just a way to identify and search for commercial services or popular nodes. This alias can be changed at any time by the node operator.
 * @property color A hexadecimal string that describes a color. For example "#000000" is black, "#FFFFFF" is white. It has no importance in terms of operating the node, it is just a way to visually differentiate nodes. That color can be changed at any time by the node operator.
 * @property conductivity A summary metric used to capture how well positioned a node is to send, receive, or route transactions efficiently. Maximizing a node's conductivity helps a node’s transactions to be capital efficient. The value is an integer ranging between 0 and 10 (bounds included).
 * @property publicKey The public key of this node. It acts as a unique identifier of this node in the Lightning Network.
 */
interface Node : Entity {

    @SerialName("node_id")
    override val id: String

    @SerialName("node_created_at")
    override val createdAt: Instant

    @SerialName("node_updated_at")
    override val updatedAt: Instant

    @SerialName("node_bitcoin_network")
    val bitcoinNetwork: BitcoinNetwork

    @SerialName("node_display_name")
    val displayName: String

    @SerialName("node_alias")
    val alias: String?

    @SerialName("node_color")
    val color: String?

    @SerialName("node_conductivity")
    val conductivity: Int?

    @SerialName("node_public_key")
    val publicKey: String?

    fun getAddressesQuery(first: Int? = null, types: List<NodeAddressType>? = null): Query<NodeToAddressesConnection> {
        return Query(
            queryPayload = """
query FetchNodeToAddressesConnection(${'$'}entity_id: ID!, ${'$'}first: Int, ${'$'}types: [NodeAddressType!]) {
    entity(id: ${'$'}entity_id) {
        ... on Node {
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
        fun getNodeQuery(id: String): Query<Node> {
            return Query(
                queryPayload = """
query GetNode(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on Node {
            ...NodeFragment
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
fragment NodeFragment on Node {
    type: __typename
    ... on GraphNode {
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
    }
    ... on LightsparkNode {
        type: __typename
        lightspark_node_id: id
        lightspark_node_created_at: created_at
        lightspark_node_updated_at: updated_at
        lightspark_node_alias: alias
        lightspark_node_bitcoin_network: bitcoin_network
        lightspark_node_color: color
        lightspark_node_conductivity: conductivity
        lightspark_node_display_name: display_name
        lightspark_node_public_key: public_key
        lightspark_node_account: account {
            id
        }
        lightspark_node_blockchain_balance: blockchain_balance {
            type: __typename
            blockchain_balance_total_balance: total_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            blockchain_balance_confirmed_balance: confirmed_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            blockchain_balance_unconfirmed_balance: unconfirmed_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            blockchain_balance_locked_balance: locked_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            blockchain_balance_required_reserve: required_reserve {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            blockchain_balance_available_balance: available_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
        }
        lightspark_node_encrypted_signing_private_key: encrypted_signing_private_key {
            type: __typename
            secret_encrypted_value: encrypted_value
            secret_cipher: cipher
        }
        lightspark_node_total_balance: total_balance {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        lightspark_node_total_local_balance: total_local_balance {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        lightspark_node_local_balance: local_balance {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        lightspark_node_purpose: purpose
        lightspark_node_remote_balance: remote_balance {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        lightspark_node_status: status
    }
}"""
    }
}
