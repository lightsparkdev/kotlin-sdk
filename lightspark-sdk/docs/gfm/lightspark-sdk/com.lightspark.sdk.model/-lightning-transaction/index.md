//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[LightningTransaction](index.md)

# LightningTransaction

[common]\
interface [LightningTransaction](index.md) : [Transaction](../-transaction/index.md), [Entity](../-entity/index.md)

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>abstract override val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md)<br>The amount of money involved in this transaction. |
| [createdAt](created-at.md) | [common]<br>abstract override val [createdAt](created-at.md): Instant<br>The date and time when this transaction was initiated. |
| [id](id.md) | [common]<br>abstract override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| [resolvedAt](resolved-at.md) | [common]<br>abstract override val [resolvedAt](resolved-at.md): Instant?<br>The date and time when this transaction was completed or failed. |
| [status](status.md) | [common]<br>abstract override val [status](status.md): [TransactionStatus](../-transaction-status/index.md)<br>The current status of this transaction. |
| [transactionHash](transaction-hash.md) | [common]<br>abstract override val [transactionHash](transaction-hash.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>The hash of this transaction, so it can be uniquely identified on the Lightning Network. |
| [updatedAt](updated-at.md) | [common]<br>abstract override val [updatedAt](updated-at.md): Instant<br>The date and time when the entity was last updated. |

## Inheritors

| Name |
|---|
| [IncomingPayment](../-incoming-payment/index.md) |
| [OutgoingPayment](../-outgoing-payment/index.md) |
| [RoutingTransaction](../-routing-transaction/index.md) |
