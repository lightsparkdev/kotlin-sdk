//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkCoroutinesWalletClient](index.md)/[deployWalletAndAwaitDeployed](deploy-wallet-and-await-deployed.md)

# deployWalletAndAwaitDeployed

[common]\
suspend fun [deployWalletAndAwaitDeployed](deploy-wallet-and-await-deployed.md)(): Flow&lt;[Wallet](../../com.lightspark.sdk.wallet.model/-wallet/index.md)&gt;

Deploys a wallet in the Lightspark infrastructure and triggers updates as state changes. This is an asynchronous operation, which will continue sending the wallet state updates until the Wallet status changes to `DEPLOYED` (or `FAILED`).

#### Return

The a flow which emits when wallet status changes.

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if there is no valid authentication. |
