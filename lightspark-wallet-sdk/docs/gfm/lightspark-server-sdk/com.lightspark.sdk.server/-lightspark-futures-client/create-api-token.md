//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[LightsparkFuturesClient](index.md)/[createApiToken](create-api-token.md)

# createApiToken

[commonJvmAndroid]\
suspend fun [createApiToken](create-api-token.md)(name: String, transact: Boolean, testMode: Boolean): &lt;Error class: unknown class&gt;&lt;CreateApiTokenOutput&gt;

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
