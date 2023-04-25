//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[CreateApiTokenOutput](index.md)

# CreateApiTokenOutput

[common]\
@Serializable

data class [CreateApiTokenOutput](index.md)(val apiToken: [ApiToken](../-api-token/index.md), val clientSecret: String)

#### Parameters

common

| | |
|---|---|
| apiToken | The API Token that has been created. |
| clientSecret | The secret that should be used to authenticate against our API. This secret is not stored and will never be available again after this. You must keep this secret secure as it grants access to your account. |

## Constructors

| | |
|---|---|
| [CreateApiTokenOutput](-create-api-token-output.md) | [common]<br>fun [CreateApiTokenOutput](-create-api-token-output.md)(apiToken: [ApiToken](../-api-token/index.md), clientSecret: String) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [apiToken](api-token.md) | [common]<br>val [apiToken](api-token.md): [ApiToken](../-api-token/index.md) |
| [clientSecret](client-secret.md) | [common]<br>val [clientSecret](client-secret.md): String |
