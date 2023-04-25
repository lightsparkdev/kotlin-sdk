//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[WithdrawalRequestStatus](index.md)

# WithdrawalRequestStatus

[common]\
@Serializable(with = [WithdrawalRequestStatusSerializer::class](../-withdrawal-request-status-serializer/index.md))

enum [WithdrawalRequestStatus](index.md) : Enum&lt;[WithdrawalRequestStatus](index.md)&gt;

## Entries

| | |
|---|---|
| [FAILED](-f-a-i-l-e-d/index.md) | [common]<br>[FAILED](-f-a-i-l-e-d/index.md) |
| [IN_PROGRESS](-i-n_-p-r-o-g-r-e-s-s/index.md) | [common]<br>[IN_PROGRESS](-i-n_-p-r-o-g-r-e-s-s/index.md) |
| [SUCCESSFUL](-s-u-c-c-e-s-s-f-u-l/index.md) | [common]<br>[SUCCESSFUL](-s-u-c-c-e-s-s-f-u-l/index.md) |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: String): [WithdrawalRequestStatus](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): Array&lt;[WithdrawalRequestStatus](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721): String |
| [ordinal](-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721): Int |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): String |
