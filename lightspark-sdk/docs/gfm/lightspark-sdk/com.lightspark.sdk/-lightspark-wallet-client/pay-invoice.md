//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkWalletClient](index.md)/[payInvoice](pay-invoice.md)

# payInvoice

[common]\
suspend fun [payInvoice](pay-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60, amount: CurrencyAmountInput? = null, maxFees: CurrencyAmountInput? = null): PayInvoiceMutation.Payment

Pay a lightning invoice from this wallet.

Note: This call will fail if the wallet is not unlocked yet via the [unlockWallet](unlock-wallet.md) function. You must successfully unlock the node with its password before calling this function.

#### Return

The payment details.

#### Parameters

common

| | |
|---|---|
| encodedInvoice | An encoded string representation of the invoice to pay. |
| timeoutSecs | The number of seconds to wait for the payment to complete. Defaults to 60. |
| amount | The amount to pay in a specified currency unit. Defaults to the full amount of the invoice. |
| maxFees | The maximum fees to pay in a specified currency unit. |

#### Throws

| | |
|---|---|
| [LightsparkException](../-lightspark-exception/index.md) | If the wallet is not unlocked yet. |
