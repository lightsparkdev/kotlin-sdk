//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[GraphNode](index.md)

# GraphNode

[common]\
@Serializable

data class [GraphNode](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), val displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val color: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val conductivity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, val publicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) : [Node](../-node/index.md), [Entity](../-entity/index.md)

This is a node on the Lightning Network, managed by a third party. The information about this node is public data that has been obtained by observing the Lightning Network.

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| bitcoinNetwork | The Bitcoin Network this node is deployed in. |
| displayName | The name of this node in the network. It will be the most human-readable option possible, depending on the data available for this node. |
| alias | A name that identifies the node. It has no importance in terms of operating the node, it is just a way to identify and search for commercial services or popular nodes. This alias can be changed at any time by the node operator. |
| color | A hexadecimal string that describes a color. For example &quot;#000000&quot; is black, &quot;#FFFFFF&quot; is white. It has no importance in terms of operating the node, it is just a way to visually differentiate nodes. That color can be changed at any time by the node operator. |
| conductivity | A summary metric used to capture how well positioned a node is to send, receive, or route transactions efficiently. Maximizing a node's conductivity helps a node’s transactions to be capital efficient. The value is an integer ranging between 0 and 10 (bounds included). |
| publicKey | The public key of this node. It acts as a unique identifier of this node in the Lightning Network. |

## Constructors

| | |
|---|---|
| [GraphNode](-graph-node.md) | [common]<br>fun [GraphNode](-graph-node.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, color: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, conductivity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, publicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getAddressesQuery](get-addresses-query.md) | [common]<br>open override fun [getAddressesQuery](get-addresses-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?, types: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[NodeAddressType](../-node-address-type/index.md)&gt;?): Query&lt;[NodeToAddressesConnection](../-node-to-addresses-connection/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [alias](alias.md) | [common]<br>open override val [alias](alias.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [bitcoinNetwork](bitcoin-network.md) | [common]<br>open override val [bitcoinNetwork](bitcoin-network.md): [BitcoinNetwork](../-bitcoin-network/index.md) |
| [color](color.md) | [common]<br>open override val [color](color.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [conductivity](conductivity.md) | [common]<br>open override val [conductivity](conductivity.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [displayName](display-name.md) | [common]<br>open override val [displayName](display-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [publicKey](public-key.md) | [common]<br>open override val [publicKey](public-key.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |