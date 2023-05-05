//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[IncomingPaymentAttemptStatus](index.md)

# IncomingPaymentAttemptStatus

[common]\
@Serializable(with = [IncomingPaymentAttemptStatusSerializer::class](../-incoming-payment-attempt-status-serializer/index.md))

enum [IncomingPaymentAttemptStatus](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[IncomingPaymentAttemptStatus](index.md)&gt; 

Enum that enumerates all the possible status of an incoming payment attempt.

## Entries

| | |
|---|---|
| [ACCEPTED](-a-c-c-e-p-t-e-d/index.md) | [common]<br>[ACCEPTED](-a-c-c-e-p-t-e-d/index.md) |
| [SETTLED](-s-e-t-t-l-e-d/index.md) | [common]<br>[SETTLED](-s-e-t-t-l-e-d/index.md) |
| [CANCELED](-c-a-n-c-e-l-e-d/index.md) | [common]<br>[CANCELED](-c-a-n-c-e-l-e-d/index.md) |
| [UNKNOWN](-u-n-k-n-o-w-n/index.md) | [common]<br>[UNKNOWN](-u-n-k-n-o-w-n/index.md) |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [IncomingPaymentAttemptStatus](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[IncomingPaymentAttemptStatus](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
