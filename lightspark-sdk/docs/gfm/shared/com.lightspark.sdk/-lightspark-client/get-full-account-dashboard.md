//[shared](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkClient](index.md)/[getFullAccountDashboard](get-full-account-dashboard.md)

# getFullAccountDashboard

[common]\
suspend fun [getFullAccountDashboard](get-full-account-dashboard.md)(bitcoinNetwork: BitcoinNetwork = BitcoinNetwork.safeValueOf(BuildKonfig.BITCOIN_NETWORK), afterDate: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? = null, beforeDate: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? = null, nodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, nodeIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null): DashboardOverviewQuery.Current_account?

Get the dashboard overview for the active account (for the auth token that initialized this client).

#### Return

The dashboard overview for the active account, including node and balance details.

#### Parameters

common

| | |
|---|---|
| bitcoinNetwork | The bitcoin network to use for the dashboard data. Defaults to the network set in the     gradle project properties. |
| afterDate | Optional date to filter the dashboard data to only include transactions after this date. |
| beforeDate | Optional date to filter the dashboard data to only include transactions before this date. |
| nodeId | Optional node ID to filter the dashboard data to a single node. |
| nodeIds | Optional list of node IDs to filter the dashboard data to a list of nodes. |
