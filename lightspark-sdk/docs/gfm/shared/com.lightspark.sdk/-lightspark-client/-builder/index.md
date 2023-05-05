//[shared](../../../../index.md)/[com.lightspark.sdk](../../index.md)/[LightsparkClient](../index.md)/[Builder](index.md)

# Builder

[common]\
class [Builder](index.md)

The Builder class for [LightsparkClient](../index.md) and the main entry point for the SDK.

```kotlin
// Initialize the client with account token info:
val lightsparkClient = LightsparkClient.Builder().apply {
    tokenId = "your-token-id"
    token = "your-secret-token"
}.build()
```

#### Return

A [LightsparkClient](../index.md) instance.

#### Parameters

common

| | |
|---|---|
| serverUrl | The URL of the Lightspark server to connect to. Defaults to a value set in gradle project properties. |
| tokenId | The token ID to use for authentication. Defaults to a value set in gradle project properties. You can find     this value in the Lightspark dashboard. |
| token | The token to use for authentication. Defaults to a value set in gradle project properties. You can find this     value in the Lightspark dashboard. |

## Constructors

| | |
|---|---|
| [Builder](-builder.md) | [common]<br>fun [Builder](-builder.md)() |

## Functions

| Name | Summary |
|---|---|
| [build](build.md) | [common]<br>fun [build](build.md)(): [LightsparkClient](../index.md) |
| [serverUrl](server-url.md) | [common]<br>fun [serverUrl](server-url.md)(serverUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LightsparkClient.Builder](index.md) |
| [token](token.md) | [common]<br>fun [token](token.md)(token: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LightsparkClient.Builder](index.md) |
| [tokenId](token-id.md) | [common]<br>fun [tokenId](token-id.md)(tokenId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LightsparkClient.Builder](index.md) |
