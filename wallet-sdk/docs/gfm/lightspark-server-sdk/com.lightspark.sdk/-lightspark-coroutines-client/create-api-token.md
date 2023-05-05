//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[LightsparkCoroutinesClient](index.md)/[createApiToken](create-api-token.md)

# createApiToken

[common]\
suspend fun [createApiToken](create-api-token.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), transact: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), testMode: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [CreateApiTokenOutput](../../com.lightspark.sdk.model/-create-api-token-output/index.md)

Create a new API token for the current account.

#### Return

The newly created token.

#### Parameters

common

| | |
|---|---|
| name | Creates a new API token that can be used to authenticate requests for this account when using the     Lightspark APIs and SDKs. |
| transact | Whether the token should be able to transact or only view data. |
| testMode | True if the token should be able to access only testnet false to access only mainnet. |
