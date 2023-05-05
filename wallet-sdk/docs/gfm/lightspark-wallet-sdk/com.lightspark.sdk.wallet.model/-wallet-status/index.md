//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[WalletStatus](index.md)

# WalletStatus

[common]\
@Serializable(with = [WalletStatusSerializer::class](../-wallet-status-serializer/index.md))

enum [WalletStatus](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[WalletStatus](index.md)&gt;

## Entries

| | |
|---|---|
| [NOT_SETUP](-n-o-t_-s-e-t-u-p/index.md) | [common]<br>[NOT_SETUP](-n-o-t_-s-e-t-u-p/index.md)<br>The wallet has not been set up yet and is ready to be deployed. This is the default status after the first login. |
| [DEPLOYING](-d-e-p-l-o-y-i-n-g/index.md) | [common]<br>[DEPLOYING](-d-e-p-l-o-y-i-n-g/index.md)<br>The wallet is currently being deployed in the Lightspark infrastructure. |
| [DEPLOYED](-d-e-p-l-o-y-e-d/index.md) | [common]<br>[DEPLOYED](-d-e-p-l-o-y-e-d/index.md)<br>The wallet has been deployed in the Lightspark infrastructure and is ready to be initialized. |
| [INITIALIZING](-i-n-i-t-i-a-l-i-z-i-n-g/index.md) | [common]<br>[INITIALIZING](-i-n-i-t-i-a-l-i-z-i-n-g/index.md)<br>The wallet is currently being initialized. |
| [READY](-r-e-a-d-y/index.md) | [common]<br>[READY](-r-e-a-d-y/index.md)<br>The wallet is available and ready to be used. |
| [UNAVAILABLE](-u-n-a-v-a-i-l-a-b-l-e/index.md) | [common]<br>[UNAVAILABLE](-u-n-a-v-a-i-l-a-b-l-e/index.md)<br>The wallet is temporarily available, due to a transient issue or a scheduled maintenance. |
| [FAILED](-f-a-i-l-e-d/index.md) | [common]<br>[FAILED](-f-a-i-l-e-d/index.md)<br>The wallet had an unrecoverable failure. This status is not expected to happend and will be investigated by the Lightspark team. |
| [TERMINATING](-t-e-r-m-i-n-a-t-i-n-g/index.md) | [common]<br>[TERMINATING](-t-e-r-m-i-n-a-t-i-n-g/index.md)<br>The wallet is being terminated. |
| [TERMINATED](-t-e-r-m-i-n-a-t-e-d/index.md) | [common]<br>[TERMINATED](-t-e-r-m-i-n-a-t-e-d/index.md)<br>The wallet has been terminated and is not available in the Lightspark infrastructure anymore. It is not connected to the Lightning network and its funds can only be accessed using the Funds Recovery flow. |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [WalletStatus](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[WalletStatus](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1149551407) | [common]<br>val [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1149551407): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1149551407) | [common]<br>val [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1149551407): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
