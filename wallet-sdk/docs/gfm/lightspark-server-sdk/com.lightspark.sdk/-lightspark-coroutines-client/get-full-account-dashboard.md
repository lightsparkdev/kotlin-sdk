//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkCoroutinesClient](index.md)/[getFullAccountDashboard](get-full-account-dashboard.md)

# getFullAccountDashboard

[common]\
suspend fun [getFullAccountDashboard](get-full-account-dashboard.md)(bitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.model/-bitcoin-network/index.md) = defaultBitcoinNetwork, nodeIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null): [AccountDashboard](../../com.lightspark.sdk.graphql/-account-dashboard/index.md)?

Get the dashboard overview for the active account (for the auth token that initialized this client).

#### Return

The dashboard overview for the active account, including node and balance details.

#### Parameters

common

| | |
|---|---|
| bitcoinNetwork | The bitcoin network to use for the dashboard data. Defaults to the network set in the     gradle project properties. |
| nodeIds | Optional list of node IDs to filter the dashboard data to a list of nodes. |
