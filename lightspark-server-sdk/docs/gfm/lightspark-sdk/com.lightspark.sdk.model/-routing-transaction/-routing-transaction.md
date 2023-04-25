//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[RoutingTransaction](index.md)/[RoutingTransaction](-routing-transaction.md)

# RoutingTransaction

[common]\
fun [RoutingTransaction](-routing-transaction.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [TransactionStatus](../-transaction-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), resolvedAt: Instant? = null, transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, incomingChannelId: [EntityId](../-entity-id/index.md)? = null, outgoingChannelId: [EntityId](../-entity-id/index.md)? = null, fees: [CurrencyAmount](../-currency-amount/index.md)? = null, failureMessage: [RichText](../-rich-text/index.md)? = null, failureReason: [RoutingTransactionFailureReason](../-routing-transaction-failure-reason/index.md)? = null)

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
