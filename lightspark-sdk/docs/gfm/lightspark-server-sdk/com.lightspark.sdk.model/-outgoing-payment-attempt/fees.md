//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[OutgoingPaymentAttempt](index.md)/[fees](fees.md)

# fees

[common]\
val [fees](fees.md): [CurrencyAmount](../-currency-amount/index.md)? = null

#### Parameters

common

| | |
|---|---|
| fees | The sum of the fees paid at each hop within the route of this attempt. In the case of a one-hop payment, this value will be zero as we don't need to pay a fee to ourselves. |
