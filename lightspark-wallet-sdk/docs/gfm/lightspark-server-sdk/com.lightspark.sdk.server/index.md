//[lightspark-server-sdk](../../index.md)/[com.lightspark.sdk.server](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ClientConfig](-client-config/index.md) | [common]<br>data class [ClientConfig](-client-config/index.md)@JvmOverloadsconstructor(var serverUrl: String = &quot;api.lightspark.com&quot;, var defaultBitcoinNetwork: [BitcoinNetwork](../com.lightspark.sdk.server.model/-bitcoin-network/index.md) = BitcoinNetwork.MAINNET, var authProvider: AuthProvider? = null) |
| [LightsparkCoroutinesClient](-lightspark-coroutines-client/index.md) | [common]<br>class [LightsparkCoroutinesClient](-lightspark-coroutines-client/index.md)<br>Main entry point for the Lightspark SDK. |
| [LightsparkFuturesClient](-lightspark-futures-client/index.md) | [commonJvmAndroid]<br>class [LightsparkFuturesClient](-lightspark-futures-client/index.md)(config: ClientConfig)<br>Main entry point for the Lightspark SDK using the Java Futures API. |
| [LightsparkSyncClient](-lightspark-sync-client/index.md) | [common]<br>class [LightsparkSyncClient](-lightspark-sync-client/index.md)(config: [ClientConfig](-client-config/index.md))<br>Main entry point for the Lightspark SDK which makes synchronous, blocking API calls. |
| [LightsparkWalletClient](-lightspark-wallet-client/index.md) | [common]<br>class [LightsparkWalletClient](-lightspark-wallet-client/index.md)(coroutinesClient: [LightsparkCoroutinesClient](-lightspark-coroutines-client/index.md))<br>Main entry point for the Lightspark Wallet SDK. |

## Functions

| Name | Summary |
|---|---|
| [execute](execute.md) | [common, commonJvmAndroid]<br>[common]<br>suspend fun &lt;[T](execute.md)&gt; Query&lt;[T](execute.md)&gt;.[execute](execute.md)(client: [LightsparkCoroutinesClient](-lightspark-coroutines-client/index.md)): [T](execute.md)?<br>[commonJvmAndroid]<br>fun &lt;[T](execute.md)&gt; Query&lt;[T](execute.md)&gt;.[execute](execute.md)(client: [LightsparkFuturesClient](-lightspark-futures-client/index.md)): &lt;Error class: unknown class&gt;&lt;[T](execute.md)?&gt; |
| [executeSync](execute-sync.md) | [common]<br>fun &lt;[T](execute-sync.md)&gt; Query&lt;[T](execute-sync.md)&gt;.[executeSync](execute-sync.md)(client: [LightsparkSyncClient](-lightspark-sync-client/index.md)): [T](execute-sync.md)? |
