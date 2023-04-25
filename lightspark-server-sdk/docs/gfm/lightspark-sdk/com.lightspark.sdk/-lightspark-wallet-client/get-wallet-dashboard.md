//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkWalletClient](index.md)/[getWalletDashboard](get-wallet-dashboard.md)

# getWalletDashboard

[common]\
suspend fun [getWalletDashboard](get-wallet-dashboard.md)(numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20): [WalletDashboard](../../com.lightspark.sdk.graphql/-wallet-dashboard/index.md)?

Get the dashboard overview for the active lightning wallet. Includes balance info and the most recent transactions.

#### Return

The dashboard overview for the node, including balance and recent transactions.

#### Parameters

common

| | |
|---|---|
| numTransactions | The max number of recent transactions to fetch. Defaults to 20. |

#### Throws

| | |
|---|---|
| [LightsparkException](../-lightspark-exception/index.md) | If the wallet ID is not set yet. |
