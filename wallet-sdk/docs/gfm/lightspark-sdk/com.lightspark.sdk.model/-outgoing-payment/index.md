//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[OutgoingPayment](index.md)

# OutgoingPayment

[common]\
@Serializable

data class [OutgoingPayment](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val status: [TransactionStatus](../-transaction-status/index.md), val amount: [CurrencyAmount](../-currency-amount/index.md), val originId: [EntityId](../-entity-id/index.md), val resolvedAt: Instant? = null, val transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val destinationId: [EntityId](../-entity-id/index.md)? = null, val fees: [CurrencyAmount](../-currency-amount/index.md)? = null, val paymentRequestData: [PaymentRequestData](../-payment-request-data/index.md)? = null, val failureReason: [PaymentFailureReason](../-payment-failure-reason/index.md)? = null, val failureMessage: [RichText](../-rich-text/index.md)? = null) : [LightningTransaction](../-lightning-transaction/index.md), [Transaction](../-transaction/index.md), [Entity](../-entity/index.md)

A transaction that was sent from a Lightspark node on the Lightning Network.

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when this transaction was initiated. |
| updatedAt | The date and time when the entity was last updated. |
| status | The current status of this transaction. |
| amount | The amount of money involved in this transaction. |
| originId | The Lightspark node this payment originated from. |
| resolvedAt | The date and time when this transaction was completed or failed. |
| transactionHash | The hash of this transaction, so it can be uniquely identified on the Lightning Network. |
| destinationId | If known, the final recipient node this payment was sent to. |
| fees | The fees paid by the sender node to send the payment. |
| paymentRequestData | The data of the payment request that was paid by this transaction, if known. |
| failureReason | If applicable, the reason why the payment failed. |
| failureMessage | If applicable, user-facing error message describing why the payment failed. |

## Constructors

| | |
|---|---|
| [OutgoingPayment](-outgoing-payment.md) | [common]<br>fun [OutgoingPayment](-outgoing-payment.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [TransactionStatus](../-transaction-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), originId: [EntityId](../-entity-id/index.md), resolvedAt: Instant? = null, transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, destinationId: [EntityId](../-entity-id/index.md)? = null, fees: [CurrencyAmount](../-currency-amount/index.md)? = null, paymentRequestData: [PaymentRequestData](../-payment-request-data/index.md)? = null, failureReason: [PaymentFailureReason](../-payment-failure-reason/index.md)? = null, failureMessage: [RichText](../-rich-text/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getAttemptsQuery](get-attempts-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getAttemptsQuery](get-attempts-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null): [Query](../../com.lightspark.sdk.requester/-query/index.md)&lt;[OutgoingPaymentToAttemptsConnection](../-outgoing-payment-to-attempts-connection/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>open override val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md) |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [destinationId](destination-id.md) | [common]<br>val [destinationId](destination-id.md): [EntityId](../-entity-id/index.md)? = null |
| [failureMessage](failure-message.md) | [common]<br>val [failureMessage](failure-message.md): [RichText](../-rich-text/index.md)? = null |
| [failureReason](failure-reason.md) | [common]<br>val [failureReason](failure-reason.md): [PaymentFailureReason](../-payment-failure-reason/index.md)? = null |
| [fees](fees.md) | [common]<br>val [fees](fees.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [originId](origin-id.md) | [common]<br>val [originId](origin-id.md): [EntityId](../-entity-id/index.md) |
| [paymentRequestData](payment-request-data.md) | [common]<br>val [paymentRequestData](payment-request-data.md): [PaymentRequestData](../-payment-request-data/index.md)? = null |
| [resolvedAt](resolved-at.md) | [common]<br>open override val [resolvedAt](resolved-at.md): Instant? = null |
| [status](status.md) | [common]<br>open override val [status](status.md): [TransactionStatus](../-transaction-status/index.md) |
| [transactionHash](transaction-hash.md) | [common]<br>open override val [transactionHash](transaction-hash.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
