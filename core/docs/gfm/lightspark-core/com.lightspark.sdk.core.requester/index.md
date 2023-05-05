//[lightspark-core](../../index.md)/[com.lightspark.sdk.core.requester](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [Query](-query/index.md) | [common]<br>data class [Query](-query/index.md)&lt;[T](-query/index.md)&gt;(val queryPayload: String, val variableBuilder: [VariableBuilder](-variable-builder/index.md).() -&gt; Unit, val signingNodeId: String? = null, val deserializer: (JsonObject) -&gt; [T](-query/index.md)) |
| [Requester](-requester/index.md) | [common]<br>class [Requester](-requester/index.md)(nodeKeyCache: [NodeKeyCache](../com.lightspark.sdk.core.crypto/-node-key-cache/index.md), authProvider: [AuthProvider](../com.lightspark.sdk.core.auth/-auth-provider/index.md), jsonSerialFormat: Json, baseUrl: String = DEFAULT_BASE_URL) |
| [ServerEnvironment](-server-environment/index.md) | [common]<br>enum [ServerEnvironment](-server-environment/index.md) : Enum&lt;[ServerEnvironment](-server-environment/index.md)&gt; |
| [StringDeserializer](-string-deserializer/index.md) | [common]<br>interface [StringDeserializer](-string-deserializer/index.md)&lt;[T](-string-deserializer/index.md)&gt; |
| [VariableBuilder](-variable-builder/index.md) | [common]<br>class [VariableBuilder](-variable-builder/index.md)(val jsonSerialFormat: Json) |

## Functions

| Name | Summary |
|---|---|
| [variables](variables.md) | [common]<br>fun [variables](variables.md)(jsonSerialFormat: Json, builder: [VariableBuilder](-variable-builder/index.md).() -&gt; Unit): JsonObject |
