// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

/**
 * This is a Lightspark node with remote signing.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param bitcoinNetwork The Bitcoin Network this node is deployed in.
 * @param displayName The name of this node in the network. It will be the most human-readable option possible, depending on the data available for this node.
 * @param ownerId The owner of this LightsparkNode.
 * @param umaPrescreeningUtxos The utxos of the channels that are connected to this node. This is used in uma flow for pre-screening.
 * @param alias A name that identifies the node. It has no importance in terms of operating the node, it is just a way to identify and search for commercial services or popular nodes. This alias can be changed at any time by the node operator.
 * @param color A hexadecimal string that describes a color. For example "#000000" is black, "#FFFFFF" is white. It has no importance in terms of operating the node, it is just a way to visually differentiate nodes. That color can be changed at any time by the node operator.
 * @param conductivity A summary metric used to capture how well positioned a node is to send, receive, or route transactions efficiently. Maximizing a node's conductivity helps a node’s transactions to be capital efficient. The value is an integer ranging between 0 and 10 (bounds included).
 * @param publicKey The public key of this node. It acts as a unique identifier of this node in the Lightning Network.
 * @param status The current status of this node.
 * @param totalBalance The sum of the balance on the Bitcoin Network, channel balances, and commit fees on this node.
 * @param totalLocalBalance The total sum of the channel balances (online and offline) on this node.
 * @param localBalance The sum of the channel balances (online only) that are available to send on this node.
 * @param remoteBalance The sum of the channel balances that are available to receive on this node.
 * @param blockchainBalance The details of the balance of this node on the Bitcoin Network.
 * @param balances The balances that describe the funds in this node.
 */
@Serializable
@SerialName("LightsparkNodeWithRemoteSigning")
data class LightsparkNodeWithRemoteSigning(
    @SerialName("lightspark_node_with_remote_signing_id")
    override val id: String,
    @SerialName("lightspark_node_with_remote_signing_created_at")
    override val createdAt: Instant,
    @SerialName("lightspark_node_with_remote_signing_updated_at")
    override val updatedAt: Instant,
    @SerialName("lightspark_node_with_remote_signing_bitcoin_network")
    override val bitcoinNetwork: BitcoinNetwork,
    @SerialName("lightspark_node_with_remote_signing_display_name")
    override val displayName: String,
    @SerialName("lightspark_node_with_remote_signing_owner")
    override val ownerId: EntityId,
    @SerialName("lightspark_node_with_remote_signing_uma_prescreening_utxos")
    override val umaPrescreeningUtxos: List<String>,
    @SerialName("lightspark_node_with_remote_signing_alias")
    override val alias: String? = null,
    @SerialName("lightspark_node_with_remote_signing_color")
    override val color: String? = null,
    @SerialName("lightspark_node_with_remote_signing_conductivity")
    override val conductivity: Int? = null,
    @SerialName("lightspark_node_with_remote_signing_public_key")
    override val publicKey: String? = null,
    @SerialName("lightspark_node_with_remote_signing_status")
    override val status: LightsparkNodeStatus? = null,
    @SerialName("lightspark_node_with_remote_signing_total_balance")
    override val totalBalance: CurrencyAmount? = null,
    @SerialName("lightspark_node_with_remote_signing_total_local_balance")
    override val totalLocalBalance: CurrencyAmount? = null,
    @SerialName("lightspark_node_with_remote_signing_local_balance")
    override val localBalance: CurrencyAmount? = null,
    @SerialName("lightspark_node_with_remote_signing_remote_balance")
    override val remoteBalance: CurrencyAmount? = null,
    @SerialName("lightspark_node_with_remote_signing_blockchain_balance")
    override val blockchainBalance: BlockchainBalance? = null,
    @SerialName("lightspark_node_with_remote_signing_balances")
    override val balances: Balances? = null,
) : LightsparkNode, Node, Entity {
    override fun getAddressesQuery(first: Int?, types: List<NodeAddressType>?): Query<NodeToAddressesConnection> {
        return Query(
            queryPayload = """
query FetchNodeToAddressesConnection(${'$'}entity_id: ID!, ${'$'}first: Int, ${'$'}types: [NodeAddressType!]) {
    entity(id: ${'$'}entity_id) {
        ... on LightsparkNodeWithRemoteSigning {
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

    override fun getChannelsQuery(
        first: Int?,
        statuses: List<ChannelStatus>?,
        after: String?,
    ): Query<LightsparkNodeToChannelsConnection> {
        return Query(
            queryPayload = """
query FetchLightsparkNodeToChannelsConnection(${'$'}entity_id: ID!, ${'$'}first: Int, ${'$'}statuses: [ChannelStatus!], ${'$'}after: String) {
    entity(id: ${'$'}entity_id) {
        ... on LightsparkNodeWithRemoteSigning {
            channels(, first: ${'$'}first, statuses: ${'$'}statuses, after: ${'$'}after) {
                type: __typename
                lightspark_node_to_channels_connection_count: count
                lightspark_node_to_channels_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
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
                add("after", after)
            }
        ) {
            val connection = requireNotNull(it["entity"]?.jsonObject?.get("channels")) { "channels not found" }
            return@Query serializerFormat.decodeFromJsonElement<LightsparkNodeToChannelsConnection>(connection)
        }
    }

    companion object {
        @JvmStatic
        fun getLightsparkNodeWithRemoteSigningQuery(id: String): Query<LightsparkNodeWithRemoteSigning> {
            return Query(
                queryPayload = """
query GetLightsparkNodeWithRemoteSigning(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on LightsparkNodeWithRemoteSigning {
            ...LightsparkNodeWithRemoteSigningFragment
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
fragment LightsparkNodeWithRemoteSigningFragment on LightsparkNodeWithRemoteSigning {
    type: __typename
    lightspark_node_with_remote_signing_id: id
    lightspark_node_with_remote_signing_created_at: created_at
    lightspark_node_with_remote_signing_updated_at: updated_at
    lightspark_node_with_remote_signing_alias: alias
    lightspark_node_with_remote_signing_bitcoin_network: bitcoin_network
    lightspark_node_with_remote_signing_color: color
    lightspark_node_with_remote_signing_conductivity: conductivity
    lightspark_node_with_remote_signing_display_name: display_name
    lightspark_node_with_remote_signing_public_key: public_key
    lightspark_node_with_remote_signing_owner: owner {
        id
    }
    lightspark_node_with_remote_signing_status: status
    lightspark_node_with_remote_signing_total_balance: total_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    lightspark_node_with_remote_signing_total_local_balance: total_local_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    lightspark_node_with_remote_signing_local_balance: local_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    lightspark_node_with_remote_signing_remote_balance: remote_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    lightspark_node_with_remote_signing_blockchain_balance: blockchain_balance {
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
    lightspark_node_with_remote_signing_uma_prescreening_utxos: uma_prescreening_utxos
    lightspark_node_with_remote_signing_balances: balances {
        type: __typename
        balances_owned_balance: owned_balance {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        balances_available_to_send_balance: available_to_send_balance {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        balances_available_to_withdraw_balance: available_to_withdraw_balance {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
    }
}"""
    }
}
