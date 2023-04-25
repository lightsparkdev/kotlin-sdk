//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkCoroutinesWalletClient](index.md)/[deployWallet](deploy-wallet.md)

# deployWallet

[common]\
suspend fun [deployWallet](deploy-wallet.md)(): [Wallet](../../com.lightspark.sdk.wallet.model/-wallet/index.md)?

Deploys a wallet in the Lightspark infrastructure. This is an asynchronous operation, the caller should then poll the wallet frequently (or subscribe to its modifications). When this process is over, the Wallet status will change to `DEPLOYED` (or `FAILED`).

#### Return

The wallet that was deployed.

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if there is no valid authentication. |
