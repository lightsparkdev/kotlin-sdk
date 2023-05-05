//[lightspark-core](../../index.md)/[com.lightspark.sdk.core](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [Lce](-lce/index.md) | [common]<br>interface [Lce](-lce/index.md)&lt;out [T](-lce/index.md)&gt; |
| [LightsparkErrorCode](-lightspark-error-code/index.md) | [common]<br>enum [LightsparkErrorCode](-lightspark-error-code/index.md) : Enum&lt;[LightsparkErrorCode](-lightspark-error-code/index.md)&gt; |
| [LightsparkException](-lightspark-exception/index.md) | [common]<br>open class [LightsparkException](-lightspark-exception/index.md)(val message: String, val errorCode: [LightsparkErrorCode](-lightspark-error-code/index.md), val cause: Throwable? = null) : Exception<br>A generic exception thrown by the Lightspark SDK. |

## Functions

| Name | Summary |
|---|---|
| [asLce](as-lce.md) | [common]<br>fun &lt;[T](as-lce.md)&gt; Flow&lt;[T](as-lce.md)&gt;.[asLce](as-lce.md)(): Flow&lt;[Lce](-lce/index.md)&lt;[T](as-lce.md)&gt;&gt; |
| [wrapWithLceFlow](wrap-with-lce-flow.md) | [common]<br>fun &lt;[T](wrap-with-lce-flow.md)&gt; [wrapWithLceFlow](wrap-with-lce-flow.md)(query: suspend () -&gt; [T](wrap-with-lce-flow.md)?): Flow&lt;[Lce](-lce/index.md)&lt;[T](wrap-with-lce-flow.md)&gt;&gt;<br>A convenience function which wraps a query in a Flow that emits [Lce](-lce/index.md) states for loading, success, and error conditions. |
