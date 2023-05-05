//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkFuturesClient](index.md)/[getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)

# getLightningFeeEstimateForNode

[commonJvmAndroid]\
suspend fun [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinationNodePublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): &lt;Error class: unknown class&gt;&lt;CurrencyAmount&gt;

Returns an estimate of the fees that will be paid to send a payment to another Lightning node.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| nodeId | The node from where you want to send the payment. |
| destinationNodePublicKey | The public key of the node that you want to pay. |
| amountMsats | The payment amount expressed in msats. |
