//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[WithdrawalRequestToChannelOpeningTransactionsConnection](index.md)

# WithdrawalRequestToChannelOpeningTransactionsConnection

[common]\
@Serializable

data class [WithdrawalRequestToChannelOpeningTransactionsConnection](index.md)(val pageInfo: [PageInfo](../-page-info/index.md), val count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ChannelOpeningTransaction](../-channel-opening-transaction/index.md)&gt;)

#### Parameters

common

| | |
|---|---|
| pageInfo | An object that holds pagination information about the objects in this connection. |
| count | The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field). |
| entities | The channel opening transactions for the current page of this connection. |

## Constructors

| | |
|---|---|
| [WithdrawalRequestToChannelOpeningTransactionsConnection](-withdrawal-request-to-channel-opening-transactions-connection.md) | [common]<br>fun [WithdrawalRequestToChannelOpeningTransactionsConnection](-withdrawal-request-to-channel-opening-transactions-connection.md)(pageInfo: [PageInfo](../-page-info/index.md), count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), entities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ChannelOpeningTransaction](../-channel-opening-transaction/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [count](count.md) | [common]<br>val [count](count.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [entities](entities.md) | [common]<br>val [entities](entities.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ChannelOpeningTransaction](../-channel-opening-transaction/index.md)&gt; |
| [pageInfo](page-info.md) | [common]<br>val [pageInfo](page-info.md): [PageInfo](../-page-info/index.md) |
