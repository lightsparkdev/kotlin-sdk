//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[Account](index.md)

# Account

[common]\
@Serializable

data class [Account](index.md)(val id: String, val createdAt: Instant, val updatedAt: Instant, val name: String? = null) : [Entity](../-entity/index.md)

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
| [Account](-account.md) | [common]<br>fun [Account](-account.md)(id: String, createdAt: Instant, updatedAt: Instant, name: String? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getApiTokensQuery](get-api-tokens-query.md) | [common]<br>@JvmOverloads<br>fun [getApiTokensQuery](get-api-tokens-query.md)(first: Int? = null): Query&lt;[AccountToApiTokensConnection](../-account-to-api-tokens-connection/index.md)&gt; |
| [getBlockchainBalanceQuery](get-blockchain-balance-query.md) | [common]<br>@JvmOverloads<br>fun [getBlockchainBalanceQuery](get-blockchain-balance-query.md)(bitcoinNetworks: List&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: List&lt;String&gt;? = null): Query&lt;[BlockchainBalance](../-blockchain-balance/index.md)?&gt; |
| [getChannelsQuery](get-channels-query.md) | [common]<br>@JvmOverloads<br>fun [getChannelsQuery](get-channels-query.md)(bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md), lightningNodeId: String? = null, afterDate: Instant? = null, beforeDate: Instant? = null, first: Int? = null): Query&lt;[AccountToChannelsConnection](../-account-to-channels-connection/index.md)&gt; |
| [getConductivityQuery](get-conductivity-query.md) | [common]<br>@JvmOverloads<br>fun [getConductivityQuery](get-conductivity-query.md)(bitcoinNetworks: List&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: List&lt;String&gt;? = null): Query&lt;Int?&gt; |
| [getLocalBalanceQuery](get-local-balance-query.md) | [common]<br>@JvmOverloads<br>fun [getLocalBalanceQuery](get-local-balance-query.md)(bitcoinNetworks: List&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: List&lt;String&gt;? = null): Query&lt;[CurrencyAmount](../-currency-amount/index.md)?&gt; |
| [getNodesQuery](get-nodes-query.md) | [common]<br>@JvmOverloads<br>fun [getNodesQuery](get-nodes-query.md)(first: Int? = null, bitcoinNetworks: List&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: List&lt;String&gt;? = null): Query&lt;[AccountToNodesConnection](../-account-to-nodes-connection/index.md)&gt; |
| [getPaymentRequestsQuery](get-payment-requests-query.md) | [common]<br>@JvmOverloads<br>fun [getPaymentRequestsQuery](get-payment-requests-query.md)(first: Int? = null, after: String? = null, afterDate: Instant? = null, beforeDate: Instant? = null, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md)? = null, lightningNodeId: String? = null): Query&lt;[AccountToPaymentRequestsConnection](../-account-to-payment-requests-connection/index.md)&gt; |
| [getRemoteBalanceQuery](get-remote-balance-query.md) | [common]<br>@JvmOverloads<br>fun [getRemoteBalanceQuery](get-remote-balance-query.md)(bitcoinNetworks: List&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: List&lt;String&gt;? = null): Query&lt;[CurrencyAmount](../-currency-amount/index.md)?&gt; |
| [getTransactionsQuery](get-transactions-query.md) | [common]<br>@JvmOverloads<br>fun [getTransactionsQuery](get-transactions-query.md)(first: Int? = null, after: String? = null, types: List&lt;[TransactionType](../-transaction-type/index.md)&gt;? = null, afterDate: Instant? = null, beforeDate: Instant? = null, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md)? = null, lightningNodeId: String? = null, statuses: List&lt;[TransactionStatus](../-transaction-status/index.md)&gt;? = null, excludeFailures: [TransactionFailures](../-transaction-failures/index.md)? = null): Query&lt;[AccountToTransactionsConnection](../-account-to-transactions-connection/index.md)&gt; |
| [getUptimePercentageQuery](get-uptime-percentage-query.md) | [common]<br>@JvmOverloads<br>fun [getUptimePercentageQuery](get-uptime-percentage-query.md)(afterDate: Instant? = null, beforeDate: Instant? = null, bitcoinNetworks: List&lt;[BitcoinNetwork](../-bitcoin-network/index.md)&gt;? = null, nodeIds: List&lt;String&gt;? = null): Query&lt;Int?&gt; |

## Properties

| Name | Summary |
|---|---|
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [id](id.md) | [common]<br>open override val [id](id.md): String |
| [name](name.md) | [common]<br>val [name](name.md): String? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
