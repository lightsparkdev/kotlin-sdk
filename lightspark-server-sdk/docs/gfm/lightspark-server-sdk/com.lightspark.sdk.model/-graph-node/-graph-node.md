//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[GraphNode](index.md)/[GraphNode](-graph-node.md)

# GraphNode

[common]\
fun [GraphNode](-graph-node.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, color: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, conductivity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, publicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

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
