//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[OutgoingPaymentAttempt](index.md)

# OutgoingPaymentAttempt

[common]\
@Serializable

data class [OutgoingPaymentAttempt](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val status: [OutgoingPaymentAttemptStatus](../-outgoing-payment-attempt-status/index.md), val outgoingPaymentId: [EntityId](../-entity-id/index.md), val failureCode: [HtlcAttemptFailureCode](../-htlc-attempt-failure-code/index.md)? = null, val failureSourceIndex: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, val resolvedAt: Instant? = null, val amount: [CurrencyAmount](../-currency-amount/index.md)? = null, val fees: [CurrencyAmount](../-currency-amount/index.md)? = null) : [Entity](../-entity/index.md)

An attempt for a payment over a route from sender node to recipient node.

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the attempt was initiated. |
| updatedAt | The date and time when the entity was last updated. |
| status | The status of an outgoing payment attempt. |
| outgoingPaymentId | The outgoing payment for this attempt. |
| failureCode | If the payment attempt failed, then this contains the Bolt #4 failure code. |
| failureSourceIndex | If the payment attempt failed, then this contains the index of the hop at which the problem occurred. |
| resolvedAt | The time the outgoing payment attempt failed or succeeded. |
| amount | The total amount of funds required to complete a payment over this route. This value includes the cumulative fees for each hop. As a result, the attempt extended to the first-hop in the route will need to have at least this much value, otherwise the route will fail at an intermediate node due to an insufficient amount. |
| fees | The sum of the fees paid at each hop within the route of this attempt. In the case of a one-hop payment, this value will be zero as we don't need to pay a fee to ourselves. |

## Constructors

| | |
|---|---|
| [OutgoingPaymentAttempt](-outgoing-payment-attempt.md) | [common]<br>fun [OutgoingPaymentAttempt](-outgoing-payment-attempt.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [OutgoingPaymentAttemptStatus](../-outgoing-payment-attempt-status/index.md), outgoingPaymentId: [EntityId](../-entity-id/index.md), failureCode: [HtlcAttemptFailureCode](../-htlc-attempt-failure-code/index.md)? = null, failureSourceIndex: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, resolvedAt: Instant? = null, amount: [CurrencyAmount](../-currency-amount/index.md)? = null, fees: [CurrencyAmount](../-currency-amount/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getHopsQuery](get-hops-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getHopsQuery](get-hops-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null): Query&lt;[OutgoingPaymentAttemptToHopsConnection](../-outgoing-payment-attempt-to-hops-connection/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [failureCode](failure-code.md) | [common]<br>val [failureCode](failure-code.md): [HtlcAttemptFailureCode](../-htlc-attempt-failure-code/index.md)? = null |
| [failureSourceIndex](failure-source-index.md) | [common]<br>val [failureSourceIndex](failure-source-index.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null |
| [fees](fees.md) | [common]<br>val [fees](fees.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [outgoingPaymentId](outgoing-payment-id.md) | [common]<br>val [outgoingPaymentId](outgoing-payment-id.md): [EntityId](../-entity-id/index.md) |
| [resolvedAt](resolved-at.md) | [common]<br>val [resolvedAt](resolved-at.md): Instant? = null |
| [status](status.md) | [common]<br>val [status](status.md): [OutgoingPaymentAttemptStatus](../-outgoing-payment-attempt-status/index.md) |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
