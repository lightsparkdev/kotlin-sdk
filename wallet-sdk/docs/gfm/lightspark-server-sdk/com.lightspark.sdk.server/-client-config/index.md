//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server](../index.md)/[ClientConfig](index.md)

# ClientConfig

[common]\
data class [ClientConfig](index.md)@JvmOverloadsconstructor(var serverUrl: String = &quot;api.lightspark.com&quot;, var defaultBitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.server.model/-bitcoin-network/index.md) = BitcoinNetwork.MAINNET, var authProvider: AuthProvider? = null)

## Constructors

| | |
|---|---|
| [ClientConfig](-client-config.md) | [common]<br>@JvmOverloads<br>fun [ClientConfig](-client-config.md)(serverUrl: String = &quot;api.lightspark.com&quot;, defaultBitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.server.model/-bitcoin-network/index.md) = BitcoinNetwork.MAINNET, authProvider: AuthProvider? = null) |

## Functions

| Name | Summary |
|---|---|
| [setAuthProvider](set-auth-provider.md) | [common]<br>fun [setAuthProvider](set-auth-provider.md)(authProvider: AuthProvider): [ClientConfig](index.md) |
| [setDefaultBitcoinNetwork](set-default-bitcoin-network.md) | [common]<br>fun [setDefaultBitcoinNetwork](set-default-bitcoin-network.md)(defaultBitcoinNetwork: [BitcoinNetwork](../../com.lightspark.sdk.server.model/-bitcoin-network/index.md)): [ClientConfig](index.md) |
| [setServerUrl](set-server-url.md) | [common]<br>fun [setServerUrl](set-server-url.md)(serverUrl: String): [ClientConfig](index.md) |

## Properties

| Name | Summary |
|---|---|
| [authProvider](auth-provider.md) | [common]<br>@set:JvmName(name = &quot;$setAuthProvider&quot;)<br>var [authProvider](auth-provider.md): AuthProvider? = null |
| [defaultBitcoinNetwork](default-bitcoin-network.md) | [common]<br>@set:JvmName(name = &quot;$setDefaultBitcoinNetwork&quot;)<br>var [defaultBitcoinNetwork](default-bitcoin-network.md): [BitcoinNetwork](../../com.lightspark.sdk.server.model/-bitcoin-network/index.md) |
| [serverUrl](server-url.md) | [common]<br>@set:JvmName(name = &quot;$setServerUrl&quot;)<br>var [serverUrl](server-url.md): String |
