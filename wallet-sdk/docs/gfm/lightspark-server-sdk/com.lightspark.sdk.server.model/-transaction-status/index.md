//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[TransactionStatus](index.md)

# TransactionStatus

[common]\
@Serializable(with = [TransactionStatusSerializer::class](../-transaction-status-serializer/index.md))

enum [TransactionStatus](index.md) : Enum&lt;[TransactionStatus](index.md)&gt;

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
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: String): [TransactionStatus](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): Array&lt;[TransactionStatus](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721): String |
| [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721): Int |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): String |
