//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkSyncWalletClient](index.md)/[payInvoice](pay-invoice.md)

# payInvoice

[common]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [payInvoice](pay-invoice.md)(encodedInvoice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), maxFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60): [OutgoingPayment](../../com.lightspark.sdk.wallet.model/-outgoing-payment/index.md)

Pay a lightning invoice from the current wallet.

Note: This call will fail if the wallet is not unlocked yet via [loadWalletSigningKey](load-wallet-signing-key.md). You must successfully unlock the wallet before calling this function.

#### Return

The payment details.

#### Parameters

common

| | |
|---|---|
| encodedInvoice | An encoded string representation of the invoice to pay. |
| maxFeesMsats | The maximum fees to pay in milli-satoshis. You must pass a value.     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats. |
| amountMsats | The amount to pay in milli-satoshis. Defaults to the full amount of the invoice. |
| timeoutSecs | The number of seconds to wait for the payment to complete. Defaults to 60. |

#### Throws

| | |
|---|---|
| LightsparkException | If the payment fails or if the wallet is locked. |
