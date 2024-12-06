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
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * This is an object representing the connected Lightspark account. You can retrieve this object to see your account information and objects tied to your account.
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
) : LightsparkNodeOwner,
    Entity {
    @JvmOverloads
    fun getApiTokensQuery(first: Int? = null, after: String? = null): Query<AccountToApiTokensConnection> {
        return Query(
            queryPayload = """
query FetchAccountToApiTokensConnection(${'$'}first: Int, ${'$'}after: String) {
    current_account {
        ... on Account {
            api_tokens(, first: ${'$'}first, after: ${'$'}after) {
                type: __typename
                account_to_api_tokens_connection_count: count
                account_to_api_tokens_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
                account_to_api_tokens_connection_entities: entities {
                    type: __typename
                    api_token_id: id
                    api_token_created_at: created_at
                    api_token_updated_at: updated_at
                    api_token_client_id: client_id
                    api_token_name: name
                    api_token_permissions: permissions
                    api_token_is_deleted: is_deleted
                }
            }
        }
    }
}
""",
            variableBuilder = {
                add("first", first)
                add("after", after)
            }
        ) {
            val connection =
                requireNotNull(it["current_account"]?.jsonObject?.get("api_tokens")) { "api_tokens not found" }
            return@Query serializerFormat.decodeFromJsonElement<AccountToApiTokensConnection>(connection)
        }
    }

    @JvmOverloads
    fun getBlockchainBalanceQuery(
        bitcoinNetworks: List<BitcoinNetwork>? = null,
        nodeIds: List<String>? = null,
    ): Query<BlockchainBalance?> {
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
            }
        ) {
            val connection = it["current_account"]?.jsonObject?.get("blockchain_balance") ?: return@Query null
            return@Query serializerFormat.decodeFromJsonElement<BlockchainBalance?>(connection)
        }
    }

    @JvmOverloads
    fun getConductivityQuery(
        bitcoinNetworks: List<BitcoinNetwork>? = null,
        nodeIds: List<String>? = null,
    ): Query<Int?> {
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
            }
        ) {
            val connection = it["current_account"]?.jsonObject?.get("conductivity") ?: return@Query null
            return@Query connection.jsonPrimitive.int
        }
    }

    @JvmOverloads
    fun getLocalBalanceQuery(
        bitcoinNetworks: List<BitcoinNetwork>? = null,
        nodeIds: List<String>? = null,
    ): Query<CurrencyAmount?> {
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
            }
        ) {
            val connection = it["current_account"]?.jsonObject?.get("local_balance") ?: return@Query null
            return@Query serializerFormat.decodeFromJsonElement<CurrencyAmount?>(connection)
        }
    }

    @JvmOverloads
    fun getNodesQuery(
        first: Int? = null,
        bitcoinNetworks: List<BitcoinNetwork>? = null,
        nodeIds: List<String>? = null,
        after: String? = null,
    ): Query<AccountToNodesConnection> {
        return Query(
            queryPayload = """
query FetchAccountToNodesConnection(${'$'}first: Int, ${'$'}bitcoin_networks: [BitcoinNetwork!], ${'$'}node_ids: [ID!], ${'$'}after: String) {
    current_account {
        ... on Account {
            nodes(, first: ${'$'}first, bitcoin_networks: ${'$'}bitcoin_networks, node_ids: ${'$'}node_ids, after: ${'$'}after) {
                type: __typename
                account_to_nodes_connection_count: count
                account_to_nodes_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
                account_to_nodes_connection_entities: entities {
                    type: __typename
                    ... on LightsparkNodeWithOSK {
                        type: __typename
                        lightspark_node_with_o_s_k_id: id
                        lightspark_node_with_o_s_k_created_at: created_at
                        lightspark_node_with_o_s_k_updated_at: updated_at
                        lightspark_node_with_o_s_k_alias: alias
                        lightspark_node_with_o_s_k_bitcoin_network: bitcoin_network
                        lightspark_node_with_o_s_k_color: color
                        lightspark_node_with_o_s_k_conductivity: conductivity
                        lightspark_node_with_o_s_k_display_name: display_name
                        lightspark_node_with_o_s_k_public_key: public_key
                        lightspark_node_with_o_s_k_owner: owner {
                            id
                        }
                        lightspark_node_with_o_s_k_status: status
                        lightspark_node_with_o_s_k_total_balance: total_balance {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        lightspark_node_with_o_s_k_total_local_balance: total_local_balance {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        lightspark_node_with_o_s_k_local_balance: local_balance {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        lightspark_node_with_o_s_k_remote_balance: remote_balance {
                            type: __typename
                            currency_amount_original_value: original_value
                            currency_amount_original_unit: original_unit
                            currency_amount_preferred_currency_unit: preferred_currency_unit
                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                        }
                        lightspark_node_with_o_s_k_blockchain_balance: blockchain_balance {
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
                        lightspark_node_with_o_s_k_uma_prescreening_utxos: uma_prescreening_utxos
                        lightspark_node_with_o_s_k_balances: balances {
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
                        lightspark_node_with_o_s_k_encrypted_signing_private_key: encrypted_signing_private_key {
                            type: __typename
                            secret_encrypted_value: encrypted_value
                            secret_cipher: cipher
                        }
                    }
                    ... on LightsparkNodeWithRemoteSigning {
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
                    }
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
                add("after", after)
            }
        ) {
            val connection = requireNotNull(it["current_account"]?.jsonObject?.get("nodes")) { "nodes not found" }
            return@Query serializerFormat.decodeFromJsonElement<AccountToNodesConnection>(connection)
        }
    }

    @JvmOverloads
    fun getRemoteBalanceQuery(
        bitcoinNetworks: List<BitcoinNetwork>? = null,
        nodeIds: List<String>? = null,
    ): Query<CurrencyAmount?> {
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
            }
        ) {
            val connection = it["current_account"]?.jsonObject?.get("remote_balance") ?: return@Query null
            return@Query serializerFormat.decodeFromJsonElement<CurrencyAmount?>(connection)
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
            }
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
        after: String? = null,
    ): Query<AccountToChannelsConnection> {
        return Query(
            queryPayload = """
query FetchAccountToChannelsConnection(${'$'}bitcoin_network: BitcoinNetwork!, ${'$'}lightning_node_id: ID, ${'$'}after_date: DateTime, ${'$'}before_date: DateTime, ${'$'}first: Int, ${'$'}after: String) {
    current_account {
        ... on Account {
            channels(, bitcoin_network: ${'$'}bitcoin_network, lightning_node_id: ${'$'}lightning_node_id, after_date: ${'$'}after_date, before_date: ${'$'}before_date, first: ${'$'}first, after: ${'$'}after) {
                type: __typename
                account_to_channels_connection_count: count
                account_to_channels_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
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
                add("after", after)
            }
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
        maxAmount: CurrencyAmountInput? = null,
        minAmount: CurrencyAmountInput? = null,
    ): Query<AccountToTransactionsConnection> {
        return Query(
            queryPayload = """
query FetchAccountToTransactionsConnection(${'$'}first: Int, ${'$'}after: String, ${'$'}types: [TransactionType!], ${'$'}after_date: DateTime, ${'$'}before_date: DateTime, ${'$'}bitcoin_network: BitcoinNetwork, ${'$'}lightning_node_id: ID, ${'$'}statuses: [TransactionStatus!], ${'$'}exclude_failures: TransactionFailures, ${'$'}max_amount: CurrencyAmountInput, ${'$'}min_amount: CurrencyAmountInput) {
    current_account {
        ... on Account {
            transactions(, first: ${'$'}first, after: ${'$'}after, types: ${'$'}types, after_date: ${'$'}after_date, before_date: ${'$'}before_date, bitcoin_network: ${'$'}bitcoin_network, lightning_node_id: ${'$'}lightning_node_id, statuses: ${'$'}statuses, exclude_failures: ${'$'}exclude_failures, max_amount: ${'$'}max_amount, min_amount: ${'$'}min_amount) {
                type: __typename
                account_to_transactions_connection_count: count
                account_to_transactions_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
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
                        incoming_payment_is_uma: is_uma
                        incoming_payment_destination: destination {
                            id
                        }
                        incoming_payment_payment_request: payment_request {
                            id
                        }
                        incoming_payment_uma_post_transaction_data: uma_post_transaction_data {
                            type: __typename
                            post_transaction_data_utxo: utxo
                            post_transaction_data_amount: amount {
                                type: __typename
                                currency_amount_original_value: original_value
                                currency_amount_original_unit: original_unit
                                currency_amount_preferred_currency_unit: preferred_currency_unit
                                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                            }
                        }
                        incoming_payment_is_internal_payment: is_internal_payment
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
                        outgoing_payment_is_uma: is_uma
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
                                invoice_data_memo: memo
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
                                    ... on LightsparkNodeWithOSK {
                                        type: __typename
                                        lightspark_node_with_o_s_k_id: id
                                        lightspark_node_with_o_s_k_created_at: created_at
                                        lightspark_node_with_o_s_k_updated_at: updated_at
                                        lightspark_node_with_o_s_k_alias: alias
                                        lightspark_node_with_o_s_k_bitcoin_network: bitcoin_network
                                        lightspark_node_with_o_s_k_color: color
                                        lightspark_node_with_o_s_k_conductivity: conductivity
                                        lightspark_node_with_o_s_k_display_name: display_name
                                        lightspark_node_with_o_s_k_public_key: public_key
                                        lightspark_node_with_o_s_k_owner: owner {
                                            id
                                        }
                                        lightspark_node_with_o_s_k_status: status
                                        lightspark_node_with_o_s_k_total_balance: total_balance {
                                            type: __typename
                                            currency_amount_original_value: original_value
                                            currency_amount_original_unit: original_unit
                                            currency_amount_preferred_currency_unit: preferred_currency_unit
                                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                                        }
                                        lightspark_node_with_o_s_k_total_local_balance: total_local_balance {
                                            type: __typename
                                            currency_amount_original_value: original_value
                                            currency_amount_original_unit: original_unit
                                            currency_amount_preferred_currency_unit: preferred_currency_unit
                                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                                        }
                                        lightspark_node_with_o_s_k_local_balance: local_balance {
                                            type: __typename
                                            currency_amount_original_value: original_value
                                            currency_amount_original_unit: original_unit
                                            currency_amount_preferred_currency_unit: preferred_currency_unit
                                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                                        }
                                        lightspark_node_with_o_s_k_remote_balance: remote_balance {
                                            type: __typename
                                            currency_amount_original_value: original_value
                                            currency_amount_original_unit: original_unit
                                            currency_amount_preferred_currency_unit: preferred_currency_unit
                                            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                                        }
                                        lightspark_node_with_o_s_k_blockchain_balance: blockchain_balance {
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
                                        lightspark_node_with_o_s_k_uma_prescreening_utxos: uma_prescreening_utxos
                                        lightspark_node_with_o_s_k_balances: balances {
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
                                        lightspark_node_with_o_s_k_encrypted_signing_private_key: encrypted_signing_private_key {
                                            type: __typename
                                            secret_encrypted_value: encrypted_value
                                            secret_cipher: cipher
                                        }
                                    }
                                    ... on LightsparkNodeWithRemoteSigning {
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
                                    }
                                }
                            }
                        }
                        outgoing_payment_failure_reason: failure_reason
                        outgoing_payment_failure_message: failure_message {
                            type: __typename
                            rich_text_text: text
                        }
                        outgoing_payment_uma_post_transaction_data: uma_post_transaction_data {
                            type: __typename
                            post_transaction_data_utxo: utxo
                            post_transaction_data_amount: amount {
                                type: __typename
                                currency_amount_original_value: original_value
                                currency_amount_original_unit: original_unit
                                currency_amount_preferred_currency_unit: preferred_currency_unit
                                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                            }
                        }
                        outgoing_payment_payment_preimage: payment_preimage
                        outgoing_payment_is_internal_payment: is_internal_payment
                        outgoing_payment_idempotency_key: idempotency_key
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
                add("max_amount", maxAmount)
                add("min_amount", minAmount)
            }
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
        maxAmount: CurrencyAmountInput? = null,
        minAmount: CurrencyAmountInput? = null,
    ): Query<AccountToPaymentRequestsConnection> {
        return Query(
            queryPayload = """
query FetchAccountToPaymentRequestsConnection(${'$'}first: Int, ${'$'}after: String, ${'$'}after_date: DateTime, ${'$'}before_date: DateTime, ${'$'}bitcoin_network: BitcoinNetwork, ${'$'}lightning_node_id: ID, ${'$'}max_amount: CurrencyAmountInput, ${'$'}min_amount: CurrencyAmountInput) {
    current_account {
        ... on Account {
            payment_requests(, first: ${'$'}first, after: ${'$'}after, after_date: ${'$'}after_date, before_date: ${'$'}before_date, bitcoin_network: ${'$'}bitcoin_network, lightning_node_id: ${'$'}lightning_node_id, max_amount: ${'$'}max_amount, min_amount: ${'$'}min_amount) {
                type: __typename
                account_to_payment_requests_connection_count: count
                account_to_payment_requests_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
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
                            invoice_data_memo: memo
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
                                ... on LightsparkNodeWithOSK {
                                    type: __typename
                                    lightspark_node_with_o_s_k_id: id
                                    lightspark_node_with_o_s_k_created_at: created_at
                                    lightspark_node_with_o_s_k_updated_at: updated_at
                                    lightspark_node_with_o_s_k_alias: alias
                                    lightspark_node_with_o_s_k_bitcoin_network: bitcoin_network
                                    lightspark_node_with_o_s_k_color: color
                                    lightspark_node_with_o_s_k_conductivity: conductivity
                                    lightspark_node_with_o_s_k_display_name: display_name
                                    lightspark_node_with_o_s_k_public_key: public_key
                                    lightspark_node_with_o_s_k_owner: owner {
                                        id
                                    }
                                    lightspark_node_with_o_s_k_status: status
                                    lightspark_node_with_o_s_k_total_balance: total_balance {
                                        type: __typename
                                        currency_amount_original_value: original_value
                                        currency_amount_original_unit: original_unit
                                        currency_amount_preferred_currency_unit: preferred_currency_unit
                                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                                    }
                                    lightspark_node_with_o_s_k_total_local_balance: total_local_balance {
                                        type: __typename
                                        currency_amount_original_value: original_value
                                        currency_amount_original_unit: original_unit
                                        currency_amount_preferred_currency_unit: preferred_currency_unit
                                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                                    }
                                    lightspark_node_with_o_s_k_local_balance: local_balance {
                                        type: __typename
                                        currency_amount_original_value: original_value
                                        currency_amount_original_unit: original_unit
                                        currency_amount_preferred_currency_unit: preferred_currency_unit
                                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                                    }
                                    lightspark_node_with_o_s_k_remote_balance: remote_balance {
                                        type: __typename
                                        currency_amount_original_value: original_value
                                        currency_amount_original_unit: original_unit
                                        currency_amount_preferred_currency_unit: preferred_currency_unit
                                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                                    }
                                    lightspark_node_with_o_s_k_blockchain_balance: blockchain_balance {
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
                                    lightspark_node_with_o_s_k_uma_prescreening_utxos: uma_prescreening_utxos
                                    lightspark_node_with_o_s_k_balances: balances {
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
                                    lightspark_node_with_o_s_k_encrypted_signing_private_key: encrypted_signing_private_key {
                                        type: __typename
                                        secret_encrypted_value: encrypted_value
                                        secret_cipher: cipher
                                    }
                                }
                                ... on LightsparkNodeWithRemoteSigning {
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
                                }
                            }
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
                        invoice_is_uma: is_uma
                        invoice_is_lnurl: is_lnurl
                    }
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
                add("max_amount", maxAmount)
                add("min_amount", minAmount)
            }
        ) {
            val connection =
                requireNotNull(
                    it["current_account"]?.jsonObject?.get("payment_requests")
                ) { "payment_requests not found" }
            return@Query serializerFormat.decodeFromJsonElement<AccountToPaymentRequestsConnection>(connection)
        }
    }

    @JvmOverloads
    fun getWithdrawalRequestsQuery(
        first: Int? = null,
        after: String? = null,
        bitcoinNetworks: List<BitcoinNetwork>? = null,
        statuses: List<WithdrawalRequestStatus>? = null,
        nodeIds: List<String>? = null,
        idempotencyKeys: List<String>? = null,
        afterDate: Instant? = null,
        beforeDate: Instant? = null,
        maxAmount: CurrencyAmountInput? = null,
        minAmount: CurrencyAmountInput? = null,
    ): Query<AccountToWithdrawalRequestsConnection> {
        return Query(
            queryPayload = """
query FetchAccountToWithdrawalRequestsConnection(${'$'}first: Int, ${'$'}after: String, ${'$'}bitcoin_networks: [BitcoinNetwork!], ${'$'}statuses: [WithdrawalRequestStatus!], ${'$'}node_ids: [ID!], ${'$'}idempotency_keys: [String!], ${'$'}after_date: DateTime, ${'$'}before_date: DateTime, ${'$'}max_amount: CurrencyAmountInput, ${'$'}min_amount: CurrencyAmountInput) {
    current_account {
        ... on Account {
            withdrawal_requests(, first: ${'$'}first, after: ${'$'}after, bitcoin_networks: ${'$'}bitcoin_networks, statuses: ${'$'}statuses, node_ids: ${'$'}node_ids, idempotency_keys: ${'$'}idempotency_keys, after_date: ${'$'}after_date, before_date: ${'$'}before_date, max_amount: ${'$'}max_amount, min_amount: ${'$'}min_amount) {
                type: __typename
                account_to_withdrawal_requests_connection_count: count
                account_to_withdrawal_requests_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
                account_to_withdrawal_requests_connection_entities: entities {
                    type: __typename
                    withdrawal_request_id: id
                    withdrawal_request_created_at: created_at
                    withdrawal_request_updated_at: updated_at
                    withdrawal_request_requested_amount: requested_amount {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    withdrawal_request_amount: amount {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    withdrawal_request_estimated_amount: estimated_amount {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    withdrawal_request_amount_withdrawn: amount_withdrawn {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    withdrawal_request_total_fees: total_fees {
                        type: __typename
                        currency_amount_original_value: original_value
                        currency_amount_original_unit: original_unit
                        currency_amount_preferred_currency_unit: preferred_currency_unit
                        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
                    }
                    withdrawal_request_bitcoin_address: bitcoin_address
                    withdrawal_request_withdrawal_mode: withdrawal_mode
                    withdrawal_request_status: status
                    withdrawal_request_completed_at: completed_at
                    withdrawal_request_withdrawal: withdrawal {
                        id
                    }
                    withdrawal_request_idempotency_key: idempotency_key
                    withdrawal_request_initiator: initiator
                }
            }
        }
    }
}
""",
            variableBuilder = {
                add("first", first)
                add("after", after)
                add("bitcoin_networks", bitcoinNetworks)
                add("statuses", statuses)
                add("node_ids", nodeIds)
                add("idempotency_keys", idempotencyKeys)
                add("after_date", afterDate)
                add("before_date", beforeDate)
                add("max_amount", maxAmount)
                add("min_amount", minAmount)
            }
        ) {
            val connection =
                requireNotNull(
                    it["current_account"]?.jsonObject?.get("withdrawal_requests")
                ) { "withdrawal_requests not found" }
            return@Query serializerFormat.decodeFromJsonElement<AccountToWithdrawalRequestsConnection>(connection)
        }
    }

    @JvmOverloads
    fun getWalletsQuery(
        first: Int? = null,
        after: String? = null,
        thirdPartyIds: List<String>? = null,
    ): Query<AccountToWalletsConnection> {
        return Query(
            queryPayload = """
query FetchAccountToWalletsConnection(${'$'}first: Int, ${'$'}after: String, ${'$'}third_party_ids: [String!]) {
    current_account {
        ... on Account {
            wallets(, first: ${'$'}first, after: ${'$'}after, third_party_ids: ${'$'}third_party_ids) {
                type: __typename
                account_to_wallets_connection_count: count
                account_to_wallets_connection_page_info: page_info {
                    type: __typename
                    page_info_has_next_page: has_next_page
                    page_info_has_previous_page: has_previous_page
                    page_info_start_cursor: start_cursor
                    page_info_end_cursor: end_cursor
                }
                account_to_wallets_connection_entities: entities {
                    type: __typename
                    wallet_id: id
                    wallet_created_at: created_at
                    wallet_updated_at: updated_at
                    wallet_last_login_at: last_login_at
                    wallet_balances: balances {
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
                    wallet_third_party_identifier: third_party_identifier
                    wallet_account: account {
                        id
                    }
                    wallet_status: status
                }
            }
        }
    }
}
""",
            variableBuilder = {
                add("first", first)
                add("after", after)
                add("third_party_ids", thirdPartyIds)
            }
        ) {
            val connection =
                requireNotNull(it["current_account"]?.jsonObject?.get("wallets")) { "wallets not found" }
            return@Query serializerFormat.decodeFromJsonElement<AccountToWalletsConnection>(connection)
        }
    }

    companion object {
        @JvmStatic
        fun getAccountQuery(): Query<Account> = Query(
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
