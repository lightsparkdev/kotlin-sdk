//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[CurrencyUnit](index.md)

# CurrencyUnit

[common]\
@Serializable(with = [CurrencyUnitSerializer::class](../-currency-unit-serializer/index.md))

enum [CurrencyUnit](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[CurrencyUnit](index.md)&gt;

## Entries

| | |
|---|---|
| [BITCOIN](-b-i-t-c-o-i-n/index.md) | [common]<br>[BITCOIN](-b-i-t-c-o-i-n/index.md)<br>Bitcoin is the cryptocurrency native to the Bitcoin network. It is used as the native medium for value transfer for the Lightning Network. |
| [SATOSHI](-s-a-t-o-s-h-i/index.md) | [common]<br>[SATOSHI](-s-a-t-o-s-h-i/index.md)<br>0.00000001 (10e-8) Bitcoin or one hundred millionth of a Bitcoin. This is the unit most commonly used in Lightning transactions. |
| [MILLISATOSHI](-m-i-l-l-i-s-a-t-o-s-h-i/index.md) | [common]<br>[MILLISATOSHI](-m-i-l-l-i-s-a-t-o-s-h-i/index.md)<br>0.001 Satoshi, or 10e-11 Bitcoin. We recommend using the Satoshi unit instead when possible. |
| [USD](-u-s-d/index.md) | [common]<br>[USD](-u-s-d/index.md)<br>United States Dollar. |
| [NANOBITCOIN](-n-a-n-o-b-i-t-c-o-i-n/index.md) | [common]<br>[NANOBITCOIN](-n-a-n-o-b-i-t-c-o-i-n/index.md)<br>0.000000001 (10e-9) Bitcoin or a billionth of a Bitcoin. We recommend using the Satoshi unit instead when possible. |
| [MICROBITCOIN](-m-i-c-r-o-b-i-t-c-o-i-n/index.md) | [common]<br>[MICROBITCOIN](-m-i-c-r-o-b-i-t-c-o-i-n/index.md)<br>0.000001 (10e-6) Bitcoin or a millionth of a Bitcoin. We recommend using the Satoshi unit instead when possible. |
| [MILLIBITCOIN](-m-i-l-l-i-b-i-t-c-o-i-n/index.md) | [common]<br>[MILLIBITCOIN](-m-i-l-l-i-b-i-t-c-o-i-n/index.md)<br>0.001 (10e-3) Bitcoin or a thousandth of a Bitcoin. We recommend using the Satoshi unit instead when possible. |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [CurrencyUnit](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[CurrencyUnit](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1086033721): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1086033721): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
