//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkFuturesWalletClient](index.md)/[createBitcoinFundingAddress](create-bitcoin-funding-address.md)

# createBitcoinFundingAddress

[commonJvmAndroid]\
fun [createBitcoinFundingAddress](create-bitcoin-funding-address.md)(): &lt;Error class: unknown class&gt;&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;

Creates an L1 Bitcoin wallet address which can be used to deposit or withdraw funds from the Lightning wallet.

#### Return

The newly created L1 wallet address.

#### Throws

| | |
|---|---|
| LightsparkException | if the wallet is locked or if there's no valid auth. |
