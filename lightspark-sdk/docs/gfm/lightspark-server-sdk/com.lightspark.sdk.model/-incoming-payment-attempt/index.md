//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[IncomingPaymentAttempt](index.md)

# IncomingPaymentAttempt

[common]\
@Serializable

data class [IncomingPaymentAttempt](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val status: [IncomingPaymentAttemptStatus](../-incoming-payment-attempt-status/index.md), val amount: [CurrencyAmount](../-currency-amount/index.md), val channelId: [EntityId](../-entity-id/index.md), val resolvedAt: Instant? = null) : [Entity](../-entity/index.md)

An attempt for a payment over a route from sender node to recipient node.

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

## Constructors

| | |
|---|---|
| [IncomingPaymentAttempt](-incoming-payment-attempt.md) | [common]<br>fun [IncomingPaymentAttempt](-incoming-payment-attempt.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [IncomingPaymentAttemptStatus](../-incoming-payment-attempt-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), channelId: [EntityId](../-entity-id/index.md), resolvedAt: Instant? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md) |
| [channelId](channel-id.md) | [common]<br>val [channelId](channel-id.md): [EntityId](../-entity-id/index.md) |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [resolvedAt](resolved-at.md) | [common]<br>val [resolvedAt](resolved-at.md): Instant? = null |
| [status](status.md) | [common]<br>val [status](status.md): [IncomingPaymentAttemptStatus](../-incoming-payment-attempt-status/index.md) |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
