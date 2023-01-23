//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkWalletClient](index.md)/[getWalletDashboard](get-wallet-dashboard.md)

# getWalletDashboard

[common]\
suspend fun [getWalletDashboard](get-wallet-dashboard.md)(numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20, bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)): [WalletDashboardData](../../com.lightspark.sdk.model/-wallet-dashboard-data/index.md)?

Get the dashboard overview for the active lightning wallet. Includes balance info and the most recent transactions.

#### Return

The dashboard overview for the node, including balance and recent transactions.

#### Parameters

common

| | |
|---|---|
| numTransactions | The max number of recent transactions to fetch. Defaults to 20. |
| bitcoinNetwork | The bitcoin network to use for the dashboard data. Defaults to the network set in the     gradle project properties |

#### Throws

| | |
|---|---|
| [LightsparkException](../-lightspark-exception/index.md) | If the wallet ID is not set yet. |
