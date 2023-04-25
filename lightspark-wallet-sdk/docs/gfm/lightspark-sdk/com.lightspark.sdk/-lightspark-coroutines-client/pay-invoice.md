//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkCoroutinesClient](index.md)/[payInvoice](pay-invoice.md)

# payInvoice

[common]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

suspend fun [payInvoice](pay-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), maxFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60): [OutgoingPayment](../../com.lightspark.sdk.model/-outgoing-payment/index.md)

Pay a lightning invoice for the given node.

Note: This call will fail if the node sending the payment is not unlocked yet via the [recoverNodeSigningKey](recover-node-signing-key.md) function. You must successfully unlock the node with its password before calling this function.

#### Return

The payment details.

#### Parameters

common

| | |
|---|---|
| nodeId | The ID of the node which will pay the invoice. |
| encodedInvoice | An encoded string representation of the invoice to pay. |
| maxFeesMsats | The maximum fees to pay in milli-satoshis. You must pass a value.     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats. |
| amountMsats | The amount to pay in milli-satoshis. Defaults to the full amount of the invoice. |
| timeoutSecs | The number of seconds to wait for the payment to complete. Defaults to 60. |
