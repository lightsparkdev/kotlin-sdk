//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[LightsparkNode](index.md)

# LightsparkNode

[common]\
@Serializable

data class [LightsparkNode](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), val displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val accountId: [EntityId](../-entity-id/index.md), val alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val color: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val conductivity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, val publicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val blockchainBalance: [BlockchainBalance](../-blockchain-balance/index.md)? = null, val encryptedSigningPrivateKey: [Secret](../-secret/index.md)? = null, val totalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val totalLocalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val localBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val purpose: [LightsparkNodePurpose](../-lightspark-node-purpose/index.md)? = null, val remoteBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val status: [LightsparkNodeStatus](../-lightspark-node-status/index.md)? = null) : [Node](../-node/index.md), [Entity](../-entity/index.md)

This is a node that is managed by Lightspark and is managed within the current connected account. It contains many details about the node configuration, state, and metadata.

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| bitcoinNetwork | The Bitcoin Network this node is deployed in. |
| displayName | The name of this node in the network. It will be the most human-readable option possible, depending on the data available for this node. |
| accountId | The account that owns this LightsparkNode. |
| alias | A name that identifies the node. It has no importance in terms of operating the node, it is just a way to identify and search for commercial services or popular nodes. This alias can be changed at any time by the node operator. |
| color | A hexadecimal string that describes a color. For example &quot;#000000&quot; is black, &quot;#FFFFFF&quot; is white. It has no importance in terms of operating the node, it is just a way to visually differentiate nodes. That color can be changed at any time by the node operator. |
| conductivity | A summary metric used to capture how well positioned a node is to send, receive, or route transactions efficiently. Maximizing a node's conductivity helps a node’s transactions to be capital efficient. The value is an integer ranging between 0 and 10 (bounds included). |
| publicKey | The public key of this node. It acts as a unique identifier of this node in the Lightning Network. |
| blockchainBalance | The details of the balance of this node on the Bitcoin Network. |
| encryptedSigningPrivateKey | The private key client is using to sign a GraphQL request which will be verified at LND. |
| totalBalance | The sum of the balance on the Bitcoin Network, channel balances, and commit fees on this node. |
| totalLocalBalance | The total sum of the channel balances (online and offline) on this node. |
| localBalance | The sum of the channel balances (online only) that are available to send on this node. |
| purpose | The main purpose of this node. It is used by Lightspark for optimizations on the node's channels. |
| remoteBalance | The sum of the channel balances that are available to receive on this node. |
| status | The current status of this node. |

## Constructors

| | |
|---|---|
| [LightsparkNode](-lightspark-node.md) | [common]<br>fun [LightsparkNode](-lightspark-node.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), accountId: [EntityId](../-entity-id/index.md), alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, color: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, conductivity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, publicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, blockchainBalance: [BlockchainBalance](../-blockchain-balance/index.md)? = null, encryptedSigningPrivateKey: [Secret](../-secret/index.md)? = null, totalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, totalLocalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, localBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, purpose: [LightsparkNodePurpose](../-lightspark-node-purpose/index.md)? = null, remoteBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, status: [LightsparkNodeStatus](../-lightspark-node-status/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getAddressesQuery](get-addresses-query.md) | [common]<br>open override fun [getAddressesQuery](get-addresses-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, types: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[NodeAddressType](../-node-address-type/index.md)&gt;?): [Query](../../com.lightspark.sdk.requester/-query/index.md)&lt;[NodeToAddressesConnection](../-node-to-addresses-connection/index.md)&gt; |
| [getChannelsQuery](get-channels-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getChannelsQuery](get-channels-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, statuses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ChannelStatus](../-channel-status/index.md)&gt;? = null): [Query](../../com.lightspark.sdk.requester/-query/index.md)&lt;[LightsparkNodeToChannelsConnection](../-lightspark-node-to-channels-connection/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [accountId](account-id.md) | [common]<br>val [accountId](account-id.md): [EntityId](../-entity-id/index.md) |
| [alias](alias.md) | [common]<br>open override val [alias](alias.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [bitcoinNetwork](bitcoin-network.md) | [common]<br>open override val [bitcoinNetwork](bitcoin-network.md): [BitcoinNetwork](../-bitcoin-network/index.md) |
| [blockchainBalance](blockchain-balance.md) | [common]<br>val [blockchainBalance](blockchain-balance.md): [BlockchainBalance](../-blockchain-balance/index.md)? = null |
| [color](color.md) | [common]<br>open override val [color](color.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [conductivity](conductivity.md) | [common]<br>open override val [conductivity](conductivity.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [displayName](display-name.md) | [common]<br>open override val [displayName](display-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [encryptedSigningPrivateKey](encrypted-signing-private-key.md) | [common]<br>val [encryptedSigningPrivateKey](encrypted-signing-private-key.md): [Secret](../-secret/index.md)? = null |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [localBalance](local-balance.md) | [common]<br>val [localBalance](local-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [publicKey](public-key.md) | [common]<br>open override val [publicKey](public-key.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [purpose](purpose.md) | [common]<br>val [purpose](purpose.md): [LightsparkNodePurpose](../-lightspark-node-purpose/index.md)? = null |
| [remoteBalance](remote-balance.md) | [common]<br>val [remoteBalance](remote-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [status](status.md) | [common]<br>val [status](status.md): [LightsparkNodeStatus](../-lightspark-node-status/index.md)? = null |
| [totalBalance](total-balance.md) | [common]<br>val [totalBalance](total-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [totalLocalBalance](total-local-balance.md) | [common]<br>val [totalLocalBalance](total-local-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
