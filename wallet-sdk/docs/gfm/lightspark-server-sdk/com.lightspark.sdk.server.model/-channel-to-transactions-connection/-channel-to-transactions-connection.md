//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[ChannelToTransactionsConnection](index.md)/[ChannelToTransactionsConnection](-channel-to-transactions-connection.md)

# ChannelToTransactionsConnection

[common]\
fun [ChannelToTransactionsConnection](-channel-to-transactions-connection.md)(count: Int, averageFee: [CurrencyAmount](../-currency-amount/index.md)? = null, totalAmountTransacted: [CurrencyAmount](../-currency-amount/index.md)? = null, totalFees: [CurrencyAmount](../-currency-amount/index.md)? = null)

#### Parameters

common

| | |
|---|---|
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| averageFee | The average fee for the transactions that transited through this channel, according to the filters and constraints of the connection. |
| totalAmountTransacted | The total amount transacted for the transactions that transited through this channel, according to the filters and constraints of the connection. |
| totalFees | The total amount of fees for the transactions that transited through this channel, according to the filters and constraints of the connection. |
