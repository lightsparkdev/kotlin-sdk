//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[Account](index.md)/[getTransactionsQuery](get-transactions-query.md)

# getTransactionsQuery

[common]\

@JvmOverloads

fun [getTransactionsQuery](get-transactions-query.md)(first: Int? = null, after: String? = null, types: List&lt;[TransactionType](../-transaction-type/index.md)&gt;? = null, afterDate: Instant? = null, beforeDate: Instant? = null, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md)? = null, lightningNodeId: String? = null, statuses: List&lt;[TransactionStatus](../-transaction-status/index.md)&gt;? = null, excludeFailures: [TransactionFailures](../-transaction-failures/index.md)? = null): Query&lt;[AccountToTransactionsConnection](../-account-to-transactions-connection/index.md)&gt;
