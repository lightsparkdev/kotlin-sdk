//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[OutgoingPaymentAttempt](index.md)/[OutgoingPaymentAttempt](-outgoing-payment-attempt.md)

# OutgoingPaymentAttempt

[common]\
fun [OutgoingPaymentAttempt](-outgoing-payment-attempt.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [OutgoingPaymentAttemptStatus](../-outgoing-payment-attempt-status/index.md), outgoingPaymentId: [EntityId](../-entity-id/index.md), failureCode: [HtlcAttemptFailureCode](../-htlc-attempt-failure-code/index.md)? = null, failureSourceIndex: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, resolvedAt: Instant? = null, amount: [CurrencyAmount](../-currency-amount/index.md)? = null, fees: [CurrencyAmount](../-currency-amount/index.md)? = null)

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
