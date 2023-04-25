//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkCoroutinesClient](index.md)/[getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)

# getBitcoinFeeEstimate

[common]\
suspend fun [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)(bitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.server.model/-bitcoin-network/index.md) = defaultBitcoinNetwork): [FeeEstimate](../../com.lightspark.sdk.server.model/-fee-estimate/index.md)

Get the L1 fee estimate for a deposit or withdrawal.

#### Return

The fee estimate including a fast and minimum fee as [CurrencyAmount](../../com.lightspark.sdk.server.model/-currency-amount/index.md)s

#### Parameters

common

| | |
|---|---|
| bitcoinNetwork | The bitcoin network to use for the fee estimate. Defaults to the network set in the gradle     project properties. |
