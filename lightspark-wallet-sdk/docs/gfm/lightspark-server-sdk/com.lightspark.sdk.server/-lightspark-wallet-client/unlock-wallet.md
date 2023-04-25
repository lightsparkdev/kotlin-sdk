//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkWalletClient](index.md)/[unlockWallet](unlock-wallet.md)

# unlockWallet

[common]\
suspend fun [unlockWallet](unlock-wallet.md)(walletId: String, password: String): Boolean

Unlocks the wallet for use with sensitive SDK operations. Also sets the active wallet to the specified walletId. This function must be called before calling any other functions that operate on the wallet.

#### Return

True if the wallet was unlocked successfully, false otherwise.

#### Parameters

common

| | |
|---|---|
| walletId | The ID of the wallet to unlock. |
| password | The password for the wallet. |
