//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[LightsparkNodePurpose](index.md)

# LightsparkNodePurpose

[common]\
@Serializable(with = [LightsparkNodePurposeSerializer::class](../-lightspark-node-purpose-serializer/index.md))

enum [LightsparkNodePurpose](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[LightsparkNodePurpose](index.md)&gt;

## Entries

| | |
|---|---|
| [SEND](-s-e-n-d/index.md) | [common]<br>[SEND](-s-e-n-d/index.md) |
| [RECEIVE](-r-e-c-e-i-v-e/index.md) | [common]<br>[RECEIVE](-r-e-c-e-i-v-e/index.md) |
| [ROUTING](-r-o-u-t-i-n-g/index.md) | [common]<br>[ROUTING](-r-o-u-t-i-n-g/index.md) |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LightsparkNodePurpose](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[LightsparkNodePurpose](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-1086033721): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-1086033721): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
