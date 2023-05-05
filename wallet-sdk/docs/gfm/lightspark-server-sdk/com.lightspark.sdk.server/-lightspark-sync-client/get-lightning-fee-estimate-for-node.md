//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkSyncClient](index.md)/[getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)

# getLightningFeeEstimateForNode

[common]\
suspend fun [getLightningFeeEstimateForNode](get-lightning-fee-estimate-for-node.md)(nodeId: String, destinationNodePublicKey: String, amountMsats: Long): [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)

Returns an estimate of the fees that will be paid to send a payment to another Lightning node.

#### Parameters

common

| | |
|---|---|
| nodeId | The node from where you want to send the payment. |
| destinationNodePublicKey | The public key of the node that you want to pay. |
| amountMsats | The payment amount expressed in msats. |
