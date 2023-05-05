//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkFuturesClient](index.md)/[getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)

# getBitcoinFeeEstimate

[commonJvmAndroid]\
fun [getBitcoinFeeEstimate](get-bitcoin-fee-estimate.md)(bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork): &lt;Error class: unknown class&gt;&lt;FeeEstimate&gt;

Get the L1 fee estimate for a deposit or withdrawal.

#### Return

The fee estimate including a fast and minimum fee as CurrencyAmounts

#### Parameters

commonJvmAndroid

| | |
|---|---|
| bitcoinNetwork | The bitcoin network to use for the fee estimate. Defaults to the network set in the gradle     project properties. |
