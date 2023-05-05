//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[Account](index.md)

# Account

[common]\
@Serializable

data class [Account](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) : [Entity](../-entity/index.md)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| name | The name of this account. |

## Constructors

| | |
|---|---|
| [Account](-account.md) | [common]<br>fun [Account](-account.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getApiTokensQuery](get-api-tokens-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getApiTokensQuery](get-api-tokens-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null): Query&lt;[AccountToApiTokensConnection](../-account-to-api-tokens-connection/index.md)&gt; |
| [getBlockchainBalanceQuery](get-blockchain-balance-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getBlockchainBalanceQuery](get-blockchain-balance-query.md)(bitcoinNetworks: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null): Query&lt;[BlockchainBalance](../-blockchain-balance/index.md)?&gt; |
| [getChannelsQuery](get-channels-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getChannelsQuery](get-channels-query.md)(bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), lightningNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, afterDate: Instant? = null, beforeDate: Instant? = null, first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null): Query&lt;[AccountToChannelsConnection](../-account-to-channels-connection/index.md)&gt; |
| [getConductivityQuery](get-conductivity-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getConductivityQuery](get-conductivity-query.md)(bitcoinNetworks: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null): Query&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?&gt; |
| [getLocalBalanceQuery](get-local-balance-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getLocalBalanceQuery](get-local-balance-query.md)(bitcoinNetworks: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null): Query&lt;[CurrencyAmount](../-currency-amount/index.md)?&gt; |
| [getNodesQuery](get-nodes-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getNodesQuery](get-nodes-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, bitcoinNetworks: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null): Query&lt;[AccountToNodesConnection](../-account-to-nodes-connection/index.md)&gt; |
| [getPaymentRequestsQuery](get-payment-requests-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getPaymentRequestsQuery](get-payment-requests-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, after: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, afterDate: Instant? = null, beforeDate: Instant? = null, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md)? = null, lightningNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): Query&lt;[AccountToPaymentRequestsConnection](../-account-to-payment-requests-connection/index.md)&gt; |
| [getRemoteBalanceQuery](get-remote-balance-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getRemoteBalanceQuery](get-remote-balance-query.md)(bitcoinNetworks: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null): Query&lt;[CurrencyAmount](../-currency-amount/index.md)?&gt; |
| [getTransactionsQuery](get-transactions-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getTransactionsQuery](get-transactions-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, after: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, types: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TransactionType](../-transaction-type/index.md)&gt;? = null, afterDate: Instant? = null, beforeDate: Instant? = null, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md)? = null, lightningNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, statuses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TransactionStatus](../-transaction-status/index.md)&gt;? = null, excludeFailures: [TransactionFailures](../-transaction-failures/index.md)? = null): Query&lt;[AccountToTransactionsConnection](../-account-to-transactions-connection/index.md)&gt; |
| [getUptimePercentageQuery](get-uptime-percentage-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getUptimePercentageQuery](get-uptime-percentage-query.md)(afterDate: Instant? = null, beforeDate: Instant? = null, bitcoinNetworks: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? = null): Query&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)?&gt; |

## Properties

| Name | Summary |
|---|---|
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [name](name.md) | [common]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
