//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[SendPaymentInput](index.md)

# SendPaymentInput

[common]\
@Serializable

data class [SendPaymentInput](index.md)(val nodeId: String, val destinationPublicKey: String, val timeoutSecs: Int, val amountMsats: Long, val maximumFeesMsats: Long)

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
| [SendPaymentInput](-send-payment-input.md) | [common]<br>fun [SendPaymentInput](-send-payment-input.md)(nodeId: String, destinationPublicKey: String, timeoutSecs: Int, amountMsats: Long, maximumFeesMsats: Long) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountMsats](amount-msats.md) | [common]<br>val [amountMsats](amount-msats.md): Long |
| [destinationPublicKey](destination-public-key.md) | [common]<br>val [destinationPublicKey](destination-public-key.md): String |
| [maximumFeesMsats](maximum-fees-msats.md) | [common]<br>val [maximumFeesMsats](maximum-fees-msats.md): Long |
| [nodeId](node-id.md) | [common]<br>val [nodeId](node-id.md): String |
| [timeoutSecs](timeout-secs.md) | [common]<br>val [timeoutSecs](timeout-secs.md): Int |
