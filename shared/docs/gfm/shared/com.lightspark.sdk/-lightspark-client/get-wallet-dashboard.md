//[shared](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkClient](index.md)/[getWalletDashboard](get-wallet-dashboard.md)

# getWalletDashboard

[common]\
suspend fun [getWalletDashboard](get-wallet-dashboard.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20, bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK)): [WalletDashboardData](../../com.lightspark.sdk.model/-wallet-dashboard-data/index.md)?

Get the dashboard overview for a single node as a lightning wallet. Includes balance info and the most recent transactions.

#### Return

The dashboard overview for the node, including balance and recent transactions.

#### Parameters

common

| | |
|---|---|
| nodeId | The ID of the node for which to fetch the dashboard data. |
| numTransactions | The max number of recent transactions to fetch. Defaults to 20. |
| bitcoinNetwork | The bitcoin network to use for the dashboard data. Defaults to the network set in the     gradle project properties |
