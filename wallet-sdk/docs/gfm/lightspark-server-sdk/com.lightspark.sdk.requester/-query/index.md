//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.requester](../index.md)/[Query](index.md)

# Query

[common]\
data class [Query](index.md)&lt;[T](index.md)&gt;(val queryPayload: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val variableBuilder: [VariableBuilder](../-variable-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), val signingNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val deserializer: (JsonObject) -&gt; [T](index.md))

## Constructors

| | |
|---|---|
| [Query](-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun &lt;[T](index.md)&gt; [Query](-query.md)(queryPayload: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), variables: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, deserializer: [StringDeserializer](../-string-deserializer/index.md)&lt;[T](index.md)&gt;, signingNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)<br>This constructor is for convenience when calling from Java rather than Kotlin. The primary constructor is simpler to use from Kotlin if possible. |
| [Query](-query.md) | [common]<br>fun &lt;[T](index.md)&gt; [Query](-query.md)(queryPayload: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), variableBuilder: [VariableBuilder](../-variable-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), signingNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, deserializer: (JsonObject) -&gt; [T](index.md)) |

## Properties

| Name | Summary |
|---|---|
| [deserializer](deserializer.md) | [common]<br>val [deserializer](deserializer.md): (JsonObject) -&gt; [T](index.md) |
| [queryPayload](query-payload.md) | [common]<br>val [queryPayload](query-payload.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [signingNodeId](signing-node-id.md) | [common]<br>val [signingNodeId](signing-node-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [variableBuilder](variable-builder.md) | [common]<br>val [variableBuilder](variable-builder.md): [VariableBuilder](../-variable-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

## Extensions

| Name | Summary |
|---|---|
| [execute](../../com.lightspark.sdk/execute.md) | [commonJvmAndroid, common]<br>[commonJvmAndroid]<br>fun &lt;[T](../../com.lightspark.sdk/execute.md)&gt; [Query](index.md#-168528822%2FExtensions%2F1046503175)&lt;[T](../../com.lightspark.sdk/execute.md)&gt;.[execute](../../com.lightspark.sdk/execute.md)(client: [LightsparkFuturesClient](../../com.lightspark.sdk/-lightspark-futures-client/index.md)): &lt;Error class: unknown class&gt;&lt;[T](../../com.lightspark.sdk/execute.md)?&gt;<br>[common]<br>suspend fun &lt;[T](../../com.lightspark.sdk/execute.md)&gt; [Query](index.md)&lt;[T](../../com.lightspark.sdk/execute.md)&gt;.[execute](../../com.lightspark.sdk/execute.md)(client: [LightsparkCoroutinesClient](../../com.lightspark.sdk/-lightspark-coroutines-client/index.md)): [T](../../com.lightspark.sdk/execute.md)? |
| [executeSync](../../com.lightspark.sdk/execute-sync.md) | [common]<br>fun &lt;[T](../../com.lightspark.sdk/execute-sync.md)&gt; [Query](index.md)&lt;[T](../../com.lightspark.sdk/execute-sync.md)&gt;.[executeSync](../../com.lightspark.sdk/execute-sync.md)(client: [LightsparkSyncClient](../../com.lightspark.sdk/-lightspark-sync-client/index.md)): [T](../../com.lightspark.sdk/execute-sync.md)? |
