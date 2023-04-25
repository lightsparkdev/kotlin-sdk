//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[Account](index.md)/[getTransactionsQuery](get-transactions-query.md)

# getTransactionsQuery

[common]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [getTransactionsQuery](get-transactions-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, after: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, types: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TransactionType](../-transaction-type/index.md)&gt;? = null, afterDate: Instant? = null, beforeDate: Instant? = null, bitcoinNetwork: [BitcoinNetwork](../-bitcoin-network/index.md)? = null, lightningNodeId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, statuses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TransactionStatus](../-transaction-status/index.md)&gt;? = null, excludeFailures: [TransactionFailures](../-transaction-failures/index.md)? = null): Query&lt;[AccountToTransactionsConnection](../-account-to-transactions-connection/index.md)&gt;
