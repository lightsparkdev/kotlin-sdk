//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[HtlcAttemptFailureCode](index.md)

# HtlcAttemptFailureCode

[common]\
@Serializable(with = [HtlcAttemptFailureCodeSerializer::class](../-htlc-attempt-failure-code-serializer/index.md))

enum [HtlcAttemptFailureCode](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[HtlcAttemptFailureCode](index.md)&gt;

## Entries

| | |
|---|---|
| [INCORRECT_OR_UNKNOWN_PAYMENT_DETAILS](-i-n-c-o-r-r-e-c-t_-o-r_-u-n-k-n-o-w-n_-p-a-y-m-e-n-t_-d-e-t-a-i-l-s/index.md) | [common]<br>[INCORRECT_OR_UNKNOWN_PAYMENT_DETAILS](-i-n-c-o-r-r-e-c-t_-o-r_-u-n-k-n-o-w-n_-p-a-y-m-e-n-t_-d-e-t-a-i-l-s/index.md) |
| [INCORRECT_PAYMENT_AMOUNT](-i-n-c-o-r-r-e-c-t_-p-a-y-m-e-n-t_-a-m-o-u-n-t/index.md) | [common]<br>[INCORRECT_PAYMENT_AMOUNT](-i-n-c-o-r-r-e-c-t_-p-a-y-m-e-n-t_-a-m-o-u-n-t/index.md) |
| [FINAL_INCORRECT_CLTV_EXPIRY](-f-i-n-a-l_-i-n-c-o-r-r-e-c-t_-c-l-t-v_-e-x-p-i-r-y/index.md) | [common]<br>[FINAL_INCORRECT_CLTV_EXPIRY](-f-i-n-a-l_-i-n-c-o-r-r-e-c-t_-c-l-t-v_-e-x-p-i-r-y/index.md) |
| [FINAL_INCORRECT_HTLC_AMOUNT](-f-i-n-a-l_-i-n-c-o-r-r-e-c-t_-h-t-l-c_-a-m-o-u-n-t/index.md) | [common]<br>[FINAL_INCORRECT_HTLC_AMOUNT](-f-i-n-a-l_-i-n-c-o-r-r-e-c-t_-h-t-l-c_-a-m-o-u-n-t/index.md) |
| [FINAL_EXPIRY_TOO_SOON](-f-i-n-a-l_-e-x-p-i-r-y_-t-o-o_-s-o-o-n/index.md) | [common]<br>[FINAL_EXPIRY_TOO_SOON](-f-i-n-a-l_-e-x-p-i-r-y_-t-o-o_-s-o-o-n/index.md) |
| [INVALID_REALM](-i-n-v-a-l-i-d_-r-e-a-l-m/index.md) | [common]<br>[INVALID_REALM](-i-n-v-a-l-i-d_-r-e-a-l-m/index.md) |
| [EXPIRY_TOO_SOON](-e-x-p-i-r-y_-t-o-o_-s-o-o-n/index.md) | [common]<br>[EXPIRY_TOO_SOON](-e-x-p-i-r-y_-t-o-o_-s-o-o-n/index.md) |
| [INVALID_ONION_VERSION](-i-n-v-a-l-i-d_-o-n-i-o-n_-v-e-r-s-i-o-n/index.md) | [common]<br>[INVALID_ONION_VERSION](-i-n-v-a-l-i-d_-o-n-i-o-n_-v-e-r-s-i-o-n/index.md) |
| [INVALID_ONION_HMAC](-i-n-v-a-l-i-d_-o-n-i-o-n_-h-m-a-c/index.md) | [common]<br>[INVALID_ONION_HMAC](-i-n-v-a-l-i-d_-o-n-i-o-n_-h-m-a-c/index.md) |
| [INVALID_ONION_KEY](-i-n-v-a-l-i-d_-o-n-i-o-n_-k-e-y/index.md) | [common]<br>[INVALID_ONION_KEY](-i-n-v-a-l-i-d_-o-n-i-o-n_-k-e-y/index.md) |
| [AMOUNT_BELOW_MINIMUM](-a-m-o-u-n-t_-b-e-l-o-w_-m-i-n-i-m-u-m/index.md) | [common]<br>[AMOUNT_BELOW_MINIMUM](-a-m-o-u-n-t_-b-e-l-o-w_-m-i-n-i-m-u-m/index.md) |
| [FEE_INSUFFICIENT](-f-e-e_-i-n-s-u-f-f-i-c-i-e-n-t/index.md) | [common]<br>[FEE_INSUFFICIENT](-f-e-e_-i-n-s-u-f-f-i-c-i-e-n-t/index.md) |
| [INCORRECT_CLTV_EXPIRY](-i-n-c-o-r-r-e-c-t_-c-l-t-v_-e-x-p-i-r-y/index.md) | [common]<br>[INCORRECT_CLTV_EXPIRY](-i-n-c-o-r-r-e-c-t_-c-l-t-v_-e-x-p-i-r-y/index.md) |
| [CHANNEL_DISABLED](-c-h-a-n-n-e-l_-d-i-s-a-b-l-e-d/index.md) | [common]<br>[CHANNEL_DISABLED](-c-h-a-n-n-e-l_-d-i-s-a-b-l-e-d/index.md) |
| [TEMPORARY_CHANNEL_FAILURE](-t-e-m-p-o-r-a-r-y_-c-h-a-n-n-e-l_-f-a-i-l-u-r-e/index.md) | [common]<br>[TEMPORARY_CHANNEL_FAILURE](-t-e-m-p-o-r-a-r-y_-c-h-a-n-n-e-l_-f-a-i-l-u-r-e/index.md) |
| [REQUIRED_NODE_FEATURE_MISSING](-r-e-q-u-i-r-e-d_-n-o-d-e_-f-e-a-t-u-r-e_-m-i-s-s-i-n-g/index.md) | [common]<br>[REQUIRED_NODE_FEATURE_MISSING](-r-e-q-u-i-r-e-d_-n-o-d-e_-f-e-a-t-u-r-e_-m-i-s-s-i-n-g/index.md) |
| [REQUIRED_CHANNEL_FEATURE_MISSING](-r-e-q-u-i-r-e-d_-c-h-a-n-n-e-l_-f-e-a-t-u-r-e_-m-i-s-s-i-n-g/index.md) | [common]<br>[REQUIRED_CHANNEL_FEATURE_MISSING](-r-e-q-u-i-r-e-d_-c-h-a-n-n-e-l_-f-e-a-t-u-r-e_-m-i-s-s-i-n-g/index.md) |
| [UNKNOWN_NEXT_PEER](-u-n-k-n-o-w-n_-n-e-x-t_-p-e-e-r/index.md) | [common]<br>[UNKNOWN_NEXT_PEER](-u-n-k-n-o-w-n_-n-e-x-t_-p-e-e-r/index.md) |
| [TEMPORARY_NODE_FAILURE](-t-e-m-p-o-r-a-r-y_-n-o-d-e_-f-a-i-l-u-r-e/index.md) | [common]<br>[TEMPORARY_NODE_FAILURE](-t-e-m-p-o-r-a-r-y_-n-o-d-e_-f-a-i-l-u-r-e/index.md) |
| [PERMANENT_NODE_FAILURE](-p-e-r-m-a-n-e-n-t_-n-o-d-e_-f-a-i-l-u-r-e/index.md) | [common]<br>[PERMANENT_NODE_FAILURE](-p-e-r-m-a-n-e-n-t_-n-o-d-e_-f-a-i-l-u-r-e/index.md) |
| [PERMANENT_CHANNEL_FAILURE](-p-e-r-m-a-n-e-n-t_-c-h-a-n-n-e-l_-f-a-i-l-u-r-e/index.md) | [common]<br>[PERMANENT_CHANNEL_FAILURE](-p-e-r-m-a-n-e-n-t_-c-h-a-n-n-e-l_-f-a-i-l-u-r-e/index.md) |
| [EXPIRY_TOO_FAR](-e-x-p-i-r-y_-t-o-o_-f-a-r/index.md) | [common]<br>[EXPIRY_TOO_FAR](-e-x-p-i-r-y_-t-o-o_-f-a-r/index.md) |
| [MPP_TIMEOUT](-m-p-p_-t-i-m-e-o-u-t/index.md) | [common]<br>[MPP_TIMEOUT](-m-p-p_-t-i-m-e-o-u-t/index.md) |
| [INVALID_ONION_PAYLOAD](-i-n-v-a-l-i-d_-o-n-i-o-n_-p-a-y-l-o-a-d/index.md) | [common]<br>[INVALID_ONION_PAYLOAD](-i-n-v-a-l-i-d_-o-n-i-o-n_-p-a-y-l-o-a-d/index.md) |
| [INTERNAL_FAILURE](-i-n-t-e-r-n-a-l_-f-a-i-l-u-r-e/index.md) | [common]<br>[INTERNAL_FAILURE](-i-n-t-e-r-n-a-l_-f-a-i-l-u-r-e/index.md) |
| [UNKNOWN_FAILURE](-u-n-k-n-o-w-n_-f-a-i-l-u-r-e/index.md) | [common]<br>[UNKNOWN_FAILURE](-u-n-k-n-o-w-n_-f-a-i-l-u-r-e/index.md) |
| [UNREADABLE_FAILURE](-u-n-r-e-a-d-a-b-l-e_-f-a-i-l-u-r-e/index.md) | [common]<br>[UNREADABLE_FAILURE](-u-n-r-e-a-d-a-b-l-e_-f-a-i-l-u-r-e/index.md) |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [HtlcAttemptFailureCode](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[HtlcAttemptFailureCode](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-962664521) | [common]<br>val [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-962664521): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-962664521) | [common]<br>val [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-962664521): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
