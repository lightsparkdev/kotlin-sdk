//[lightspark-sdk](../../index.md)/[com.lightspark.sdk](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ClientConfig](-client-config/index.md) | [common]<br>data class [ClientConfig](-client-config/index.md)@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)constructor(var serverUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;api.lightspark.com&quot;, var defaultBitcoinNetwork: [BitcoinNetwork](../com.lightspark.sdk.model/-bitcoin-network/index.md) = BitcoinNetwork.MAINNET, var authProvider: [AuthProvider](../com.lightspark.sdk.auth/-auth-provider/index.md)? = null) |
| [Lce](-lce/index.md) | [common]<br>interface [Lce](-lce/index.md)&lt;out [T](-lce/index.md)&gt; |
| [LightsparkCoroutinesClient](-lightspark-coroutines-client/index.md) | [common]<br>class [LightsparkCoroutinesClient](-lightspark-coroutines-client/index.md)<br>Main entry point for the Lightspark SDK. |
| [LightsparkErrorCode](-lightspark-error-code/index.md) | [common]<br>enum [LightsparkErrorCode](-lightspark-error-code/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[LightsparkErrorCode](-lightspark-error-code/index.md)&gt; |
| [LightsparkException](-lightspark-exception/index.md) | [common]<br>open class [LightsparkException](-lightspark-exception/index.md)(val message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val errorCode: [LightsparkErrorCode](-lightspark-error-code/index.md), val cause: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? = null) : [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)<br>A generic exception thrown by the Lightspark SDK. |
| [LightsparkFuturesClient](-lightspark-futures-client/index.md) | [commonJvmAndroid]<br>class [LightsparkFuturesClient](-lightspark-futures-client/index.md)(config: ClientConfig)<br>Main entry point for the Lightspark SDK using the Java Futures API. |
| [LightsparkSyncClient](-lightspark-sync-client/index.md) | [common]<br>class [LightsparkSyncClient](-lightspark-sync-client/index.md)(config: [ClientConfig](-client-config/index.md))<br>Main entry point for the Lightspark SDK which makes synchronous, blocking API calls. |
| [LightsparkWalletClient](-lightspark-wallet-client/index.md) | [common]<br>class [LightsparkWalletClient](-lightspark-wallet-client/index.md)(coroutinesClient: [LightsparkCoroutinesClient](-lightspark-coroutines-client/index.md))<br>Main entry point for the Lightspark Wallet SDK. |

## Functions

| Name | Summary |
|---|---|
| [asLce](as-lce.md) | [common]<br>fun &lt;[T](as-lce.md)&gt; Flow&lt;[T](as-lce.md)&gt;.[asLce](as-lce.md)(): Flow&lt;[Lce](-lce/index.md)&lt;[T](as-lce.md)&gt;&gt; |
| [execute](execute.md) | [common, commonJvmAndroid]<br>[common]<br>suspend fun &lt;[T](execute.md)&gt; [Query](../com.lightspark.sdk.requester/-query/index.md)&lt;[T](execute.md)&gt;.[execute](execute.md)(client: [LightsparkCoroutinesClient](-lightspark-coroutines-client/index.md)): [T](execute.md)?<br>[commonJvmAndroid]<br>fun &lt;[T](execute.md)&gt; [Query](../com.lightspark.sdk.requester/-query/index.md#-168528822%2FExtensions%2F1699347959)&lt;[T](execute.md)&gt;.[execute](execute.md)(client: [LightsparkFuturesClient](-lightspark-futures-client/index.md)): &lt;Error class: unknown class&gt;&lt;[T](execute.md)?&gt; |
| [executeSync](execute-sync.md) | [common]<br>fun &lt;[T](execute-sync.md)&gt; [Query](../com.lightspark.sdk.requester/-query/index.md)&lt;[T](execute-sync.md)&gt;.[executeSync](execute-sync.md)(client: [LightsparkSyncClient](-lightspark-sync-client/index.md)): [T](execute-sync.md)? |
| [wrapWithLceFlow](wrap-with-lce-flow.md) | [common]<br>fun &lt;[T](wrap-with-lce-flow.md)&gt; [wrapWithLceFlow](wrap-with-lce-flow.md)(query: suspend () -&gt; [T](wrap-with-lce-flow.md)?): Flow&lt;[Lce](-lce/index.md)&lt;[T](wrap-with-lce-flow.md)&gt;&gt;<br>A convenience function which wraps a query in a Flow that emits [Lce](-lce/index.md) states for loading, success, and error conditions. |
