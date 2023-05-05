//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkWalletClient](index.md)/[setActiveWalletWithoutUnlocking](set-active-wallet-without-unlocking.md)

# setActiveWalletWithoutUnlocking

[common]\
fun [setActiveWalletWithoutUnlocking](set-active-wallet-without-unlocking.md)(walletId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)

Sets the active wallet, but does not attempt to unlock it. If the wallet is not unlocked, sensitive operations like [payInvoice](pay-invoice.md) will fail.
