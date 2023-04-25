//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[PaymentFailureReason](index.md)

# PaymentFailureReason

[common]\
@Serializable(with = [PaymentFailureReasonSerializer::class](../-payment-failure-reason-serializer/index.md))

enum [PaymentFailureReason](index.md) : Enum&lt;[PaymentFailureReason](index.md)&gt;

## Entries

| | |
|---|---|
| [NONE](-n-o-n-e/index.md) | [common]<br>[NONE](-n-o-n-e/index.md) |
| [TIMEOUT](-t-i-m-e-o-u-t/index.md) | [common]<br>[TIMEOUT](-t-i-m-e-o-u-t/index.md) |
| [NO_ROUTE](-n-o_-r-o-u-t-e/index.md) | [common]<br>[NO_ROUTE](-n-o_-r-o-u-t-e/index.md) |
| [ERROR](-e-r-r-o-r/index.md) | [common]<br>[ERROR](-e-r-r-o-r/index.md) |
| [INCORRECT_PAYMENT_DETAILS](-i-n-c-o-r-r-e-c-t_-p-a-y-m-e-n-t_-d-e-t-a-i-l-s/index.md) | [common]<br>[INCORRECT_PAYMENT_DETAILS](-i-n-c-o-r-r-e-c-t_-p-a-y-m-e-n-t_-d-e-t-a-i-l-s/index.md) |
| [INSUFFICIENT_BALANCE](-i-n-s-u-f-f-i-c-i-e-n-t_-b-a-l-a-n-c-e/index.md) | [common]<br>[INSUFFICIENT_BALANCE](-i-n-s-u-f-f-i-c-i-e-n-t_-b-a-l-a-n-c-e/index.md) |
| [INVOICE_ALREADY_PAID](-i-n-v-o-i-c-e_-a-l-r-e-a-d-y_-p-a-i-d/index.md) | [common]<br>[INVOICE_ALREADY_PAID](-i-n-v-o-i-c-e_-a-l-r-e-a-d-y_-p-a-i-d/index.md) |
| [SELF_PAYMENT](-s-e-l-f_-p-a-y-m-e-n-t/index.md) | [common]<br>[SELF_PAYMENT](-s-e-l-f_-p-a-y-m-e-n-t/index.md) |
| [INVOICE_EXPIRED](-i-n-v-o-i-c-e_-e-x-p-i-r-e-d/index.md) | [common]<br>[INVOICE_EXPIRED](-i-n-v-o-i-c-e_-e-x-p-i-r-e-d/index.md) |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: String): [PaymentFailureReason](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): Array&lt;[PaymentFailureReason](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721): String |
| [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721): Int |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): String |
