//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[WithdrawalRequestToChannelOpeningTransactionsConnection](index.md)/[WithdrawalRequestToChannelOpeningTransactionsConnection](-withdrawal-request-to-channel-opening-transactions-connection.md)

# WithdrawalRequestToChannelOpeningTransactionsConnection

[common]\
fun [WithdrawalRequestToChannelOpeningTransactionsConnection](-withdrawal-request-to-channel-opening-transactions-connection.md)(pageInfo: [PageInfo](../-page-info/index.md), count: Int, entities: List&lt;[ChannelOpeningTransaction](../-channel-opening-transaction/index.md)&gt;)

#### Parameters

common

| | |
|---|---|
| pageInfo | An object that holds pagination information about the objects in this connection. |
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The channel opening transactions for the current page of this connection. |