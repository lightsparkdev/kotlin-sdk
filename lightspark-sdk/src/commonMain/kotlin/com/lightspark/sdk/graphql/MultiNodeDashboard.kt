package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDashboard(
    val id: String,
    val name: String,
    @SerialName("dashboard_overview_nodes")
    val nodeConnection: AccountNodesConnection?,
    @SerialName("blockchain_balance")
    val blockchainBalance: BlockchainBalance,
    @SerialName("local_balance")
    val localBalance: CurrencyAmount,
    @SerialName("remote_balance")
    val remoteBalance: CurrencyAmount,
)

@Serializable
data class AccountNodesConnection(
    @SerialName("account_to_nodes_connection_entities")
    val nodes: List<AccountNode>,
    @SerialName("account_to_nodes_connection_count")
    val count: Int,
)

@Serializable
data class AccountNode(
    val id: String,
    val color: String?,
    @SerialName("display_name")
    val displayName: String,
    val purpose: LightsparkNodePurpose?,
    @SerialName("public_key")
    val publicKey: String?,
    val status: LightsparkNodeStatus?,
    val addresses: NodeToAddressesConnection,
    @SerialName("local_balance")
    val localBalance: CurrencyAmount?,
    @SerialName("remote_balance")
    val remoteBalance: CurrencyAmount?,
    @SerialName("blockchain_balance")
    val blockchainBalance: NodeBalances?,
) {
    @Serializable
    data class NodeBalances(
        @SerialName("blockchain_balance_available_balance")
        val availableBalance: CurrencyAmount?,
        @SerialName("blockchain_balance_total_balance")
        val totalBalance: CurrencyAmount?,
    )
}

const val AccountDashboardQuery = """
  query MultiNodeDashboard(
    ${'$'}network: BitcoinNetwork!,
    ${'$'}nodeIds: [ID!]
  ) {
    current_account {
      id
      name
      dashboard_overview_nodes: nodes(
        bitcoin_networks: [${'$'}network]
        node_ids: ${'$'}nodeIds
      ) {
        account_to_nodes_connection_count: count
        account_to_nodes_connection_entities: entities {
          color
          display_name
          purpose
          id
          addresses(first: 1) {
            node_to_addresses_connection_entities: entities {
              node_address_address: address
              node_address_type: type
              type: __typename
            }
            node_to_addresses_connection_count: count
            type: __typename
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
            ...BlockchainBalanceFragment
          }
          type: __typename
        }
        type: __typename
      }
      blockchain_balance(bitcoin_networks: [${'$'}network], node_ids: ${'$'}nodeIds) {
        l1_balance: total_balance {
          currency_amount_value: value
          currency_amount_unit: unit
          type: __typename
        }
        required_reserve {
          ...CurrencyAmountFragment
        }
        available_balance {
          ...CurrencyAmountFragment
        }
        unconfirmed_balance {
          ...CurrencyAmountFragment
        }
        type: __typename
      }
      local_balance(bitcoin_networks: [${'$'}network], node_ids: ${'$'}nodeIds) {
        ...CurrencyAmountFragment
      }
      remote_balance(bitcoin_networks: [${'$'}network], node_ids: ${'$'}nodeIds) {
        ...CurrencyAmountFragment
      }
    }
  }
  
  ${BlockchainBalance.FRAGMENT}
  ${CurrencyAmount.FRAGMENT}
"""
