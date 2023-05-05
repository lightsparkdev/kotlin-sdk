//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[IncomingPayment](index.md)

# IncomingPayment

[common]\
@Serializable

data class [IncomingPayment](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val status: [TransactionStatus](../-transaction-status/index.md), val amount: [CurrencyAmount](../-currency-amount/index.md), val destinationId: [EntityId](../-entity-id/index.md), val resolvedAt: Instant? = null, val transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val originId: [EntityId](../-entity-id/index.md)? = null, val paymentRequestId: [EntityId](../-entity-id/index.md)? = null) : [LightningTransaction](../-lightning-transaction/index.md), [Transaction](../-transaction/index.md), [Entity](../-entity/index.md)

A transaction that was sent to a Lightspark node on the Lightning Network.

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when this transaction was initiated. |
| updatedAt | The date and time when the entity was last updated. |
| status | The current status of this transaction. |
| amount | The amount of money involved in this transaction. |
| destinationId | The recipient Lightspark node this payment was sent to. |
| resolvedAt | The date and time when this transaction was completed or failed. |
| transactionHash | The hash of this transaction, so it can be uniquely identified on the Lightning Network. |
| originId | If known, the Lightspark node this payment originated from. |
| paymentRequestId | The optional payment request for this incoming payment, which will be null if the payment is sent through keysend. |

## Constructors

| | |
|---|---|
| [IncomingPayment](-incoming-payment.md) | [common]<br>fun [IncomingPayment](-incoming-payment.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [TransactionStatus](../-transaction-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), destinationId: [EntityId](../-entity-id/index.md), resolvedAt: Instant? = null, transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, originId: [EntityId](../-entity-id/index.md)? = null, paymentRequestId: [EntityId](../-entity-id/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getAttemptsQuery](get-attempts-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getAttemptsQuery](get-attempts-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, statuses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IncomingPaymentAttemptStatus](../-incoming-payment-attempt-status/index.md)&gt;? = null): Query&lt;[IncomingPaymentToAttemptsConnection](../-incoming-payment-to-attempts-connection/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>open override val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md) |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [destinationId](destination-id.md) | [common]<br>val [destinationId](destination-id.md): [EntityId](../-entity-id/index.md) |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [originId](origin-id.md) | [common]<br>val [originId](origin-id.md): [EntityId](../-entity-id/index.md)? = null |
| [paymentRequestId](payment-request-id.md) | [common]<br>val [paymentRequestId](payment-request-id.md): [EntityId](../-entity-id/index.md)? = null |
| [resolvedAt](resolved-at.md) | [common]<br>open override val [resolvedAt](resolved-at.md): Instant? = null |
| [status](status.md) | [common]<br>open override val [status](status.md): [TransactionStatus](../-transaction-status/index.md) |
| [transactionHash](transaction-hash.md) | [common]<br>open override val [transactionHash](transaction-hash.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
