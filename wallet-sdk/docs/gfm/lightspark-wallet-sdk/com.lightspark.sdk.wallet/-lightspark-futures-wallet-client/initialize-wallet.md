//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkFuturesWalletClient](index.md)/[initializeWallet](initialize-wallet.md)

# initializeWallet

[commonJvmAndroid]\
suspend fun [initializeWallet](initialize-wallet.md)(keyType: KeyType, signingPublicKey: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): &lt;Error class: unknown class&gt;&lt;Wallet&gt;

Initializes a wallet in the Lightspark infrastructure and syncs it to the Bitcoin network. This is an asynchronous operation, the caller should then poll the wallet frequently (or subscribe to its modifications). When this process is over, the Wallet status will change to `READY` (or `FAILED`).

#### Return

The wallet that was initialized.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| keyType | The type of key to use for the wallet. |
| signingPublicKey | The base64-encoded public key to use for signing transactions. |

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if there is no valid authentication. |
