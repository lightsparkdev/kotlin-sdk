//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkWalletClient](index.md)/[getFeeEstimate](get-fee-estimate.md)

# getFeeEstimate

[common]\
suspend fun [getFeeEstimate](get-fee-estimate.md)(bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)): [FeeEstimate](../../com.lightspark.sdk.model/-fee-estimate/index.md)

Get the fee estimate for a payment.

#### Return

The fee estimate including a fast and minimum fee as [CurrencyAmount](../../com.lightspark.sdk.model/-currency-amount/index.md)s

#### Parameters

common

| | |
|---|---|
| bitcoinNetwork | The bitcoin network to use for the fee estimate. Defaults to the network set in the gradle     project properties. |
