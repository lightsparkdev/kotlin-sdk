//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[SendPaymentInput](index.md)/[SendPaymentInput](-send-payment-input.md)

# SendPaymentInput

[common]\
fun [SendPaymentInput](-send-payment-input.md)(destinationPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), maximumFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))

#### Parameters

common

| | |
|---|---|
| destinationPublicKey | The public key of the destination node. |
| timeoutSecs | The timeout in seconds that we will try to make the payment. |
| amountMsats | The amount you will send to the destination node, expressed in msats. |
| maximumFeesMsats | The maximum amount of fees that you want to pay for this payment to be sent, expressed in msats. |
