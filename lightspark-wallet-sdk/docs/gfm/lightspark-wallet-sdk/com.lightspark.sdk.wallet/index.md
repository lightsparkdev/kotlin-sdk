//[lightspark-wallet-sdk](../../index.md)/[com.lightspark.sdk.wallet](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ClientConfig](-client-config/index.md) | [common]<br>data class [ClientConfig](-client-config/index.md)@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)constructor(var serverUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;api.lightspark.com&quot;, var authProvider: AuthProvider? = null) |
| [LightsparkCoroutinesWalletClient](-lightspark-coroutines-wallet-client/index.md) | [common]<br>class [LightsparkCoroutinesWalletClient](-lightspark-coroutines-wallet-client/index.md)<br>Main entry point for the Lightspark Wallet SDK. |
| [LightsparkFuturesWalletClient](-lightspark-futures-wallet-client/index.md) | [commonJvmAndroid]<br>class [LightsparkFuturesWalletClient](-lightspark-futures-wallet-client/index.md)(config: ClientConfig)<br>Main entry point for the Lightspark SDK using the Java Futures API. |
| [LightsparkSyncWalletClient](-lightspark-sync-wallet-client/index.md) | [common]<br>class [LightsparkSyncWalletClient](-lightspark-sync-wallet-client/index.md)(config: [ClientConfig](-client-config/index.md))<br>Main entry point for the Lightspark SDK which makes synchronous, blocking API calls. |

## Functions

| Name | Summary |
|---|---|
| [execute](execute.md) | [common]<br>suspend fun &lt;[T](execute.md)&gt; Query&lt;[T](execute.md)&gt;.[execute](execute.md)(client: [LightsparkCoroutinesWalletClient](-lightspark-coroutines-wallet-client/index.md)): [T](execute.md)? |
| [executeSync](execute-sync.md) | [common]<br>fun &lt;[T](execute-sync.md)&gt; Query&lt;[T](execute-sync.md)&gt;.[executeSync](execute-sync.md)(client: [LightsparkSyncWalletClient](-lightspark-sync-wallet-client/index.md)): [T](execute-sync.md)? |
