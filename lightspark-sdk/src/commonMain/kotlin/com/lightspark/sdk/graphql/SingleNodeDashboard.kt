package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletDashboard(
    val id: String,
    @SerialName("display_name")
    val displayName: String,
    val color: String?,
    @SerialName("public_key")
    val publicKey: String?,
    val status: LightsparkNodeStatus?,
    val addresses: List<NodeAddress>,
    @SerialName("local_balance")
    val localBalance: CurrencyAmount?,
    @SerialName("remote_balance")
    val remoteBalance: CurrencyAmount?,
    @SerialName("blockchain_balance")
    val blockchainBalance: BlockchainBalance?,
    @SerialName("recent_transactions")
    val recentTransactions: AccountToTransactionsConnection,
)

const val SingleNodeDashboardQuery = """
query SingleNodeDashboard(
    ${'$'}network: BitcoinNetwork!,
    ${'$'}nodeId: ID!,
    ${'$'}numTransactions: Int,
    ${'$'}transactionsAfterDate: DateTime,
    ${'$'}transactionTypes: [TransactionType!] = [PAYMENT, PAYMENT_REQUEST, ROUTE, L1_WITHDRAW, L1_DEPOSIT]
    ${'$'}transactionStatuses: [TransactionStatus!] = null
) {
    current_account {
        id
        name
        dashboard_overview_nodes: nodes(
            first: 1
            bitcoin_networks: [${'$'}network]
            node_ids: [${'$'}nodeId]
        ) {
            count
            entities {
                color
                display_name
                id
                addresses(first: 1) {
                    node_to_addresses_connection_entities: entities {
                        node_address_address: address
                        node_address_type: type
                        __typename
                    }
                    node_to_addresses_connection_count: count
                    __typename
                }
                public_key
                status
                local_balance {
                    ...CurrencyAmountFragment
                }
                remote_balance {
                    ...CurrencyAmountFragment
                }
                blockchain_balance {
                    available_balance {
                        ...CurrencyAmountFragment
                    }
                    total_balance {
                        ...CurrencyAmountFragment
                    }
                    __typename
                }
                __typename
            }
            __typename
        }
        blockchain_balance(bitcoin_networks: [${'$'}network], node_ids: [${'$'}nodeId]) {
            ...BlockchainBalanceFragment
        }
        local_balance(bitcoin_networks: [${'$'}network], node_ids: [${'$'}nodeId]) {
            ...CurrencyAmountFragment
        }
        remote_balance(bitcoin_networks: [${'$'}network], node_ids: [${'$'}nodeId]) {
            ...CurrencyAmountFragment
        }
        recent_transactions: transactions(
            first: ${'$'}numTransactions
            types: ${'$'}transactionTypes
            bitcoin_network: ${'$'}network
            lightning_node_id: ${'$'}nodeId
            statuses: ${'$'}transactionStatuses
            after_date: ${'$'}transactionsAfterDate
        ) {
            account_to_transactions_connection_count: count
            account_to_transactions_connection_total_amount_transacted: total_amount_transacted {
                ...CurrencyAmountFragment
            }
            account_to_transactions_connection_entities: entities {
                ...TransactionFragment
            }
            account_to_transactions_connection_profit_loss: profit_loss {
                ...CurrencyAmountFragment
            }
            account_to_transactions_connection_average_fee_earned: average_fee_earned {
                ...CurrencyAmountFragment
            }
            account_to_transactions_connection_page_info: page_info {
                page_info_has_next_page: has_next_page
                page_info_has_previous_page: has_previous_page
                page_info_start_cursor: start_cursor
                page_info_end_cursor: end_cursor
            }
            type: __typename
        }
        __typename
    }
}

${Transaction.FRAGMENT}
${BlockchainBalance.FRAGMENT}
${CurrencyAmount.FRAGMENT}
"""
