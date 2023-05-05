//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk](../index.md)/[ClientConfig](index.md)

# ClientConfig

[common]\
data class [ClientConfig](index.md)@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)constructor(var serverUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;api.lightspark.com&quot;, var defaultBitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.model/-bitcoin-network/index.md) = BitcoinNetwork.MAINNET, var authProvider: AuthProvider? = null)

## Constructors

| | |
|---|---|
| [ClientConfig](-client-config.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [ClientConfig](-client-config.md)(serverUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;api.lightspark.com&quot;, defaultBitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.model/-bitcoin-network/index.md) = BitcoinNetwork.MAINNET, authProvider: AuthProvider? = null) |

## Functions

| Name | Summary |
|---|---|
| [setAuthProvider](set-auth-provider.md) | [common]<br>fun [setAuthProvider](set-auth-provider.md)(authProvider: AuthProvider): [ClientConfig](index.md) |
| [setDefaultBitcoinNetwork](set-default-bitcoin-network.md) | [common]<br>fun [setDefaultBitcoinNetwork](set-default-bitcoin-network.md)(defaultBitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.model/-bitcoin-network/index.md)): [ClientConfig](index.md) |
| [setServerUrl](set-server-url.md) | [common]<br>fun [setServerUrl](set-server-url.md)(serverUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ClientConfig](index.md) |

## Properties

| Name | Summary |
|---|---|
| [authProvider](auth-provider.md) | [common]<br>@set:[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = &quot;$setAuthProvider&quot;)<br>var [authProvider](auth-provider.md): AuthProvider? = null |
| [defaultBitcoinNetwork](default-bitcoin-network.md) | [common]<br>@set:[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = &quot;$setDefaultBitcoinNetwork&quot;)<br>var [defaultBitcoinNetwork](default-bitcoin-network.md): [BitcoinNetwork](../../com.lightspark.sdk.model/-bitcoin-network/index.md) |
| [serverUrl](server-url.md) | [common]<br>@set:[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = &quot;$setServerUrl&quot;)<br>var [serverUrl](server-url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
