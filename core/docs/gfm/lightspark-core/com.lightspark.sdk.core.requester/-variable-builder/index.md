//[lightspark-core](../../../index.md)/[com.lightspark.sdk.core.requester](../index.md)/[VariableBuilder](index.md)

# VariableBuilder

[common]\
class [VariableBuilder](index.md)(val jsonSerialFormat: Json)

## Constructors

| | |
|---|---|
| [VariableBuilder](-variable-builder.md) | [common]<br>fun [VariableBuilder](-variable-builder.md)(jsonSerialFormat: Json) |

## Functions

| Name | Summary |
|---|---|
| [add](add.md) | [common]<br>inline fun &lt;[T](add.md)&gt; [add](add.md)(name: String, value: [T](add.md))<br>fun [add](add.md)(name: String, value: Boolean)<br>fun [add](add.md)(name: String, value: Int)<br>fun [add](add.md)(name: String, value: Long)<br>fun [add](add.md)(name: String, value: String)<br>fun [add](add.md)(name: String, value: JsonElement) |
| [build](build.md) | [common]<br>fun [build](build.md)(): JsonObject |

## Properties

| Name | Summary |
|---|---|
| [jsonSerialFormat](json-serial-format.md) | [common]<br>val [jsonSerialFormat](json-serial-format.md): Json |
| [variables](variables.md) | [common]<br>val [variables](variables.md): MutableMap&lt;String, JsonElement&gt; |
