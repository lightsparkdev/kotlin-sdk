//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[CreateApiTokenInput](index.md)

# CreateApiTokenInput

[common]\
@Serializable

data class [CreateApiTokenInput](index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val permissions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Permission](../-permission/index.md)&gt;)

#### Parameters

common

| | |
|---|---|
| name | An arbitrary name that the user can choose to identify the API token in a list. |
| permissions | List of permissions to grant to the API token |

## Constructors

| | |
|---|---|
| [CreateApiTokenInput](-create-api-token-input.md) | [common]<br>fun [CreateApiTokenInput](-create-api-token-input.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), permissions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Permission](../-permission/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [permissions](permissions.md) | [common]<br>val [permissions](permissions.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Permission](../-permission/index.md)&gt; |
