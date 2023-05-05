//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[OutgoingPaymentToAttemptsConnection](index.md)/[OutgoingPaymentToAttemptsConnection](-outgoing-payment-to-attempts-connection.md)

# OutgoingPaymentToAttemptsConnection

[common]\
fun [OutgoingPaymentToAttemptsConnection](-outgoing-payment-to-attempts-connection.md)(count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[OutgoingPaymentAttempt](../-outgoing-payment-attempt/index.md)&gt;)

#### Parameters

common

| | |
|---|---|
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The attempts for the current page of this connection. |
