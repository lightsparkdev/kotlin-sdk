//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[OutgoingPaymentAttempt](index.md)/[amount](amount.md)

# amount

[common]\
val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md)? = null

#### Parameters

common

| | |
|---|---|
| amount | The total amount of funds required to complete a payment over this route. This value includes the cumulative fees for each hop. As a result, the attempt extended to the first-hop in the route will need to have at least this much value, otherwise the route will fail at an intermediate node due to an insufficient amount. |
