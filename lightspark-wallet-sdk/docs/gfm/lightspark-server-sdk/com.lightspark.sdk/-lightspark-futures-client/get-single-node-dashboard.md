//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkFuturesClient](index.md)/[getSingleNodeDashboard](get-single-node-dashboard.md)

# getSingleNodeDashboard

[commonJvmAndroid]\
fun [getSingleNodeDashboard](get-single-node-dashboard.md)(nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20, bitcoinNetwork: BitcoinNetwork = defaultBitcoinNetwork): &lt;Error class: unknown class&gt;&lt;WalletDashboard?&gt;

Get the dashboard overview for a single node as a lightning wallet. Includes balance info and the most recent transactions.

#### Return

The dashboard overview for the node, including balance and recent transactions.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| nodeId | The ID of the node for which to fetch the dashboard data. |
| numTransactions | The max number of recent transactions to fetch. Defaults to 20. |
| bitcoinNetwork | The bitcoin network to use for the dashboard data. Defaults to the network set in the     gradle project properties |
