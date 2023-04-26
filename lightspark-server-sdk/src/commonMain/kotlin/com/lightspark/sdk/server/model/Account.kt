// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.server.util.serializerFormat
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 *
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param name The name of this account.
 */
@Serializable
@SerialName("Account")
data class Account(

    @SerialName("account_id")
    override val id: String,
    @SerialName("account_created_at")
    override val createdAt: Instant,
    @SerialName("account_updated_at")
    override val updatedAt: Instant,
    @SerialName("account_name")
    val name: String? = null,
) : Entity {
    @JvmOverloads
    fun getApiTokensQuery(first: Int? = null): Query<AccountToApiTokensConnection> {
        return Query(
            queryPayload = """
query FetchAccountToApiTokensConnection(${'$'}first: Int) {
    current_account {
        ... on Account {
            api_tokens(, first: ${'$'}first) {
                type: __typename
                account_to_api_tokens_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
                account_to_api_tokens_connection_count: count
                account_to_api_tokens_connection_entities: entities {
                    type: __typename
                    api_token_id: id
                    api_token_created_at: created_at
                    api_token_updated_at: updated_at
                    api_token_client_id: client_id
                    api_token_name: name
                    api_token_permissions: permissions
                }
            }
        }
    }
}
""",
            variableBuilder = {
                add("first", first)
            },
        ) {
            val connection =
                requireNotNull(it["current_account"]?.jsonObject?.get("api_tokens")) { "api_tokens not found" }
            return@Query serializerFormat.decodeFromJsonElement<AccountToApiTokensConnection>(connection)
        }
    }

    @JvmOverloads
    fun getBlockchainBalanceQuery(bitcoinNetworks: List<BitcoinNetwork>? = null, nodeIds: List<String>? = null): Query<BlockchainBalance?> {
        return Query(
            queryPayload = """
query FetchAccountBlockchainBalance(${'$'}bitcoin_networks: [BitcoinNetwork!], ${'$'}node_ids: [ID!]) {
    current_account {
        ... on Account {
            blockchain_balance(, bitcoin_networks: ${'$'}bitcoin_networks, node_ids: ${'$'}node_ids) {
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
        }
    }
}
""",
            variableBuilder = {
                add("bitcoin_networks", bitcoinNetworks)
                add("node_ids", nodeIds)
            },
        ) {
            val connection = it["current_account"]?.jsonObject?.get("blockchain_balance") ?: return@Query null
            return@Query serializerFormat.decodeFromJsonElement<BlockchainBalance>(connection)
        }
    }

    @JvmOverloads
    fun getConductivityQuery(bitcoinNetworks: List<BitcoinNetwork>? = null, nodeIds: List<String>? = null): Query<Int?> {
        return Query(
            queryPayload = """
query FetchAccountConductivity(${'$'}bitcoin_networks: [BitcoinNetwork!], ${'$'}node_ids: [ID!]) {
    current_account {
        ... on Account {
            conductivity(, bitcoin_networks: ${'$'}bitcoin_networks, node_ids: ${'$'}node_ids)
        }
    }
}
""",
            variableBuilder = {
                add("bitcoin_networks", bitcoinNetworks)
                add("node_ids", nodeIds)
            },
        ) {
            val connection = it["current_account"]?.jsonObject?.get("conductivity") ?: return@Query null
            return@Query connection.jsonPrimitive.int
        }
    }

    @JvmOverloads
    fun getLocalBalanceQuery(bitcoinNetworks: List<BitcoinNetwork>? = null, nodeIds: List<String>? = null): Query<CurrencyAmount?> {
        return Query(
            queryPayload = """
query FetchAccountLocalBalance(${'$'}bitcoin_networks: [BitcoinNetwork!], ${'$'}node_ids: [ID!]) {
    current_account {
        ... on Account {
            local_balance(, bitcoin_networks: ${'$'}bitcoin_networks, node_ids: ${'$'}node_ids) {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
        }
    }
}
""",
            variableBuilder = {
                add("bitcoin_networks", bitcoinNetworks)
                add("node_ids", nodeIds)
            },
        ) {
            val connection = it["current_account"]?.jsonObject?.get("local_balance") ?: return@Query null
            return@Query serializerFormat.decodeFromJsonElement<CurrencyAmount>(connection)
        }
    }

    @JvmOverloads
    fun getNodesQuery(first: Int? = null, bitcoinNetworks: List<BitcoinNetwork>? = null, nodeIds: List<String>? = null): Query<AccountToNodesConnection> {
        return Query(
            queryPayload = """
query FetchAccountToNodesConnection(${'$'}first: Int, ${'$'}bitcoin_networks: [BitcoinNetwork!], ${'$'}node_ids: [ID!]) {
    current_account {
        ... on Account {
            nodes(, first: ${'$'}first, bitcoin_networks: ${'$'}bitcoin_networks, node_ids: ${'$'}node_ids) {
                type: __typename
                account_to_nodes_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
                account_to_nodes_connection_count: count
                account_to_nodes_connection_purpose: purpose
                account_to_nodes_connection_entities: entities {
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
            }
        }
    }
}
""",
            variableBuilder = {
                add("first", first)
                add("bitcoin_networks", bitcoinNetworks)
                add("node_ids", nodeIds)
            },
        ) {
            val connection = requireNotNull(it["current_account"]?.jsonObject?.get("nodes")) { "nodes not found" }
            return@Query serializerFormat.decodeFromJsonElement<AccountToNodesConnection>(connection)
        }
    }

    @JvmOverloads
    fun getRemoteBalanceQuery(bitcoinNetworks: List<BitcoinNetwork>? = null, nodeIds: List<String>? = null): Query<CurrencyAmount?> {
        return Query(
            queryPayload = """
query FetchAccountRemoteBalance(${'$'}bitcoin_networks: [BitcoinNetwork!], ${'$'}node_ids: [ID!]) {
    current_account {
        ... on Account {
            remote_balance(, bitcoin_networks: ${'$'}bitcoin_networks, node_ids: ${'$'}node_ids) {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
        }
    }
}
""",
            variableBuilder = {
                add("bitcoin_networks", bitcoinNetworks)
                add("node_ids", nodeIds)
            },
        ) {
            val connection = it["current_account"]?.jsonObject?.get("remote_balance") ?: return@Query null
            return@Query serializerFormat.decodeFromJsonElement<CurrencyAmount>(connection)
        }
    }

    @JvmOverloads
    fun getUptimePercentageQuery(
        afterDate: Instant? = null,
        beforeDate: Instant? = null,
        bitcoinNetworks: List<BitcoinNetwork>? = null,
        nodeIds: List<String>? = null,
    ): Query<Int?> {
        return Query(
            queryPayload = """
query FetchAccountUptimePercentage(${'$'}after_date: DateTime, ${'$'}before_date: DateTime, ${'$'}bitcoin_networks: [BitcoinNetwork!], ${'$'}node_ids: [ID!]) {
    current_account {
        ... on Account {
            uptime_percentage(, after_date: ${'$'}after_date, before_date: ${'$'}before_date, bitcoin_networks: ${'$'}bitcoin_networks, node_ids: ${'$'}node_ids)
        }
    }
}
""",
            variableBuilder = {
                add("after_date", afterDate)
                add("before_date", beforeDate)
                add("bitcoin_networks", bitcoinNetworks)
                add("node_ids", nodeIds)
            },
        ) {
            val connection = it["current_account"]?.jsonObject?.get("uptime_percentage") ?: return@Query null
            return@Query connection.jsonPrimitive.int
        }
    }

    @JvmOverloads
    fun getChannelsQuery(
        bitcoinNetwork: BitcoinNetwork,
        lightningNodeId: String? = null,
        afterDate: Instant? = null,
        beforeDate: Instant? = null,
        first: Int? = null,
    ): Query<AccountToChannelsConnection> {
        return Query(
            queryPayload = """
query FetchAccountToChannelsConnection(${'$'}bitcoin_network: BitcoinNetwork!, ${'$'}lightning_node_id: ID, ${'$'}after_date: DateTime, ${'$'}before_date: DateTime, ${'$'}first: Int) {
    current_account {
        ... on Account {
            channels(, bitcoin_network: ${'$'}bitcoin_network, lightning_node_id: ${'$'}lightning_node_id, after_date: ${'$'}after_date, before_date: ${'$'}before_date, first: ${'$'}first) {
                type: __typename
                account_to_channels_connection_count: count
                account_to_channels_connection_entities: entities {
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
                add("bitcoin_network", bitcoinNetwork)
                add("lightning_node_id", lightningNodeId)
                add("after_date", afterDate)
                add("before_date", beforeDate)
                add("first", first)
            },
        ) {
            val connection =
                requireNotNull(it["current_account"]?.jsonObject?.get("channels")) { "channels not found" }
            return@Query serializerFormat.decodeFromJsonElement<AccountToChannelsConnection>(connection)
        }
    }

    @JvmOverloads
    fun getTransactionsQuery(
        first: Int? = null,
        after: String? = null,
        types: List<TransactionType>? = null,
        afterDate: Instant? = null,
        beforeDate: Instant? = null,
        bitcoinNetwork: BitcoinNetwork? = null,
        lightningNodeId: String? = null,
        statuses: List<TransactionStatus>? = null,
        excludeFailures: TransactionFailures? = null,
    ): Query<AccountToTransactionsConnection> {
        return Query(
            queryPayload = """
query FetchAccountToTransactionsConnection(${'$'}first: Int, ${'$'}after: String, ${'$'}types: [TransactionType!], ${'$'}after_date: DateTime, ${'$'}before_date: DateTime, ${'$'}bitcoin_network: BitcoinNetwork, ${'$'}lightning_node_id: ID, ${'$'}statuses: [TransactionStatus!], ${'$'}exclude_failures: TransactionFailures) {
    current_account {
        ... on Account {
            transactions(, first: ${'$'}first, after: ${'$'}after, types: ${'$'}types, after_date: ${'$'}after_date, before_date: ${'$'}before_date, bitcoin_network: ${'$'}bitcoin_network, lightning_node_id: ${'$'}lightning_node_id, statuses: ${'$'}statuses, exclude_failures: ${'$'}exclude_failures) {
                type: __typename
                account_to_transactions_connection_profit_loss: profit_loss {
                    type: __typename
                    currency_amount_original_value: original_value
                    currency_amount_original_unit: original_unit
                    currency_amount_preferred_currency_unit: preferred_currency_unit
                    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                }
                account_to_transactions_connection_average_fee_earned: average_fee_earned {
                    type: __typename
                    currency_amount_original_value: original_value
                    currency_amount_original_unit: original_unit
                    currency_amount_preferred_currency_unit: preferred_currency_unit
                    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                }
                account_to_transactions_connection_count: count
                account_to_transactions_connection_total_amount_transacted: total_amount_transacted {
                    type: __typename
                    currency_amount_original_value: original_value
                    currency_amount_original_unit: original_unit
                    currency_amount_preferred_currency_unit: preferred_currency_unit
                    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                }
                account_to_transactions_connection_entities: entities {
                    type: __typename
                    ... on ChannelClosingTransaction {
                        type: __typename
                        channel_closing_transaction_id: id
                        channel_closing_transaction_created_at: created_at
                        channel_closing_transaction_updated_at: updated_at
                        channel_closing_transaction_status: status
                        channel_closing_transaction_resolved_at: resolved_at
                        channel_closing_transaction_amount: amount {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        channel_closing_transaction_transaction_hash: transaction_hash
                        channel_closing_transaction_fees: fees {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        channel_closing_transaction_block_hash: block_hash
                        channel_closing_transaction_block_height: block_height
                        channel_closing_transaction_destination_addresses: destination_addresses
                        channel_closing_transaction_num_confirmations: num_confirmations
                        channel_closing_transaction_channel: channel {
                            id
                        }
                    }
                    ... on ChannelOpeningTransaction {
                        type: __typename
                        channel_opening_transaction_id: id
                        channel_opening_transaction_created_at: created_at
                        channel_opening_transaction_updated_at: updated_at
                        channel_opening_transaction_status: status
                        channel_opening_transaction_resolved_at: resolved_at
                        channel_opening_transaction_amount: amount {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        channel_opening_transaction_transaction_hash: transaction_hash
                        channel_opening_transaction_fees: fees {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        channel_opening_transaction_block_hash: block_hash
                        channel_opening_transaction_block_height: block_height
                        channel_opening_transaction_destination_addresses: destination_addresses
                        channel_opening_transaction_num_confirmations: num_confirmations
                        channel_opening_transaction_channel: channel {
                            id
                        }
                    }
                    ... on Deposit {
                        type: __typename
                        deposit_id: id
                        deposit_created_at: created_at
                        deposit_updated_at: updated_at
                        deposit_status: status
                        deposit_resolved_at: resolved_at
                        deposit_amount: amount {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        deposit_transaction_hash: transaction_hash
                        deposit_fees: fees {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        deposit_block_hash: block_hash
                        deposit_block_height: block_height
                        deposit_destination_addresses: destination_addresses
                        deposit_num_confirmations: num_confirmations
                        deposit_destination: destination {
                            id
                        }
                    }
                    ... on IncomingPayment {
                        type: __typename
                        incoming_payment_id: id
                        incoming_payment_created_at: created_at
                        incoming_payment_updated_at: updated_at
                        incoming_payment_status: status
                        incoming_payment_resolved_at: resolved_at
                        incoming_payment_amount: amount {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        incoming_payment_transaction_hash: transaction_hash
                        incoming_payment_origin: origin {
                            id
                        }
                        incoming_payment_destination: destination {
                            id
                        }
                        incoming_payment_payment_request: payment_request {
                            id
                        }
                    }
                    ... on OutgoingPayment {
                        type: __typename
                        outgoing_payment_id: id
                        outgoing_payment_created_at: created_at
                        outgoing_payment_updated_at: updated_at
                        outgoing_payment_status: status
                        outgoing_payment_resolved_at: resolved_at
                        outgoing_payment_amount: amount {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        outgoing_payment_transaction_hash: transaction_hash
                        outgoing_payment_origin: origin {
                            id
                        }
                        outgoing_payment_destination: destination {
                            id
                        }
                        outgoing_payment_fees: fees {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        outgoing_payment_payment_request_data: payment_request_data {
                            type: __typename
                            ... on InvoiceData {
                                type: __typename
                                invoice_data_encoded_payment_request: encoded_payment_request
                                invoice_data_bitcoin_network: bitcoin_network
                                invoice_data_payment_hash: payment_hash
                                invoice_data_amount: amount {
                                    type: __typename
                                    currency_amount_original_value: original_value
                                    currency_amount_original_unit: original_unit
                                    currency_amount_preferred_currency_unit: preferred_currency_unit
                                    currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                    currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                                }
                                invoice_data_created_at: created_at
                                invoice_data_expires_at: expires_at
                                invoice_data_destination: destination {
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
                                }
                                invoice_data_memo: memo
                            }
                        }
                        outgoing_payment_failure_reason: failure_reason
                        outgoing_payment_failure_message: failure_message {
                            type: __typename
                            rich_text_text: text
                        }
                    }
                    ... on RoutingTransaction {
                        type: __typename
                        routing_transaction_id: id
                        routing_transaction_created_at: created_at
                        routing_transaction_updated_at: updated_at
                        routing_transaction_status: status
                        routing_transaction_resolved_at: resolved_at
                        routing_transaction_amount: amount {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        routing_transaction_transaction_hash: transaction_hash
                        routing_transaction_incoming_channel: incoming_channel {
                            id
                        }
                        routing_transaction_outgoing_channel: outgoing_channel {
                            id
                        }
                        routing_transaction_fees: fees {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        routing_transaction_failure_message: failure_message {
                            type: __typename
                            rich_text_text: text
                        }
                        routing_transaction_failure_reason: failure_reason
                    }
                    ... on Withdrawal {
                        type: __typename
                        withdrawal_id: id
                        withdrawal_created_at: created_at
                        withdrawal_updated_at: updated_at
                        withdrawal_status: status
                        withdrawal_resolved_at: resolved_at
                        withdrawal_amount: amount {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        withdrawal_transaction_hash: transaction_hash
                        withdrawal_fees: fees {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        withdrawal_block_hash: block_hash
                        withdrawal_block_height: block_height
                        withdrawal_destination_addresses: destination_addresses
                        withdrawal_num_confirmations: num_confirmations
                        withdrawal_origin: origin {
                            id
                        }
                    }
                }
                account_to_transactions_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
            }
        }
    }
}
""",
            variableBuilder = {
                add("first", first)
                add("after", after)
                add("types", types)
                add("after_date", afterDate)
                add("before_date", beforeDate)
                add("bitcoin_network", bitcoinNetwork)
                add("lightning_node_id", lightningNodeId)
                add("statuses", statuses)
                add("exclude_failures", excludeFailures)
            },
        ) {
            val connection =
                requireNotNull(it["current_account"]?.jsonObject?.get("transactions")) { "transactions not found" }
            return@Query serializerFormat.decodeFromJsonElement<AccountToTransactionsConnection>(connection)
        }
    }

    @JvmOverloads
    fun getPaymentRequestsQuery(
        first: Int? = null,
        after: String? = null,
        afterDate: Instant? = null,
        beforeDate: Instant? = null,
        bitcoinNetwork: BitcoinNetwork? = null,
        lightningNodeId: String? = null,
    ): Query<AccountToPaymentRequestsConnection> {
        return Query(
            queryPayload = """
query FetchAccountToPaymentRequestsConnection(${'$'}first: Int, ${'$'}after: String, ${'$'}after_date: DateTime, ${'$'}before_date: DateTime, ${'$'}bitcoin_network: BitcoinNetwork, ${'$'}lightning_node_id: ID) {
    current_account {
        ... on Account {
            payment_requests(, first: ${'$'}first, after: ${'$'}after, after_date: ${'$'}after_date, before_date: ${'$'}before_date, bitcoin_network: ${'$'}bitcoin_network, lightning_node_id: ${'$'}lightning_node_id) {
                type: __typename
                account_to_payment_requests_connection_count: count
                account_to_payment_requests_connection_entities: entities {
                    type: __typename
                    ... on Invoice {
                        type: __typename
                        invoice_id: id
                        invoice_created_at: created_at
                        invoice_updated_at: updated_at
                        invoice_data: data {
                            type: __typename
                            invoice_data_encoded_payment_request: encoded_payment_request
                            invoice_data_bitcoin_network: bitcoin_network
                            invoice_data_payment_hash: payment_hash
                            invoice_data_amount: amount {
                                type: __typename
                                currency_amount_original_value: original_value
                                currency_amount_original_unit: original_unit
                                currency_amount_preferred_currency_unit: preferred_currency_unit
                                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                            }
                            invoice_data_created_at: created_at
                            invoice_data_expires_at: expires_at
                            invoice_data_destination: destination {
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
                            }
                            invoice_data_memo: memo
                        }
                        invoice_status: status
                        invoice_amount_paid: amount_paid {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                    }
                }
                account_to_payment_requests_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
            }
        }
    }
}
""",
            variableBuilder = {
                add("first", first)
                add("after", after)
                add("after_date", afterDate)
                add("before_date", beforeDate)
                add("bitcoin_network", bitcoinNetwork)
                add("lightning_node_id", lightningNodeId)
            },
        ) {
            val connection =
                requireNotNull(it["current_account"]?.jsonObject?.get("payment_requests")) { "payment_requests not found" }
            return@Query serializerFormat.decodeFromJsonElement<AccountToPaymentRequestsConnection>(connection)
        }
    }

    companion object {
        @JvmStatic
        fun getAccountQuery(): Query<Account> {
            return Query(
                queryPayload = """
query GetAccount {
    current_account {
        ... on Account {
            ...AccountFragment
        }
    }
}

$FRAGMENT
""",
                variableBuilder = { },
            ) {
                val entity = requireNotNull(it["current_account"]) { "Entity not found" }
                serializerFormat.decodeFromJsonElement(entity)
            }
        }

        const val FRAGMENT = """
fragment AccountFragment on Account {
    type: __typename
    account_id: id
    account_created_at: created_at
    account_updated_at: updated_at
    account_name: name
}"""
    }
}