//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkSyncClient](index.md)/[getSingleNodeDashboard](get-single-node-dashboard.md)

# getSingleNodeDashboard

[common]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [getSingleNodeDashboard](get-single-node-dashboard.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20, bitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.model/-bitcoin-network/index.md) = defaultBitcoinNetwork): [WalletDashboard](../../com.lightspark.sdk.graphql/-wallet-dashboard/index.md)?

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
