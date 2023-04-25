//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkClient](index.md)/[payInvoice](pay-invoice.md)

# payInvoice

[common]\
suspend fun [payInvoice](pay-invoice.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60, amount: CurrencyAmountInput? = null, maxFees: CurrencyAmountInput? = null): PayInvoiceMutation.Payment

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
| timeoutSecs | The number of seconds to wait for the payment to complete. Defaults to 60. |
| amount | The amount to pay in a specified currency unit. Defaults to the full amount of the invoice. |
| maxFees | The maximum fees to pay in a specified currency unit. |
