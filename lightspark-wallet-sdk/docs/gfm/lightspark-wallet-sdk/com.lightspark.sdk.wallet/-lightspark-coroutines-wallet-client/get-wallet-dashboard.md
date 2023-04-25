//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[LightsparkCoroutinesWalletClient](index.md)/[getWalletDashboard](get-wallet-dashboard.md)

# getWalletDashboard

[common]\
suspend fun [getWalletDashboard](get-wallet-dashboard.md)(numTransactions: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20, numPaymentRequests: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 20): [WalletDashboard](../../com.lightspark.sdk.wallet.graphql/-wallet-dashboard/index.md)?

Get the dashboard overview for a Lightning wallet. Includes balance info and the most recent transactions and payment requests.

#### Return

The dashboard overview for the wallet, including balance and recent transactions and payment requests.

#### Parameters

common

| | |
|---|---|
| numTransactions | The max number of recent transactions to fetch. Defaults to 20. |
| numPaymentRequests | The max number of recent payment requests to fetch. Defaults to 20. |

#### Throws

| | |
|---|---|
| LightsparkAuthenticationException | if there is no valid authentication. |
