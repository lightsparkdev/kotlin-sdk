//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[NodeAddressType](index.md)

# NodeAddressType

[common]\
@Serializable(with = [NodeAddressTypeSerializer::class](../-node-address-type-serializer/index.md))

enum [NodeAddressType](index.md) : Enum&lt;[NodeAddressType](index.md)&gt; 

An enum that enumerates all possible types of addresses of a node on the Lightning Network.

## Entries

| | |
|---|---|
| [IPV4](-i-p-v4/index.md) | [common]<br>[IPV4](-i-p-v4/index.md) |
| [IPV6](-i-p-v6/index.md) | [common]<br>[IPV6](-i-p-v6/index.md) |
| [TOR](-t-o-r/index.md) | [common]<br>[TOR](-t-o-r/index.md) |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: String): [NodeAddressType](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): Array&lt;[NodeAddressType](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721): String |
| [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721): Int |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): String |
