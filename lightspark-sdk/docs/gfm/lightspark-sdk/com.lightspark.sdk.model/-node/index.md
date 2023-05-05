//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[Node](index.md)

# Node

[common]\
interface [Node](index.md) : [Entity](../-entity/index.md)

This interface represents a lightning node that can be connected to the Lightning Network to send and receive transactions.

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getAddressesQuery](get-addresses-query.md) | [common]<br>open fun [getAddressesQuery](get-addresses-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, types: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[NodeAddressType](../-node-address-type/index.md)&gt;? = null): Query&lt;[NodeToAddressesConnection](../-node-to-addresses-connection/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [alias](alias.md) | [common]<br>abstract val [alias](alias.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>A name that identifies the node. It has no importance in terms of operating the node, it is just a way to identify and search for commercial services or popular nodes. This alias can be changed at any time by the node operator. |
| [bitcoinNetwork](bitcoin-network.md) | [common]<br>abstract val [bitcoinNetwork](bitcoin-network.md): [BitcoinNetwork](../-bitcoin-network/index.md)<br>The Bitcoin Network this node is deployed in. |
| [color](color.md) | [common]<br>abstract val [color](color.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>A hexadecimal string that describes a color. For example &quot;#000000&quot; is black, &quot;#FFFFFF&quot; is white. It has no importance in terms of operating the node, it is just a way to visually differentiate nodes. That color can be changed at any time by the node operator. |
| [conductivity](conductivity.md) | [common]<br>abstract val [conductivity](conductivity.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?<br>A summary metric used to capture how well positioned a node is to send, receive, or route transactions efficiently. Maximizing a node's conductivity helps a node’s transactions to be capital efficient. The value is an integer ranging between 0 and 10 (bounds included). |
| [createdAt](created-at.md) | [common]<br>abstract override val [createdAt](created-at.md): Instant<br>The date and time when the entity was first created. |
| [displayName](display-name.md) | [common]<br>abstract val [displayName](display-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The name of this node in the network. It will be the most human-readable option possible, depending on the data available for this node. |
| [id](id.md) | [common]<br>abstract override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| [publicKey](public-key.md) | [common]<br>abstract val [publicKey](public-key.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>The public key of this node. It acts as a unique identifier of this node in the Lightning Network. |
| [updatedAt](updated-at.md) | [common]<br>abstract override val [updatedAt](updated-at.md): Instant<br>The date and time when the entity was last updated. |

## Inheritors

| Name |
|---|
| [GraphNode](../-graph-node/index.md) |
| [LightsparkNode](../-lightspark-node/index.md) |
