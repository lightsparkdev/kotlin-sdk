//[lightspark-sdk](../../index.md)/[com.lightspark.sdk](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [Lce](-lce/index.md) | [common]<br>interface [Lce](-lce/index.md)&lt;out [T](-lce/index.md)&gt; |
| [LightsparkClient](-lightspark-client/index.md) | [common]<br>class [LightsparkClient](-lightspark-client/index.md)<br>Main entry point for the Lightspark SDK. |
| [LightsparkErrorCode](-lightspark-error-code/index.md) | [common]<br>enum [LightsparkErrorCode](-lightspark-error-code/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[LightsparkErrorCode](-lightspark-error-code/index.md)&gt; |
| [LightsparkException](-lightspark-exception/index.md) | [common]<br>open class [LightsparkException](-lightspark-exception/index.md)(val message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val errorCode: [LightsparkErrorCode](-lightspark-error-code/index.md)) : [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)<br>A generic exception thrown by the Lightspark SDK. |
| [LightsparkWalletClient](-lightspark-wallet-client/index.md) | [common]<br>class [LightsparkWalletClient](-lightspark-wallet-client/index.md)<br>Main entry point for the Lightspark Wallet SDK. |

## Functions

| Name | Summary |
|---|---|
| [asLce](as-lce.md) | [common]<br>fun &lt;[T](as-lce.md)&gt; Flow&lt;[T](as-lce.md)&gt;.[asLce](as-lce.md)(): Flow&lt;[Lce](-lce/index.md)&lt;[T](as-lce.md)&gt;&gt; |
| [wrapWithLceFlow](wrap-with-lce-flow.md) | [common]<br>fun &lt;[T](wrap-with-lce-flow.md)&gt; [wrapWithLceFlow](wrap-with-lce-flow.md)(query: suspend () -&gt; [T](wrap-with-lce-flow.md)?): Flow&lt;[Lce](-lce/index.md)&lt;[T](wrap-with-lce-flow.md)&gt;&gt;<br>A convenience function which wraps a query in a Flow that emits [Lce](-lce/index.md) states for loading, success, and error conditions. |
