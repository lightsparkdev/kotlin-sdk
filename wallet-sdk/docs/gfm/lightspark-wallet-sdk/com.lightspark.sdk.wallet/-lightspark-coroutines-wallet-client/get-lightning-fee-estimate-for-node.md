//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkCoroutinesWalletClient](index.md)/[getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)

# getLightningFeeEstimateForNode

[common]\
suspend fun [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)(destinationNodePublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [CurrencyAmount](../../com.lightspark.sdk.wallet.model/-currency-amount/index.md)

Returns an estimate of the fees that will be paid to send a payment to another Lightning node.

#### Parameters

common

| | |
|---|---|
| destinationNodePublicKey | The public key of the node that you want to pay. |
| amountMsats | The payment amount expressed in msats. |

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | If the user is not authenticated. |
