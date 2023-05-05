//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkWalletClient](index.md)/[unlockActiveWallet](unlock-active-wallet.md)

# unlockActiveWallet

[common]\
suspend fun [unlockActiveWallet](unlock-active-wallet.md)(password: String): Boolean

Unlocks the active wallet for use with sensitive SDK operations. This function or [unlockWallet](unlock-wallet.md) must be called before calling sensitive operations like [payInvoice](pay-invoice.md).

#### Return

True if the wallet was unlocked successfully, false otherwise.

#### Parameters

common

| | |
|---|---|
| password | The password for the wallet. |

#### Throws

| | |
|---|---|
| LightsparkException | If the wallet ID is not set yet. |
