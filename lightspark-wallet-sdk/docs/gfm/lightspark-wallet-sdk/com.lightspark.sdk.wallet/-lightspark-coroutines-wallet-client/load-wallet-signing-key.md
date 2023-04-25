//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkCoroutinesWalletClient](index.md)/[loadWalletSigningKey](load-wallet-signing-key.md)

# loadWalletSigningKey

[common]\
fun [loadWalletSigningKey](load-wallet-signing-key.md)(signingKeyBytesPEM: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html))

Unlocks the wallet for use with the SDK for the current application session. This function must be called before any other functions that require wallet signing keys, including [payInvoice](pay-invoice.md).

This function is intended for use in cases where the wallet's private signing key is already saved by the application outside of the SDK. It is the responsibility of the application to ensure that the key is valid and that it is the correct key for the wallet. Otherwise signed requests will fail.

#### Parameters

common

| | |
|---|---|
| signingKeyBytesPEM | The PEM encoded bytes of the wallet's private signing key. |

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if the user is not authenticated. |
