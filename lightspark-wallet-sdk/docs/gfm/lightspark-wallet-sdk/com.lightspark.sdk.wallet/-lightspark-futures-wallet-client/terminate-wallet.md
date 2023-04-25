//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkFuturesWalletClient](index.md)/[terminateWallet](terminate-wallet.md)

# terminateWallet

[commonJvmAndroid]\
suspend fun [terminateWallet](terminate-wallet.md)(): &lt;Error class: unknown class&gt;&lt;Wallet?&gt;

Removes the wallet from Lightspark infrastructure. It won't be connected to the Lightning network anymore and its funds won't be accessible outside of the Funds Recovery Kit process.

#### Return

The wallet that was terminated.

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if there is no valid authentication. |
