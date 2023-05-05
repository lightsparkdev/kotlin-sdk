//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[ChannelStatus](index.md)

# ChannelStatus

[common]\
@Serializable(with = [ChannelStatusSerializer::class](../-channel-status-serializer/index.md))

enum [ChannelStatus](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[ChannelStatus](index.md)&gt;

## Entries

| | |
|---|---|
| [OK](-o-k/index.md) | [common]<br>[OK](-o-k/index.md)<br>The channel is online and ready to send and receive funds. |
| [PENDING](-p-e-n-d-i-n-g/index.md) | [common]<br>[PENDING](-p-e-n-d-i-n-g/index.md)<br>The channel has been created, but the Bitcoin transaction that initiates it still needs to be confirmed on the Bitcoin blockchain. |
| [OFFLINE](-o-f-f-l-i-n-e/index.md) | [common]<br>[OFFLINE](-o-f-f-l-i-n-e/index.md)<br>The channel is not available, likely because the peer is not online. |
| [UNBALANCED_FOR_SEND](-u-n-b-a-l-a-n-c-e-d_-f-o-r_-s-e-n-d/index.md) | [common]<br>[UNBALANCED_FOR_SEND](-u-n-b-a-l-a-n-c-e-d_-f-o-r_-s-e-n-d/index.md)<br>The channel is behaving properly, but its remote balance is much higher than its local balance so it is not balanced properly for sending funds out. |
| [UNBALANCED_FOR_RECEIVE](-u-n-b-a-l-a-n-c-e-d_-f-o-r_-r-e-c-e-i-v-e/index.md) | [common]<br>[UNBALANCED_FOR_RECEIVE](-u-n-b-a-l-a-n-c-e-d_-f-o-r_-r-e-c-e-i-v-e/index.md)<br>The channel is behaving properly, but its remote balance is much lower than its local balance so it is not balanced properly for receiving funds. |
| [CLOSED](-c-l-o-s-e-d/index.md) | [common]<br>[CLOSED](-c-l-o-s-e-d/index.md)<br>The channel has been closed. Information about the channel is still available for historical purposes but the channel cannot be used anymore. |
| [ERROR](-e-r-r-o-r/index.md) | [common]<br>[ERROR](-e-r-r-o-r/index.md)<br>Something unexpected happened and we cannot determine the status of this channel. Please try again later or contact the support. |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ChannelStatus](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[ChannelStatus](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-1086033721): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-1086033721): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
