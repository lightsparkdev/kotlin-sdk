//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkCoroutinesWalletClient](index.md)/[initializeWalletAndWaitForInitialized](initialize-wallet-and-wait-for-initialized.md)

# initializeWalletAndWaitForInitialized

[common]\
suspend fun [initializeWalletAndWaitForInitialized](initialize-wallet-and-wait-for-initialized.md)(keyType: [KeyType](../../com.lightspark.sdk.wallet.model/-key-type/index.md), signingPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Flow&lt;[Wallet](../../com.lightspark.sdk.wallet.model/-wallet/index.md)&gt;

Initializes a wallet in the Lightspark infrastructure and syncs it to the Bitcoin network and triggers updates as state changes. This is an asynchronous operation, which will continue sending the wallet state updates until the Wallet status changes to `READY` (or `FAILED`).

#### Return

A flow of Wallet updates.

#### Parameters

common

| | |
|---|---|
| keyType | The type of key to use for the wallet. |
| signingPublicKey | The base64-encoded public key to use for signing transactions. |

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if there is no valid authentication. |
