//[lightspark-core](../../../index.md)/[com.lightspark.sdk.core.requester](../index.md)/[Query](index.md)

# Query

[common]\
data class [Query](index.md)&lt;[T](index.md)&gt;(val queryPayload: String, val variableBuilder: [VariableBuilder](../-variable-builder/index.md).() -&gt; Unit, val signingNodeId: String? = null, val deserializer: (JsonObject) -&gt; [T](index.md))

## Constructors

| | |
|---|---|
| [Query](-query.md) | [common]<br>@JvmOverloads<br>fun &lt;[T](index.md)&gt; [Query](-query.md)(queryPayload: String, variables: Map&lt;String, String&gt;, deserializer: [StringDeserializer](../-string-deserializer/index.md)&lt;[T](index.md)&gt;, signingNodeId: String? = null)<br>This constructor is for convenience when calling from Java rather than Kotlin. The primary constructor is simpler to use from Kotlin if possible. |
| [Query](-query.md) | [common]<br>fun &lt;[T](index.md)&gt; [Query](-query.md)(queryPayload: String, variableBuilder: [VariableBuilder](../-variable-builder/index.md).() -&gt; Unit, signingNodeId: String? = null, deserializer: (JsonObject) -&gt; [T](index.md)) |

## Properties

| Name | Summary |
|---|---|
| [deserializer](deserializer.md) | [common]<br>val [deserializer](deserializer.md): (JsonObject) -&gt; [T](index.md) |
| [queryPayload](query-payload.md) | [common]<br>val [queryPayload](query-payload.md): String |
| [signingNodeId](signing-node-id.md) | [common]<br>val [signingNodeId](signing-node-id.md): String? = null |
| [variableBuilder](variable-builder.md) | [common]<br>val [variableBuilder](variable-builder.md): [VariableBuilder](../-variable-builder/index.md).() -&gt; Unit |
