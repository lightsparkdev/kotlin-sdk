//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[OutgoingPaymentAttemptToHopsConnection](index.md)

# OutgoingPaymentAttemptToHopsConnection

[common]\
@Serializable

data class [OutgoingPaymentAttemptToHopsConnection](index.md)(val count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Hop](../-hop/index.md)&gt;)

The connection from an outgoing payment attempt to the list of sequential hops that define the path from sender node to recipient node.

#### Parameters

common

| | |
|---|---|
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The hops for the current page of this connection. |

## Constructors

| | |
|---|---|
| [OutgoingPaymentAttemptToHopsConnection](-outgoing-payment-attempt-to-hops-connection.md) | [common]<br>fun [OutgoingPaymentAttemptToHopsConnection](-outgoing-payment-attempt-to-hops-connection.md)(count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Hop](../-hop/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [count](count.md) | [common]<br>val [count](count.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [entities](entities.md) | [common]<br>val [entities](entities.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Hop](../-hop/index.md)&gt; |
