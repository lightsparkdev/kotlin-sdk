//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[CreateApiTokenInput](index.md)

# CreateApiTokenInput

[common]\
@Serializable

data class [CreateApiTokenInput](index.md)(val name: String, val permissions: List&lt;[Permission](../-permission/index.md)&gt;)

#### Parameters

common

| | |
|---|---|
| name | An arbitrary name that the user can choose to identify the API token in a list. |
| permissions | List of permissions to grant to the API token |

## Constructors

| | |
|---|---|
| [CreateApiTokenInput](-create-api-token-input.md) | [common]<br>fun [CreateApiTokenInput](-create-api-token-input.md)(name: String, permissions: List&lt;[Permission](../-permission/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [name](name.md) | [common]<br>val [name](name.md): String |
| [permissions](permissions.md) | [common]<br>val [permissions](permissions.md): List&lt;[Permission](../-permission/index.md)&gt; |
