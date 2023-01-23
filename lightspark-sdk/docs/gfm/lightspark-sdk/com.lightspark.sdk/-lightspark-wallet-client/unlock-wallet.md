//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkWalletClient](index.md)/[unlockWallet](unlock-wallet.md)

# unlockWallet

[common]\
suspend fun [unlockWallet](unlock-wallet.md)(walletId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Unlocks the wallet for use with sensitive SDK operations. Also sets the active wallet to the specified walletId. This function must be called before calling any other functions that operate on the wallet.

#### Return

True if the wallet was unlocked successfully, false otherwise.

#### Parameters

common

| | |
|---|---|
| walletId | The ID of the wallet to unlock. |
| password | The password for the wallet. |
