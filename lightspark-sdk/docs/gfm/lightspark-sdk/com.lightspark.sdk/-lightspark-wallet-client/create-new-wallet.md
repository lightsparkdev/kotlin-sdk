//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkWalletClient](index.md)/[createNewWallet](create-new-wallet.md)

# createNewWallet

[common]\
suspend fun [createNewWallet](create-new-wallet.md)(password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK))

Creates a new wallet for the user. This will create a new node on the server.

#### Parameters

common

| | |
|---|---|
| password | The password used to encrypt the wallet's keys. |
| bitcoinNetwork | The bitcoin network to use for the dashboard data. Defaults to the network set in the     gradle project properties |
