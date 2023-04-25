//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[TransactionStatus](index.md)

# TransactionStatus

[common]\
@Serializable(with = [TransactionStatusSerializer::class](../-transaction-status-serializer/index.md))

enum [TransactionStatus](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[TransactionStatus](index.md)&gt;

## Entries

| | |
|---|---|
| [SUCCESS](-s-u-c-c-e-s-s/index.md) | [common]<br>[SUCCESS](-s-u-c-c-e-s-s/index.md)<br>Transaction succeeded.. |
| [FAILED](-f-a-i-l-e-d/index.md) | [common]<br>[FAILED](-f-a-i-l-e-d/index.md)<br>Transaction failed. |
| [PENDING](-p-e-n-d-i-n-g/index.md) | [common]<br>[PENDING](-p-e-n-d-i-n-g/index.md)<br>Transaction has been initiated and is currently in-flight. |
| [NOT_STARTED](-n-o-t_-s-t-a-r-t-e-d/index.md) | [common]<br>[NOT_STARTED](-n-o-t_-s-t-a-r-t-e-d/index.md)<br>For transaction type PAYMENT_REQUEST only. No payments have been made to a payment request. |
| [EXPIRED](-e-x-p-i-r-e-d/index.md) | [common]<br>[EXPIRED](-e-x-p-i-r-e-d/index.md)<br>For transaction type PAYMENT_REQUEST only. A payment request has expired. |
| [CANCELLED](-c-a-n-c-e-l-l-e-d/index.md) | [common]<br>[CANCELLED](-c-a-n-c-e-l-l-e-d/index.md)<br>For transaction type PAYMENT_REQUEST only. |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TransactionStatus](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[TransactionStatus](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-962664521) | [common]<br>val [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-962664521): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-962664521) | [common]<br>val [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-962664521): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
