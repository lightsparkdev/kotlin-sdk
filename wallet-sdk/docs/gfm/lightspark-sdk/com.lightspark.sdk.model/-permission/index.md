//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[Permission](index.md)

# Permission

[common]\
@Serializable(with = [PermissionSerializer::class](../-permission-serializer/index.md))

enum [Permission](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Permission](index.md)&gt;

## Entries

| | |
|---|---|
| [ALL](-a-l-l/index.md) | [common]<br>[ALL](-a-l-l/index.md) |
| [MAINNET_VIEW](-m-a-i-n-n-e-t_-v-i-e-w/index.md) | [common]<br>[MAINNET_VIEW](-m-a-i-n-n-e-t_-v-i-e-w/index.md) |
| [MAINNET_TRANSACT](-m-a-i-n-n-e-t_-t-r-a-n-s-a-c-t/index.md) | [common]<br>[MAINNET_TRANSACT](-m-a-i-n-n-e-t_-t-r-a-n-s-a-c-t/index.md) |
| [MAINNET_MANAGE](-m-a-i-n-n-e-t_-m-a-n-a-g-e/index.md) | [common]<br>[MAINNET_MANAGE](-m-a-i-n-n-e-t_-m-a-n-a-g-e/index.md) |
| [TESTNET_VIEW](-t-e-s-t-n-e-t_-v-i-e-w/index.md) | [common]<br>[TESTNET_VIEW](-t-e-s-t-n-e-t_-v-i-e-w/index.md) |
| [TESTNET_TRANSACT](-t-e-s-t-n-e-t_-t-r-a-n-s-a-c-t/index.md) | [common]<br>[TESTNET_TRANSACT](-t-e-s-t-n-e-t_-t-r-a-n-s-a-c-t/index.md) |
| [TESTNET_MANAGE](-t-e-s-t-n-e-t_-m-a-n-a-g-e/index.md) | [common]<br>[TESTNET_MANAGE](-t-e-s-t-n-e-t_-m-a-n-a-g-e/index.md) |
| [REGTEST_VIEW](-r-e-g-t-e-s-t_-v-i-e-w/index.md) | [common]<br>[REGTEST_VIEW](-r-e-g-t-e-s-t_-v-i-e-w/index.md) |
| [REGTEST_TRANSACT](-r-e-g-t-e-s-t_-t-r-a-n-s-a-c-t/index.md) | [common]<br>[REGTEST_TRANSACT](-r-e-g-t-e-s-t_-t-r-a-n-s-a-c-t/index.md) |
| [REGTEST_MANAGE](-r-e-g-t-e-s-t_-m-a-n-a-g-e/index.md) | [common]<br>[REGTEST_MANAGE](-r-e-g-t-e-s-t_-m-a-n-a-g-e/index.md) |
| [USER_VIEW](-u-s-e-r_-v-i-e-w/index.md) | [common]<br>[USER_VIEW](-u-s-e-r_-v-i-e-w/index.md) |
| [USER_MANAGE](-u-s-e-r_-m-a-n-a-g-e/index.md) | [common]<br>[USER_MANAGE](-u-s-e-r_-m-a-n-a-g-e/index.md) |
| [ACCOUNT_VIEW](-a-c-c-o-u-n-t_-v-i-e-w/index.md) | [common]<br>[ACCOUNT_VIEW](-a-c-c-o-u-n-t_-v-i-e-w/index.md) |
| [ACCOUNT_MANAGE](-a-c-c-o-u-n-t_-m-a-n-a-g-e/index.md) | [common]<br>[ACCOUNT_MANAGE](-a-c-c-o-u-n-t_-m-a-n-a-g-e/index.md) |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Permission](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Permission](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-962664521) | [common]<br>val [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-962664521): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-962664521) | [common]<br>val [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-962664521): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
