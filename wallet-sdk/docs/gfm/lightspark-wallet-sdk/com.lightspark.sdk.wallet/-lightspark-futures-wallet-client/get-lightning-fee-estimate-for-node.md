//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkFuturesWalletClient](index.md)/[getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)

# getLightningFeeEstimateForNode

[commonJvmAndroid]\
suspend fun [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)(destinationNodePublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): &lt;Error class: unknown class&gt;&lt;CurrencyAmount&gt;

Returns an estimate of the fees that will be paid to send a payment to another Lightning node.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| destinationNodePublicKey | The public key of the node that you want to pay. |
| amountMsats | The payment amount expressed in msats. |

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | If the user is not authenticated. |
