// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.util.serializerFormat
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

/**
 * This is a node that is managed by Lightspark and is managed within the current connected account. It contains many details about the node configuration, state, and metadata.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param bitcoinNetwork The Bitcoin Network this node is deployed in.
 * @param displayName The name of this node in the network. It will be the most human-readable option possible, depending on the data available for this node.
 * @param accountId The account that owns this LightsparkNode.
 * @param ownerId The owner of this LightsparkNode.
 * @param alias A name that identifies the node. It has no importance in terms of operating the node, it is just a way to identify and search for commercial services or popular nodes. This alias can be changed at any time by the node operator.
 * @param color A hexadecimal string that describes a color. For example "#000000" is black, "#FFFFFF" is white. It has no importance in terms of operating the node, it is just a way to visually differentiate nodes. That color can be changed at any time by the node operator.
 * @param conductivity A summary metric used to capture how well positioned a node is to send, receive, or route transactions efficiently. Maximizing a node's conductivity helps a node’s transactions to be capital efficient. The value is an integer ranging between 0 and 10 (bounds included).
 * @param publicKey The public key of this node. It acts as a unique identifier of this node in the Lightning Network.
 * @param blockchainBalance The details of the balance of this node on the Bitcoin Network.
 * @param encryptedSigningPrivateKey The private key client is using to sign a GraphQL request which will be verified at LND.
 * @param totalBalance The sum of the balance on the Bitcoin Network, channel balances, and commit fees on this node.
 * @param totalLocalBalance The total sum of the channel balances (online and offline) on this node.
 * @param localBalance The sum of the channel balances (online only) that are available to send on this node.
 * @param purpose The main purpose of this node. It is used by Lightspark for optimizations on the node's channels.
 * @param remoteBalance The sum of the channel balances that are available to receive on this node.
 * @param status The current status of this node.
 */
@Serializable
@SerialName("LightsparkNode")
data class LightsparkNode(

    @SerialName("lightspark_node_id")
    override val id: String,
    @SerialName("lightspark_node_created_at")
    override val createdAt: Instant,
    @SerialName("lightspark_node_updated_at")
    override val updatedAt: Instant,
    @SerialName("lightspark_node_bitcoin_network")
    override val bitcoinNetwork: BitcoinNetwork,
    @SerialName("lightspark_node_display_name")
    override val displayName: String,
    @SerialName("lightspark_node_account")
    val accountId: EntityId,
    @SerialName("lightspark_node_owner")
    val ownerId: EntityId,
    @SerialName("lightspark_node_alias")
    override val alias: String? = null,
    @SerialName("lightspark_node_color")
    override val color: String? = null,
    @SerialName("lightspark_node_conductivity")
    override val conductivity: Int? = null,
    @SerialName("lightspark_node_public_key")
    override val publicKey: String? = null,
    @SerialName("lightspark_node_blockchain_balance")
    val blockchainBalance: BlockchainBalance? = null,
    @SerialName("lightspark_node_encrypted_signing_private_key")
    val encryptedSigningPrivateKey: Secret? = null,
    @SerialName("lightspark_node_total_balance")
    val totalBalance: CurrencyAmount? = null,
    @SerialName("lightspark_node_total_local_balance")
    val totalLocalBalance: CurrencyAmount? = null,
    @SerialName("lightspark_node_local_balance")
    val localBalance: CurrencyAmount? = null,
    @SerialName("lightspark_node_purpose")
    val purpose: LightsparkNodePurpose? = null,
    @SerialName("lightspark_node_remote_balance")
    val remoteBalance: CurrencyAmount? = null,
    @SerialName("lightspark_node_status")
    val status: LightsparkNodeStatus? = null,
) : Node, Entity {

    override fun getAddressesQuery(first: Int?, types: List<NodeAddressType>?): Query<NodeToAddressesConnection> {
        return Query(
            queryPayload = """
query FetchNodeToAddressesConnection(${'$'}entity_id: ID!, ${'$'}first: Int, ${'$'}types: [NodeAddressType!]) {
    entity(id: ${'$'}entity_id) {
        ... on LightsparkNode {
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
            }
        ) {
            val connection = requireNotNull(it["entity"]?.jsonObject?.get("addresses")) { "addresses not found" }
            return@Query serializerFormat.decodeFromJsonElement<NodeToAddressesConnection>(connection)
        }
    }

    @JvmOverloads
    fun getChannelsQuery(
        first: Int? = null,
        statuses: List<ChannelStatus>? = null,
    ): Query<LightsparkNodeToChannelsConnection> {
        return Query(
            queryPayload = """
query FetchLightsparkNodeToChannelsConnection(${'$'}entity_id: ID!, ${'$'}first: Int, ${'$'}statuses: [ChannelStatus!]) {
    entity(id: ${'$'}entity_id) {
        ... on LightsparkNode {
            channels(, first: ${'$'}first, statuses: ${'$'}statuses) {
                type: __typename
                lightspark_node_to_channels_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
                lightspark_node_to_channels_connection_count: count
                lightspark_node_to_channels_connection_entities: entities {
                    type: __typename
                    channel_id: id
                    channel_created_at: created_at
                    channel_updated_at: updated_at
                    channel_funding_transaction: funding_transaction {
                        id
                    }
                    channel_capacity: capacity {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    channel_local_balance: local_balance {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    channel_local_unsettled_balance: local_unsettled_balance {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    channel_remote_balance: remote_balance {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    channel_remote_unsettled_balance: remote_unsettled_balance {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    channel_unsettled_balance: unsettled_balance {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    channel_total_balance: total_balance {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    channel_status: status
                    channel_estimated_force_closure_wait_minutes: estimated_force_closure_wait_minutes
                    channel_commit_fee: commit_fee {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    channel_fees: fees {
                        type: __typename
                        channel_fees_base_fee: base_fee {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        channel_fees_fee_rate_per_mil: fee_rate_per_mil
                    }
                    channel_remote_node: remote_node {
                        id
                    }
                    channel_local_node: local_node {
                        id
                    }
                    channel_short_channel_id: short_channel_id
                }
            }
        }
    }
}
""",
            variableBuilder = {
                add("entity_id", id)
                add("first", first)
                add("statuses", statuses)
            }
        ) {
            val connection = requireNotNull(it["entity"]?.jsonObject?.get("channels")) { "channels not found" }
            return@Query serializerFormat.decodeFromJsonElement<LightsparkNodeToChannelsConnection>(connection)
        }
    }

    companion object {
        @JvmStatic
        fun getLightsparkNodeQuery(id: String): Query<LightsparkNode> {
            return Query(
                queryPayload = """
query GetLightsparkNode(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on LightsparkNode {
            ...LightsparkNodeFragment
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
fragment LightsparkNodeFragment on LightsparkNode {
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
    lightspark_node_owner: owner {
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
}"""
    }
}
