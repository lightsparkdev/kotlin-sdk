//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.graphql](../index.md)/[WalletDashboard](index.md)

# WalletDashboard

[common]\
@Serializable

data class [WalletDashboard](index.md)(val id: String, val displayName: String, val purpose: [LightsparkNodePurpose](../../com.lightspark.sdk.server.model/-lightspark-node-purpose/index.md)?, val color: String?, val publicKey: String?, val status: [LightsparkNodeStatus](../../com.lightspark.sdk.server.model/-lightspark-node-status/index.md)?, val addresses: List&lt;[NodeAddress](../../com.lightspark.sdk.server.model/-node-address/index.md)&gt;, val localBalance: [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)?, val remoteBalance: [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)?, val blockchainBalance: [BlockchainBalance](../../com.lightspark.sdk.server.model/-blockchain-balance/index.md)?, val recentTransactions: [AccountToTransactionsConnection](../../com.lightspark.sdk.server.model/-account-to-transactions-connection/index.md))

## Constructors

| | |
|---|---|
| [WalletDashboard](-wallet-dashboard.md) | [common]<br>fun [WalletDashboard](-wallet-dashboard.md)(id: String, displayName: String, purpose: [LightsparkNodePurpose](../../com.lightspark.sdk.server.model/-lightspark-node-purpose/index.md)?, color: String?, publicKey: String?, status: [LightsparkNodeStatus](../../com.lightspark.sdk.server.model/-lightspark-node-status/index.md)?, addresses: List&lt;[NodeAddress](../../com.lightspark.sdk.server.model/-node-address/index.md)&gt;, localBalance: [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)?, remoteBalance: [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)?, blockchainBalance: [BlockchainBalance](../../com.lightspark.sdk.server.model/-blockchain-balance/index.md)?, recentTransactions: [AccountToTransactionsConnection](../../com.lightspark.sdk.server.model/-account-to-transactions-connection/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [addresses](addresses.md) | [common]<br>val [addresses](addresses.md): List&lt;[NodeAddress](../../com.lightspark.sdk.server.model/-node-address/index.md)&gt; |
| [blockchainBalance](blockchain-balance.md) | [common]<br>val [blockchainBalance](blockchain-balance.md): [BlockchainBalance](../../com.lightspark.sdk.server.model/-blockchain-balance/index.md)? |
| [color](color.md) | [common]<br>val [color](color.md): String? |
| [displayName](display-name.md) | [common]<br>val [displayName](display-name.md): String |
| [id](id.md) | [common]<br>val [id](id.md): String |
| [localBalance](local-balance.md) | [common]<br>val [localBalance](local-balance.md): [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)? |
| [publicKey](public-key.md) | [common]<br>val [publicKey](public-key.md): String? |
| [purpose](purpose.md) | [common]<br>val [purpose](purpose.md): [LightsparkNodePurpose](../../com.lightspark.sdk.server.model/-lightspark-node-purpose/index.md)? |
| [recentTransactions](recent-transactions.md) | [common]<br>val [recentTransactions](recent-transactions.md): [AccountToTransactionsConnection](../../com.lightspark.sdk.server.model/-account-to-transactions-connection/index.md) |
| [remoteBalance](remote-balance.md) | [common]<br>val [remoteBalance](remote-balance.md): [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)? |
| [status](status.md) | [common]<br>val [status](status.md): [LightsparkNodeStatus](../../com.lightspark.sdk.server.model/-lightspark-node-status/index.md)? |
