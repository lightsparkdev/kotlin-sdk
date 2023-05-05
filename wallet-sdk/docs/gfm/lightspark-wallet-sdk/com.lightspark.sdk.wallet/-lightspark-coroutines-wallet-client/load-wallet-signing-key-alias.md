//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkCoroutinesWalletClient](index.md)/[loadWalletSigningKeyAlias](load-wallet-signing-key-alias.md)

# loadWalletSigningKeyAlias

[common]\
fun [loadWalletSigningKeyAlias](load-wallet-signing-key-alias.md)(signingKeyAlias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Unlocks the wallet for use with the SDK for the current application session by specifying a key alias in the KeyStore where the wallet's key is stored.

This function or [loadWalletSigningKey](load-wallet-signing-key.md) must be called before any other functions that require wallet signing keys, including [payInvoice](pay-invoice.md).

This function is intended for use in cases where the wallet's private signing key is already saved by the application outside of the SDK. It is the responsibility of the application to ensure that the key is valid and that it is the correct key for the wallet. Otherwise signed requests will fail.

#### Parameters

common

| | |
|---|---|
| signingKeyAlias | The key alias in the KeyStore of the wallet's private signing key. |

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if the user is not authenticated. |
