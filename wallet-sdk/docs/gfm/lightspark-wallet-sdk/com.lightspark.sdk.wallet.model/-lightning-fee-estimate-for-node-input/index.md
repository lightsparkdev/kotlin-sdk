//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[LightningFeeEstimateForNodeInput](index.md)

# LightningFeeEstimateForNodeInput

[common]\
@Serializable

data class [LightningFeeEstimateForNodeInput](index.md)(val destinationNodePublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))

#### Parameters

common

| | |
|---|---|
| destinationNodePublicKey | The public key of the node that you want to pay. |
| amountMsats | The payment amount expressed in msats. |

## Constructors

| | |
|---|---|
| [LightningFeeEstimateForNodeInput](-lightning-fee-estimate-for-node-input.md) | [common]<br>fun [LightningFeeEstimateForNodeInput](-lightning-fee-estimate-for-node-input.md)(destinationNodePublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amountMsats: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amountMsats](amount-msats.md) | [common]<br>val [amountMsats](amount-msats.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [destinationNodePublicKey](destination-node-public-key.md) | [common]<br>val [destinationNodePublicKey](destination-node-public-key.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
