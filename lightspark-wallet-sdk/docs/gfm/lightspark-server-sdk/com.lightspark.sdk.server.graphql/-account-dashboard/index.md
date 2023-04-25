//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.graphql](../index.md)/[AccountDashboard](index.md)

# AccountDashboard

[common]\
@Serializable

data class [AccountDashboard](index.md)(val id: String, val name: String, val nodeConnection: [AccountNodesConnection](../-account-nodes-connection/index.md)?, val blockchainBalance: [BlockchainBalance](../../com.lightspark.sdk.server.model/-blockchain-balance/index.md), val localBalance: [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md), val remoteBalance: [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md))

## Constructors

| | |
|---|---|
| [AccountDashboard](-account-dashboard.md) | [common]<br>fun [AccountDashboard](-account-dashboard.md)(id: String, name: String, nodeConnection: [AccountNodesConnection](../-account-nodes-connection/index.md)?, blockchainBalance: [BlockchainBalance](../../com.lightspark.sdk.server.model/-blockchain-balance/index.md), localBalance: [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md), remoteBalance: [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [blockchainBalance](blockchain-balance.md) | [common]<br>val [blockchainBalance](blockchain-balance.md): [BlockchainBalance](../../com.lightspark.sdk.server.model/-blockchain-balance/index.md) |
| [id](id.md) | [common]<br>val [id](id.md): String |
| [localBalance](local-balance.md) | [common]<br>val [localBalance](local-balance.md): [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md) |
| [name](name.md) | [common]<br>val [name](name.md): String |
| [nodeConnection](node-connection.md) | [common]<br>val [nodeConnection](node-connection.md): [AccountNodesConnection](../-account-nodes-connection/index.md)? |
| [remoteBalance](remote-balance.md) | [common]<br>val [remoteBalance](remote-balance.md): [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md) |
