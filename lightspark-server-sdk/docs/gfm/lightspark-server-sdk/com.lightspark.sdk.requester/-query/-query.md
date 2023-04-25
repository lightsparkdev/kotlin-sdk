//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.requester](../index.md)/[Query](index.md)/[Query](-query.md)

# Query

[common]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun &lt;[T](index.md)&gt; [Query](-query.md)(queryPayload: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), variables: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, deserializer: [StringDeserializer](../-string-deserializer/index.md)&lt;[T](index.md)&gt;, signingNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

This constructor is for convenience when calling from Java rather than Kotlin. The primary constructor is simpler to use from Kotlin if possible.

#### Parameters

common

| | |
|---|---|
| queryPayload | The GraphQL query payload |
| variables | The variables to be passed to the query |
| signingNodeId | The node ID of the node that should sign the request or null if not needed. |
| deserializer | A function that deserializes the JSON response into the desired type. |

[common]\
fun &lt;[T](index.md)&gt; [Query](-query.md)(queryPayload: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), variableBuilder: [VariableBuilder](../-variable-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), signingNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, deserializer: (JsonObject) -&gt; [T](index.md))
