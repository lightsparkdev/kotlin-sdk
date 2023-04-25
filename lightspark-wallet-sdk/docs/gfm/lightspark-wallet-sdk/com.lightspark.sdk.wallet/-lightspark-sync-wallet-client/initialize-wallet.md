//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkSyncWalletClient](index.md)/[initializeWallet](initialize-wallet.md)

# initializeWallet

[common]\
fun [initializeWallet](initialize-wallet.md)(keyType: [KeyType](../../com.lightspark.sdk.wallet.model/-key-type/index.md), signingPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Wallet](../../com.lightspark.sdk.wallet.model/-wallet/index.md)?

Initializes a wallet in the Lightspark infrastructure and syncs it to the Bitcoin network. This is an asynchronous operation, the caller should then poll the wallet frequently (or subscribe to its modifications). When this process is over, the Wallet status will change to `READY` (or `FAILED`).

#### Return

The wallet that was initialized.

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
