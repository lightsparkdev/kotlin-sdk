//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[LightsparkNode](index.md)/[LightsparkNode](-lightspark-node.md)

# LightsparkNode

[common]\
fun [LightsparkNode](-lightspark-node.md)(id: String, createdAt: Instant, updatedAt: Instant, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), displayName: String, accountId: [EntityId](../-entity-id/index.md), alias: String? = null, color: String? = null, conductivity: Int? = null, publicKey: String? = null, blockchainBalance: [BlockchainBalance](../-blockchain-balance/index.md)? = null, encryptedSigningPrivateKey: [Secret](../-secret/index.md)? = null, totalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, totalLocalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, localBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, purpose: [LightsparkNodePurpose](../-lightspark-node-purpose/index.md)? = null, remoteBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, status: [LightsparkNodeStatus](../-lightspark-node-status/index.md)? = null)

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
