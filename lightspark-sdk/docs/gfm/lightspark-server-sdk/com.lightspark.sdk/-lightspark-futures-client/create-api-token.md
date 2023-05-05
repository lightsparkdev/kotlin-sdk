//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkFuturesClient](index.md)/[createApiToken](create-api-token.md)

# createApiToken

[commonJvmAndroid]\
suspend fun [createApiToken](create-api-token.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), transact: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), testMode: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): &lt;Error class: unknown class&gt;&lt;CreateApiTokenOutput&gt;

Create a new API token for the current account.

#### Return

The newly created token.

#### Parameters

commonJvmAndroid

| | |
|---|---|
| name | Creates a new API token that can be used to authenticate requests for this account when using the     Lightspark APIs and SDKs. |
| transact | Whether the token should be able to transact or only view data. |
| testMode | True if the token should be able to access only testnet false to access only mainnet. |
