//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[IncomingPaymentToAttemptsConnection](index.md)/[IncomingPaymentToAttemptsConnection](-incoming-payment-to-attempts-connection.md)

# IncomingPaymentToAttemptsConnection

[common]\
fun [IncomingPaymentToAttemptsConnection](-incoming-payment-to-attempts-connection.md)(count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IncomingPaymentAttempt](../-incoming-payment-attempt/index.md)&gt;)

#### Parameters

common

| | |
|---|---|
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The incoming payment attempts for the current page of this connection. |
