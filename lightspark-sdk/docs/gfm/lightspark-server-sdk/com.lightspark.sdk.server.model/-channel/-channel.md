//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[Channel](index.md)/[Channel](-channel.md)

# Channel

[common]\
fun [Channel](-channel.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, localNodeId: [EntityId](../-entity-id/index.md), fundingTransactionId: [EntityId](../-entity-id/index.md)? = null, capacity: [CurrencyAmount](../-currency-amount/index.md)? = null, localBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, localUnsettledBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, remoteBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, remoteUnsettledBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, unsettledBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, totalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, status: [ChannelStatus](../-channel-status/index.md)? = null, estimatedForceClosureWaitMinutes: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, commitFee: [CurrencyAmount](../-currency-amount/index.md)? = null, fees: [ChannelFees](../-channel-fees/index.md)? = null, remoteNodeId: [EntityId](../-entity-id/index.md)? = null, shortChannelId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| localNodeId | The local Lightspark node of the channel. |
| fundingTransactionId | The transaction that funded the channel upon channel opening. |
| capacity | The total amount of funds in this channel, including the channel balance on the local node, the channel balance on the remote node and the on-chain fees to close the channel. |
| localBalance | The channel balance on the local node. |
| localUnsettledBalance | The channel balance on the local node that is currently allocated to in-progress payments. |
| remoteBalance | The channel balance on the remote node. |
| remoteUnsettledBalance | The channel balance on the remote node that is currently allocated to in-progress payments. |
| unsettledBalance | The channel balance that is currently allocated to in-progress payments. |
| totalBalance | The total balance in this channel, including the channel balance on both local and remote nodes. |
| status | The current status of this channel. |
| estimatedForceClosureWaitMinutes | The estimated time to wait for the channel's hash timelock contract to expire when force closing the channel. It is in unit of minutes. |
| commitFee | The amount to be paid in fees for the current set of commitment transactions. |
| fees | The fees charged for routing payments through this channel. |
| remoteNodeId | If known, the remote node of the channel. |
| shortChannelId | The unique identifier of the channel on Lightning Network, which is the location in the chain that the channel was confirmed. The format is ::. |
