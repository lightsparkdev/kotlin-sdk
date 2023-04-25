//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[RoutingTransaction](index.md)

# RoutingTransaction

[common]\
@Serializable

data class [RoutingTransaction](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val status: [TransactionStatus](../-transaction-status/index.md), val amount: [CurrencyAmount](../-currency-amount/index.md), val resolvedAt: Instant? = null, val transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val incomingChannelId: [EntityId](../-entity-id/index.md)? = null, val outgoingChannelId: [EntityId](../-entity-id/index.md)? = null, val fees: [CurrencyAmount](../-currency-amount/index.md)? = null, val failureMessage: [RichText](../-rich-text/index.md)? = null, val failureReason: [RoutingTransactionFailureReason](../-routing-transaction-failure-reason/index.md)? = null) : [LightningTransaction](../-lightning-transaction/index.md), [Transaction](../-transaction/index.md), [Entity](../-entity/index.md)

A transaction that was forwarded through a Lightspark node on the Lightning Network.

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when this transaction was initiated. |
| updatedAt | The date and time when the entity was last updated. |
| status | The current status of this transaction. |
| amount | The amount of money involved in this transaction. |
| resolvedAt | The date and time when this transaction was completed or failed. |
| transactionHash | The hash of this transaction, so it can be uniquely identified on the Lightning Network. |
| incomingChannelId | If known, the channel this transaction was received from. |
| outgoingChannelId | If known, the channel this transaction was forwarded to. |
| fees | The fees collected by the node when routing this transaction. We subtract the outgoing amount to the incoming amount to determine how much fees were collected. |
| failureMessage | If applicable, user-facing error message describing why the routing failed. |
| failureReason | If applicable, the reason why the routing failed. |

## Constructors

| | |
|---|---|
| [RoutingTransaction](-routing-transaction.md) | [common]<br>fun [RoutingTransaction](-routing-transaction.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [TransactionStatus](../-transaction-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), resolvedAt: Instant? = null, transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, incomingChannelId: [EntityId](../-entity-id/index.md)? = null, outgoingChannelId: [EntityId](../-entity-id/index.md)? = null, fees: [CurrencyAmount](../-currency-amount/index.md)? = null, failureMessage: [RichText](../-rich-text/index.md)? = null, failureReason: [RoutingTransactionFailureReason](../-routing-transaction-failure-reason/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>open override val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md) |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [failureMessage](failure-message.md) | [common]<br>val [failureMessage](failure-message.md): [RichText](../-rich-text/index.md)? = null |
| [failureReason](failure-reason.md) | [common]<br>val [failureReason](failure-reason.md): [RoutingTransactionFailureReason](../-routing-transaction-failure-reason/index.md)? = null |
| [fees](fees.md) | [common]<br>val [fees](fees.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [incomingChannelId](incoming-channel-id.md) | [common]<br>val [incomingChannelId](incoming-channel-id.md): [EntityId](../-entity-id/index.md)? = null |
| [outgoingChannelId](outgoing-channel-id.md) | [common]<br>val [outgoingChannelId](outgoing-channel-id.md): [EntityId](../-entity-id/index.md)? = null |
| [resolvedAt](resolved-at.md) | [common]<br>open override val [resolvedAt](resolved-at.md): Instant? = null |
| [status](status.md) | [common]<br>open override val [status](status.md): [TransactionStatus](../-transaction-status/index.md) |
| [transactionHash](transaction-hash.md) | [common]<br>open override val [transactionHash](transaction-hash.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
