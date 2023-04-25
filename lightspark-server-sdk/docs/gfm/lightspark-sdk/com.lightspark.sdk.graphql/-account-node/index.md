//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.graphql](../index.md)/[AccountNode](index.md)

# AccountNode

[common]\
@Serializable

data class [AccountNode](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val color: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val purpose: [LightsparkNodePurpose](../../com.lightspark.sdk.model/-lightspark-node-purpose/index.md)?, val publicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val status: [LightsparkNodeStatus](../../com.lightspark.sdk.model/-lightspark-node-status/index.md)?, val addresses: [NodeToAddressesConnection](../../com.lightspark.sdk.model/-node-to-addresses-connection/index.md), val localBalance: [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md)?, val remoteBalance: [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md)?, val blockchainBalance: [AccountNode.NodeBalances](-node-balances/index.md)?)

## Constructors

| | |
|---|---|
| [AccountNode](-account-node.md) | [common]<br>fun [AccountNode](-account-node.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), color: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), purpose: [LightsparkNodePurpose](../../com.lightspark.sdk.model/-lightspark-node-purpose/index.md)?, publicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, status: [LightsparkNodeStatus](../../com.lightspark.sdk.model/-lightspark-node-status/index.md)?, addresses: [NodeToAddressesConnection](../../com.lightspark.sdk.model/-node-to-addresses-connection/index.md), localBalance: [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md)?, remoteBalance: [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md)?, blockchainBalance: [AccountNode.NodeBalances](-node-balances/index.md)?) |

## Types

| Name | Summary |
|---|---|
| [NodeBalances](-node-balances/index.md) | [common]<br>@Serializable<br>data class [NodeBalances](-node-balances/index.md)(val availableBalance: [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md)?, val totalBalance: [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md)?) |

## Properties

| Name | Summary |
|---|---|
| [addresses](addresses.md) | [common]<br>val [addresses](addresses.md): [NodeToAddressesConnection](../../com.lightspark.sdk.model/-node-to-addresses-connection/index.md) |
| [blockchainBalance](blockchain-balance.md) | [common]<br>val [blockchainBalance](blockchain-balance.md): [AccountNode.NodeBalances](-node-balances/index.md)? |
| [color](color.md) | [common]<br>val [color](color.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [displayName](display-name.md) | [common]<br>val [displayName](display-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [common]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [localBalance](local-balance.md) | [common]<br>val [localBalance](local-balance.md): [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md)? |
| [publicKey](public-key.md) | [common]<br>val [publicKey](public-key.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [purpose](purpose.md) | [common]<br>val [purpose](purpose.md): [LightsparkNodePurpose](../../com.lightspark.sdk.model/-lightspark-node-purpose/index.md)? |
| [remoteBalance](remote-balance.md) | [common]<br>val [remoteBalance](remote-balance.md): [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md)? |
| [status](status.md) | [common]<br>val [status](status.md): [LightsparkNodeStatus](../../com.lightspark.sdk.model/-lightspark-node-status/index.md)? |
