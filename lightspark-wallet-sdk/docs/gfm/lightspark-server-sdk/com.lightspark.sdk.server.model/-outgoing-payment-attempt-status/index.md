//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[OutgoingPaymentAttemptStatus](index.md)

# OutgoingPaymentAttemptStatus

[common]\
@Serializable(with = [OutgoingPaymentAttemptStatusSerializer::class](../-outgoing-payment-attempt-status-serializer/index.md))

enum [OutgoingPaymentAttemptStatus](index.md) : Enum&lt;[OutgoingPaymentAttemptStatus](index.md)&gt; 

Enum that enumerates all the possible status of an outgoing payment attempt.

## Entries

| | |
|---|---|
| [IN_FLIGHT](-i-n_-f-l-i-g-h-t/index.md) | [common]<br>[IN_FLIGHT](-i-n_-f-l-i-g-h-t/index.md) |
| [SUCCEEDED](-s-u-c-c-e-e-d-e-d/index.md) | [common]<br>[SUCCEEDED](-s-u-c-c-e-e-d-e-d/index.md) |
| [FAILED](-f-a-i-l-e-d/index.md) | [common]<br>[FAILED](-f-a-i-l-e-d/index.md) |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: String): [OutgoingPaymentAttemptStatus](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): Array&lt;[OutgoingPaymentAttemptStatus](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721): String |
| [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721): Int |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): String |