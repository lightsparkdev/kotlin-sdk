//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkWalletClient](index.md)/[lockWallet](lock-wallet.md)

# lockWallet

[common]\
fun [lockWallet](lock-wallet.md)()

Locks the active wallet if needed to prevent payment until the password is entered again and passed to [unlockWallet](unlock-wallet.md).

#### Throws

| | |
|---|---|
| LightsparkException | If the wallet ID is not set yet. |
