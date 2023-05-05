//[lightspark-core](../../../index.md)/[com.lightspark.sdk.core.requester](../index.md)/[Query](index.md)/[Query](-query.md)

# Query

[common]\

@JvmOverloads

fun &lt;[T](index.md)&gt; [Query](-query.md)(queryPayload: String, variables: Map&lt;String, String&gt;, deserializer: [StringDeserializer](../-string-deserializer/index.md)&lt;[T](index.md)&gt;, signingNodeId: String? = null)

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
fun &lt;[T](index.md)&gt; [Query](-query.md)(queryPayload: String, variableBuilder: [VariableBuilder](../-variable-builder/index.md).() -&gt; Unit, signingNodeId: String? = null, deserializer: (JsonObject) -&gt; [T](index.md))
