//[lightspark-sdk](../../index.md)/[com.lightspark.sdk.requester](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [Query](-query/index.md) | [common]<br>data class [Query](-query/index.md)&lt;[T](-query/index.md)&gt;(val queryPayload: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val variableBuilder: [VariableBuilder](-variable-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), val signingNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val deserializer: (JsonObject) -&gt; [T](-query/index.md)) |
| [ServerEnvironment](-server-environment/index.md) | [common]<br>enum [ServerEnvironment](-server-environment/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[ServerEnvironment](-server-environment/index.md)&gt; |
| [StringDeserializer](-string-deserializer/index.md) | [common]<br>interface [StringDeserializer](-string-deserializer/index.md)&lt;[T](-string-deserializer/index.md)&gt; |
| [VariableBuilder](-variable-builder/index.md) | [common]<br>class [VariableBuilder](-variable-builder/index.md) |

## Functions

| Name | Summary |
|---|---|
| [variables](variables.md) | [common]<br>fun [variables](variables.md)(builder: [VariableBuilder](-variable-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): JsonObject |
