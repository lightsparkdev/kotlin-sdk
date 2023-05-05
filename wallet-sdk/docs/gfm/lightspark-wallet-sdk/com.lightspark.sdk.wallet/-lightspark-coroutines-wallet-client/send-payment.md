//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkCoroutinesWalletClient](index.md)/[sendPayment](send-payment.md)

# sendPayment

[common]\
suspend fun [sendPayment](send-payment.md)(destinationPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), maxFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 60): [OutgoingPayment](../../com.lightspark.sdk.wallet.model/-outgoing-payment/index.md)

Sends a payment directly to a node on the Lightning Network through the public key of the node without an invoice.

#### Return

An `OutgoingPayment` object if the payment was successful, or throws if the payment failed.

#### Parameters

common

| | |
|---|---|
| destinationPublicKey | The public key of the destination node. |
| amountMsats | The amount to pay in milli-satoshis. |
| maxFeesMsats | The maximum amount of fees that you want to pay for this payment to be sent.     As guidance, a maximum fee of 15 basis points should make almost all transactions succeed. For example,     for a transaction between 10k sats and 100k sats, this would mean a fee limit of 15 to 150 sats. |
| timeoutSecs | The timeout in seconds that we will try to make the payment. |

#### Throws

| | |
|---|---|
| LightsparkException | if the payment failed or if the wallet is locked. |
