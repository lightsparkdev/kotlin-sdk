//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[IncomingPaymentAttempt](index.md)/[IncomingPaymentAttempt](-incoming-payment-attempt.md)

# IncomingPaymentAttempt

[common]\
fun [IncomingPaymentAttempt](-incoming-payment-attempt.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [IncomingPaymentAttemptStatus](../-incoming-payment-attempt-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), channelId: [EntityId](../-entity-id/index.md), resolvedAt: Instant? = null)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| status | The status of the incoming payment attempt. |
| amount | The total amount of that was attempted to send. |
| channelId | The channel this attempt was made on. |
| resolvedAt | The time the incoming payment attempt failed or succeeded. |
