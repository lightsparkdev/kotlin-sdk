//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkFuturesWalletClient](index.md)/[deployWallet](deploy-wallet.md)

# deployWallet

[commonJvmAndroid]\
suspend fun [deployWallet](deploy-wallet.md)(): &lt;Error class: unknown class&gt;&lt;Wallet&gt;

Deploys a wallet in the Lightspark infrastructure. This is an asynchronous operation, the caller should then poll the wallet frequently (or subscribe to its modifications). When this process is over, the Wallet status will change to `DEPLOYED` (or `FAILED`).

#### Return

The wallet that was deployed.

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if there is no valid authentication. |
