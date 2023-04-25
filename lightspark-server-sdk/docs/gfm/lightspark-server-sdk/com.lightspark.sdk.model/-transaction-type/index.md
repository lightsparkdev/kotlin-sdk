//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[TransactionType](index.md)

# TransactionType

[common]\
@Serializable(with = [TransactionTypeSerializer::class](../-transaction-type-serializer/index.md))

enum [TransactionType](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[TransactionType](index.md)&gt;

## Entries

| | |
|---|---|
| [OUTGOING_PAYMENT](-o-u-t-g-o-i-n-g_-p-a-y-m-e-n-t/index.md) | [common]<br>[OUTGOING_PAYMENT](-o-u-t-g-o-i-n-g_-p-a-y-m-e-n-t/index.md)<br>Transactions initiated from a Lightspark node on Lightning Network. |
| [INCOMING_PAYMENT](-i-n-c-o-m-i-n-g_-p-a-y-m-e-n-t/index.md) | [common]<br>[INCOMING_PAYMENT](-i-n-c-o-m-i-n-g_-p-a-y-m-e-n-t/index.md)<br>Transactions received by a Lightspark node on Lightning Network. |
| [ROUTED](-r-o-u-t-e-d/index.md) | [common]<br>[ROUTED](-r-o-u-t-e-d/index.md)<br>Transactions that forwarded payments through Lightspark nodes on Lightning Network. |
| [L1_WITHDRAW](-l1_-w-i-t-h-d-r-a-w/index.md) | [common]<br>[L1_WITHDRAW](-l1_-w-i-t-h-d-r-a-w/index.md)<br>Transactions on the Bitcoin blockchain to withdraw funds from a Lightspark node to a Bitcoin wallet. |
| [L1_DEPOSIT](-l1_-d-e-p-o-s-i-t/index.md) | [common]<br>[L1_DEPOSIT](-l1_-d-e-p-o-s-i-t/index.md)<br>Transactions on Bitcoin blockchain to fund a Lightspark node's wallet. |
| [CHANNEL_OPEN](-c-h-a-n-n-e-l_-o-p-e-n/index.md) | [common]<br>[CHANNEL_OPEN](-c-h-a-n-n-e-l_-o-p-e-n/index.md)<br>Transactions on Bitcoin blockchain to open a channel on Lightning Network funded by the local Lightspark node. |
| [CHANNEL_CLOSE](-c-h-a-n-n-e-l_-c-l-o-s-e/index.md) | [common]<br>[CHANNEL_CLOSE](-c-h-a-n-n-e-l_-c-l-o-s-e/index.md)<br>Transactions on Bitcoin blockchain to close a channel on Lightning Network where the balances are allocated back to local and remote nodes. |
| [PAYMENT](-p-a-y-m-e-n-t/index.md) | [common]<br>[PAYMENT](-p-a-y-m-e-n-t/index.md)<br>Transactions initiated from a Lightspark node on Lightning Network. |
| [PAYMENT_REQUEST](-p-a-y-m-e-n-t_-r-e-q-u-e-s-t/index.md) | [common]<br>[PAYMENT_REQUEST](-p-a-y-m-e-n-t_-r-e-q-u-e-s-t/index.md)<br>Payment requests from a Lightspark node on Lightning Network |
| [ROUTE](-r-o-u-t-e/index.md) | [common]<br>[ROUTE](-r-o-u-t-e/index.md)<br>Transactions that forwarded payments through Lightspark nodes on Lightning Network. |
| [FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md) | [common]<br>[FUTURE_VALUE](-f-u-t-u-r-e_-v-a-l-u-e/index.md)<br>This is an enum value that represents values that could be added in the future. Clients should support unknown values as more of them could be added without notice. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [common]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TransactionType](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [common]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[TransactionType](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |

## Properties

| Name | Summary |
|---|---|
| [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-1086033721) | [common]<br>val [name](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-372974862%2FProperties%2F-1086033721): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-1086033721) | [common]<br>val [ordinal](../../com.lightspark.sdk.requester/-server-environment/-p-r-o-d/index.md#-739389684%2FProperties%2F-1086033721): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [rawValue](raw-value.md) | [common]<br>val [rawValue](raw-value.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
