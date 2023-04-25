//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkSyncClient](index.md)/[getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)

# getBitcoinFeeEstimate

[common]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)(bitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.model/-bitcoin-network/index.md) = defaultBitcoinNetwork): [FeeEstimate](../../com.lightspark.sdk.model/-fee-estimate/index.md)

Get the L1 fee estimate for a deposit or withdrawal.

#### Return

The fee estimate including a fast and minimum fee as [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md)s

#### Parameters

common

| | |
|---|---|
| bitcoinNetwork | The bitcoin network to use for the fee estimate. Defaults to the network set in the gradle     project properties. |
