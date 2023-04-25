//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[Wallet](index.md)/[getTransactionsQuery](get-transactions-query.md)

# getTransactionsQuery

[common]\

@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [getTransactionsQuery](get-transactions-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, after: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, createdAfterDate: Instant? = null, createdBeforeDate: Instant? = null, statuses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TransactionStatus](../-transaction-status/index.md)&gt;? = null, types: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TransactionType](../-transaction-type/index.md)&gt;? = null): Query&lt;[WalletToTransactionsConnection](../-wallet-to-transactions-connection/index.md)&gt;
