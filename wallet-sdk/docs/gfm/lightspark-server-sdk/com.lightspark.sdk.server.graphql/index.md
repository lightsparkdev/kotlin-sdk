//[lightspark-server-sdk](../../index.md)/[com.lightspark.sdk.server.graphql](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AccountDashboard](-account-dashboard/index.md) | [common]<br>@Serializable<br>data class [AccountDashboard](-account-dashboard/index.md)(val id: String, val name: String, val nodeConnection: [AccountNodesConnection](-account-nodes-connection/index.md)?, val blockchainBalance: [BlockchainBalance](../com.lightspark.sdk.server.model/-blockchain-balance/index.md), val localBalance: [CurrencyAmount](../com.lightspark.sdk.server.model/-currency-amount/index.md), val remoteBalance: [CurrencyAmount](../com.lightspark.sdk.server.model/-currency-amount/index.md)) |
| [AccountNode](-account-node/index.md) | [common]<br>@Serializable<br>data class [AccountNode](-account-node/index.md)(val id: String, val color: String?, val displayName: String, val purpose: [LightsparkNodePurpose](../com.lightspark.sdk.server.model/-lightspark-node-purpose/index.md)?, val publicKey: String?, val status: [LightsparkNodeStatus](../com.lightspark.sdk.server.model/-lightspark-node-status/index.md)?, val addresses: [NodeToAddressesConnection](../com.lightspark.sdk.server.model/-node-to-addresses-connection/index.md), val localBalance: [CurrencyAmount](../com.lightspark.sdk.server.model/-currency-amount/index.md)?, val remoteBalance: [CurrencyAmount](../com.lightspark.sdk.server.model/-currency-amount/index.md)?, val blockchainBalance: [AccountNode.NodeBalances](-account-node/-node-balances/index.md)?) |
| [AccountNodesConnection](-account-nodes-connection/index.md) | [common]<br>@Serializable<br>data class [AccountNodesConnection](-account-nodes-connection/index.md)(val nodes: List&lt;[AccountNode](-account-node/index.md)&gt;, val count: Int) |
| [WalletDashboard](-wallet-dashboard/index.md) | [common]<br>@Serializable<br>data class [WalletDashboard](-wallet-dashboard/index.md)(val id: String, val displayName: String, val purpose: [LightsparkNodePurpose](../com.lightspark.sdk.server.model/-lightspark-node-purpose/index.md)?, val color: String?, val publicKey: String?, val status: [LightsparkNodeStatus](../com.lightspark.sdk.server.model/-lightspark-node-status/index.md)?, val addresses: List&lt;[NodeAddress](../com.lightspark.sdk.server.model/-node-address/index.md)&gt;, val localBalance: [CurrencyAmount](../com.lightspark.sdk.server.model/-currency-amount/index.md)?, val remoteBalance: [CurrencyAmount](../com.lightspark.sdk.server.model/-currency-amount/index.md)?, val blockchainBalance: [BlockchainBalance](../com.lightspark.sdk.server.model/-blockchain-balance/index.md)?, val recentTransactions: [AccountToTransactionsConnection](../com.lightspark.sdk.server.model/-account-to-transactions-connection/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [AccountDashboardQuery](-account-dashboard-query.md) | [common]<br>const val [AccountDashboardQuery](-account-dashboard-query.md): String |
| [BitcoinFeeEstimateQuery](-bitcoin-fee-estimate-query.md) | [common]<br>const val [BitcoinFeeEstimateQuery](-bitcoin-fee-estimate-query.md): String |
| [CreateApiTokenMutation](-create-api-token-mutation.md) | [common]<br>const val [CreateApiTokenMutation](-create-api-token-mutation.md): String |
| [CreateInvoiceMutation](-create-invoice-mutation.md) | [common]<br>const val [CreateInvoiceMutation](-create-invoice-mutation.md): String |
| [CreateNodeWalletAddressMutation](-create-node-wallet-address-mutation.md) | [common]<br>const val [CreateNodeWalletAddressMutation](-create-node-wallet-address-mutation.md): String |
| [CurrentAccountQuery](-current-account-query.md) | [common]<br>const val [CurrentAccountQuery](-current-account-query.md): String |
| [DecodeInvoiceQuery](-decode-invoice-query.md) | [common]<br>const val [DecodeInvoiceQuery](-decode-invoice-query.md): String |
| [DeleteApiTokenMutation](-delete-api-token-mutation.md) | [common]<br>const val [DeleteApiTokenMutation](-delete-api-token-mutation.md): String |
| [FundNodeMutation](-fund-node-mutation.md) | [common]<br>const val [FundNodeMutation](-fund-node-mutation.md): String |
| [LightningFeeEstimateForInvoiceQuery](-lightning-fee-estimate-for-invoice-query.md) | [common]<br>const val [LightningFeeEstimateForInvoiceQuery](-lightning-fee-estimate-for-invoice-query.md): String |
| [LightningFeeEstimateForNodeQuery](-lightning-fee-estimate-for-node-query.md) | [common]<br>const val [LightningFeeEstimateForNodeQuery](-lightning-fee-estimate-for-node-query.md): String |
| [PayInvoiceMutation](-pay-invoice-mutation.md) | [common]<br>const val [PayInvoiceMutation](-pay-invoice-mutation.md): String |
| [RecoverNodeSigningKeyQuery](-recover-node-signing-key-query.md) | [common]<br>const val [RecoverNodeSigningKeyQuery](-recover-node-signing-key-query.md): String |
| [RequestWithdrawalMutation](-request-withdrawal-mutation.md) | [common]<br>const val [RequestWithdrawalMutation](-request-withdrawal-mutation.md): String |
| [SendPaymentMutation](-send-payment-mutation.md) | [common]<br>const val [SendPaymentMutation](-send-payment-mutation.md): String |
| [SingleNodeDashboardQuery](-single-node-dashboard-query.md) | [common]<br>const val [SingleNodeDashboardQuery](-single-node-dashboard-query.md): String |
| [TransactionsForNode](-transactions-for-node.md) | [common]<br>val [TransactionsForNode](-transactions-for-node.md): String |
| [TransactionSubscriptionQuery](-transaction-subscription-query.md) | [common]<br>const val [TransactionSubscriptionQuery](-transaction-subscription-query.md): String |
| [WithdrawFundsQuery](-withdraw-funds-query.md) | [common]<br>const val [WithdrawFundsQuery](-withdraw-funds-query.md): String |
