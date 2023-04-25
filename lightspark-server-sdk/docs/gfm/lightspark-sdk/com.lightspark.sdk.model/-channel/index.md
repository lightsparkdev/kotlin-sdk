//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[Channel](index.md)

# Channel

[common]\
@Serializable

data class [Channel](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val localNodeId: [EntityId](../-entity-id/index.md), val fundingTransactionId: [EntityId](../-entity-id/index.md)? = null, val capacity: [CurrencyAmount](../-currency-amount/index.md)? = null, val localBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val localUnsettledBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val remoteBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val remoteUnsettledBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val unsettledBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val totalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, val status: [ChannelStatus](../-channel-status/index.md)? = null, val estimatedForceClosureWaitMinutes: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, val commitFee: [CurrencyAmount](../-currency-amount/index.md)? = null, val fees: [ChannelFees](../-channel-fees/index.md)? = null, val remoteNodeId: [EntityId](../-entity-id/index.md)? = null, val shortChannelId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) : [Entity](../-entity/index.md)

An object that represents a payment channel between two nodes in the Lightning Network.

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

## Constructors

| | |
|---|---|
| [Channel](-channel.md) | [common]<br>fun [Channel](-channel.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, localNodeId: [EntityId](../-entity-id/index.md), fundingTransactionId: [EntityId](../-entity-id/index.md)? = null, capacity: [CurrencyAmount](../-currency-amount/index.md)? = null, localBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, localUnsettledBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, remoteBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, remoteUnsettledBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, unsettledBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, totalBalance: [CurrencyAmount](../-currency-amount/index.md)? = null, status: [ChannelStatus](../-channel-status/index.md)? = null, estimatedForceClosureWaitMinutes: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, commitFee: [CurrencyAmount](../-currency-amount/index.md)? = null, fees: [ChannelFees](../-channel-fees/index.md)? = null, remoteNodeId: [EntityId](../-entity-id/index.md)? = null, shortChannelId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getTransactionsQuery](get-transactions-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getTransactionsQuery](get-transactions-query.md)(types: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TransactionType](../-transaction-type/index.md)&gt;? = null, afterDate: Instant? = null, beforeDate: Instant? = null): [Query](../../com.lightspark.sdk.requester/-query/index.md)&lt;[ChannelToTransactionsConnection](../-channel-to-transactions-connection/index.md)&gt; |
| [getUptimePercentageQuery](get-uptime-percentage-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getUptimePercentageQuery](get-uptime-percentage-query.md)(afterDate: Instant? = null, beforeDate: Instant? = null): [Query](../../com.lightspark.sdk.requester/-query/index.md)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?&gt; |

## Properties

| Name | Summary |
|---|---|
| [capacity](capacity.md) | [common]<br>val [capacity](capacity.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [commitFee](commit-fee.md) | [common]<br>val [commitFee](commit-fee.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [estimatedForceClosureWaitMinutes](estimated-force-closure-wait-minutes.md) | [common]<br>val [estimatedForceClosureWaitMinutes](estimated-force-closure-wait-minutes.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null |
| [fees](fees.md) | [common]<br>val [fees](fees.md): [ChannelFees](../-channel-fees/index.md)? = null |
| [fundingTransactionId](funding-transaction-id.md) | [common]<br>val [fundingTransactionId](funding-transaction-id.md): [EntityId](../-entity-id/index.md)? = null |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [localBalance](local-balance.md) | [common]<br>val [localBalance](local-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [localNodeId](local-node-id.md) | [common]<br>val [localNodeId](local-node-id.md): [EntityId](../-entity-id/index.md) |
| [localUnsettledBalance](local-unsettled-balance.md) | [common]<br>val [localUnsettledBalance](local-unsettled-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [remoteBalance](remote-balance.md) | [common]<br>val [remoteBalance](remote-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [remoteNodeId](remote-node-id.md) | [common]<br>val [remoteNodeId](remote-node-id.md): [EntityId](../-entity-id/index.md)? = null |
| [remoteUnsettledBalance](remote-unsettled-balance.md) | [common]<br>val [remoteUnsettledBalance](remote-unsettled-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [shortChannelId](short-channel-id.md) | [common]<br>val [shortChannelId](short-channel-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [status](status.md) | [common]<br>val [status](status.md): [ChannelStatus](../-channel-status/index.md)? = null |
| [totalBalance](total-balance.md) | [common]<br>val [totalBalance](total-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [unsettledBalance](unsettled-balance.md) | [common]<br>val [unsettledBalance](unsettled-balance.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
