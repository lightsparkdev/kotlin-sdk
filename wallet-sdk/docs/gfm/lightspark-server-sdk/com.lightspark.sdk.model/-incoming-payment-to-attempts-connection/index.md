//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[IncomingPaymentToAttemptsConnection](index.md)

# IncomingPaymentToAttemptsConnection

[common]\
@Serializable

data class [IncomingPaymentToAttemptsConnection](index.md)(val count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IncomingPaymentAttempt](../-incoming-payment-attempt/index.md)&gt;)

The connection from incoming payment to all attempts.

#### Parameters

common

| | |
|---|---|
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The incoming payment attempts for the current page of this connection. |

## Constructors

| | |
|---|---|
| [IncomingPaymentToAttemptsConnection](-incoming-payment-to-attempts-connection.md) | [common]<br>fun [IncomingPaymentToAttemptsConnection](-incoming-payment-to-attempts-connection.md)(count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IncomingPaymentAttempt](../-incoming-payment-attempt/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [count](count.md) | [common]<br>val [count](count.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [entities](entities.md) | [common]<br>val [entities](entities.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IncomingPaymentAttempt](../-incoming-payment-attempt/index.md)&gt; |
