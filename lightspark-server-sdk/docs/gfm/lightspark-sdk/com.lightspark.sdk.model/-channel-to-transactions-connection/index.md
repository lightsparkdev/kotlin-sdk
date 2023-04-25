//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[ChannelToTransactionsConnection](index.md)

# ChannelToTransactionsConnection

[common]\
@Serializable

data class [ChannelToTransactionsConnection](index.md)(val count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val averageFee: [CurrencyAmount](../-currency-amount/index.md)? = null, val totalAmountTransacted: [CurrencyAmount](../-currency-amount/index.md)? = null, val totalFees: [CurrencyAmount](../-currency-amount/index.md)? = null)

#### Parameters

common

| | |
|---|---|
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| averageFee | The average fee for the transactions that transited through this channel, according to the filters and constraints of the connection. |
| totalAmountTransacted | The total amount transacted for the transactions that transited through this channel, according to the filters and constraints of the connection. |
| totalFees | The total amount of fees for the transactions that transited through this channel, according to the filters and constraints of the connection. |

## Constructors

| | |
|---|---|
| [ChannelToTransactionsConnection](-channel-to-transactions-connection.md) | [common]<br>fun [ChannelToTransactionsConnection](-channel-to-transactions-connection.md)(count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), averageFee: [CurrencyAmount](../-currency-amount/index.md)? = null, totalAmountTransacted: [CurrencyAmount](../-currency-amount/index.md)? = null, totalFees: [CurrencyAmount](../-currency-amount/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [averageFee](average-fee.md) | [common]<br>val [averageFee](average-fee.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [count](count.md) | [common]<br>val [count](count.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [totalAmountTransacted](total-amount-transacted.md) | [common]<br>val [totalAmountTransacted](total-amount-transacted.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [totalFees](total-fees.md) | [common]<br>val [totalFees](total-fees.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
