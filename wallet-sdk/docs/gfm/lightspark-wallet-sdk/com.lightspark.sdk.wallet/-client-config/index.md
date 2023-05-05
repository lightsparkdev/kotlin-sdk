//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet](../index.md)/[ClientConfig](index.md)

# ClientConfig

[common]\
data class [ClientConfig](index.md)@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)constructor(var serverUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;api.lightspark.com&quot;, var authProvider: AuthProvider? = null)

## Constructors

| | |
|---|---|
| [ClientConfig](-client-config.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [ClientConfig](-client-config.md)(serverUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;api.lightspark.com&quot;, authProvider: AuthProvider? = null) |

## Functions

| Name | Summary |
|---|---|
| [setAuthProvider](set-auth-provider.md) | [common]<br>fun [setAuthProvider](set-auth-provider.md)(authProvider: AuthProvider): [ClientConfig](index.md) |
| [setServerUrl](set-server-url.md) | [common]<br>fun [setServerUrl](set-server-url.md)(serverUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ClientConfig](index.md) |

## Properties

| Name | Summary |
|---|---|
| [authProvider](auth-provider.md) | [common]<br>@set:[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = &quot;$setAuthProvider&quot;)<br>var [authProvider](auth-provider.md): AuthProvider? = null |
| [serverUrl](server-url.md) | [common]<br>@set:[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = &quot;$setServerUrl&quot;)<br>var [serverUrl](server-url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
