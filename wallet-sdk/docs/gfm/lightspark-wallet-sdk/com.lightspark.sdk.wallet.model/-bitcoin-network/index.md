//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[BitcoinNetwork](index.md)

# BitcoinNetwork

[common]\
@Serializable(with = [BitcoinNetworkSerializer::class](../-bitcoin-network-serializer/index.md))

enum [BitcoinNetwork](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[BitcoinNetwork](index.md)&gt;

## Entries

| | |
|---|---|
| [MAINNET](-m-a-i-n-n-e-t/index.md) | [common]<br>[MAINNET](-m-a-i-n-n-e-t/index.md)<br>The production version of the Bitcoin Blockchain. |
| [REGTEST](-r-e-g-t-e-s-t/index.md) | [common]<br>[REGTEST](-r-e-g-t-e-s-t/index.md)<br>A test version of the Bitcoin Blockchain, maintained by Lightspark. |
| [SIGNET](-s-i-g-n-e-t/index.md) | [common]<br>[SIGNET](-s-i-g-n-e-t/index.md)<br>A test version of the Bitcoin Blockchain, maintained by a centralized organization. Not in use at Lightspark. |
| [TESTNET](-t-e-s-t-n-e-t/index.md) | [common]<br>[TESTNET](-t-e-s-t-n-e-t/index.md)<br>A test version of the Bitcoin Blockchain, publically available. |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [BitcoinNetwork](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[BitcoinNetwork](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1149551407) | [common]<br>val [name](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-372974862%2FProperties%2F-1149551407): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1149551407) | [common]<br>val [ordinal](../-withdrawal-request-status/-f-u-t-u-r-e_-v-a-l-u-e/index.md#-739389684%2FProperties%2F-1149551407): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
