//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[SendPaymentInput](index.md)

# SendPaymentInput

[common]\
@Serializable

data class [SendPaymentInput](index.md)(val nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val destinationPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val maximumFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))

#### Parameters

common

| | |
|---|---|
| nodeId | The node from where you want to send the payment. |
| destinationPublicKey | The public key of the destination node. |
| timeoutSecs | The timeout in seconds that we will try to make the payment. |
| amountMsats | The amount you will send to the destination node, expressed in msats. |
| maximumFeesMsats | The maximum amount of fees that you want to pay for this payment to be sent, expressed in msats. |

## Constructors

| | |
|---|---|
| [SendPaymentInput](-send-payment-input.md) | [common]<br>fun [SendPaymentInput](-send-payment-input.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinationPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), timeoutSecs: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), maximumFeesMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountMsats](amount-msats.md) | [common]<br>val [amountMsats](amount-msats.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [destinationPublicKey](destination-public-key.md) | [common]<br>val [destinationPublicKey](destination-public-key.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [maximumFeesMsats](maximum-fees-msats.md) | [common]<br>val [maximumFeesMsats](maximum-fees-msats.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [nodeId](node-id.md) | [common]<br>val [nodeId](node-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [timeoutSecs](timeout-secs.md) | [common]<br>val [timeoutSecs](timeout-secs.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
