//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[LightsparkNodeStatus](index.md)

# LightsparkNodeStatus

[common]\
@Serializable(with = [LightsparkNodeStatusSerializer::class](../-lightspark-node-status-serializer/index.md))

enum [LightsparkNodeStatus](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[LightsparkNodeStatus](index.md)&gt;

## Entries

| | |
|---|---|
| [CREATED](-c-r-e-a-t-e-d/index.md) | [common]<br>[CREATED](-c-r-e-a-t-e-d/index.md) |
| [DEPLOYED](-d-e-p-l-o-y-e-d/index.md) | [common]<br>[DEPLOYED](-d-e-p-l-o-y-e-d/index.md) |
| [STARTED](-s-t-a-r-t-e-d/index.md) | [common]<br>[STARTED](-s-t-a-r-t-e-d/index.md) |
| [SYNCING](-s-y-n-c-i-n-g/index.md) | [common]<br>[SYNCING](-s-y-n-c-i-n-g/index.md) |
| [READY](-r-e-a-d-y/index.md) | [common]<br>[READY](-r-e-a-d-y/index.md) |
| [STOPPED](-s-t-o-p-p-e-d/index.md) | [common]<br>[STOPPED](-s-t-o-p-p-e-d/index.md) |
| [TERMINATED](-t-e-r-m-i-n-a-t-e-d/index.md) | [common]<br>[TERMINATED](-t-e-r-m-i-n-a-t-e-d/index.md) |
| [WALLET_LOCKED](-w-a-l-l-e-t_-l-o-c-k-e-d/index.md) | [common]<br>[WALLET_LOCKED](-w-a-l-l-e-t_-l-o-c-k-e-d/index.md) |
| [FAILED_TO_DEPLOY](-f-a-i-l-e-d_-t-o_-d-e-p-l-o-y/index.md) | [common]<br>[FAILED_TO_DEPLOY](-f-a-i-l-e-d_-t-o_-d-e-p-l-o-y/index.md) |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LightsparkNodeStatus](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[LightsparkNodeStatus](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-1086033721): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-1086033721): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
