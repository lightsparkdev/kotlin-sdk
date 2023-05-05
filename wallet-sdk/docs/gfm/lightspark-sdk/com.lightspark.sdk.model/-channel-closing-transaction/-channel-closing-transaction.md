//[lightspark-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[ChannelClosingTransaction](index.md)/[ChannelClosingTransaction](-channel-closing-transaction.md)

# ChannelClosingTransaction

[common]\
fun [ChannelClosingTransaction](-channel-closing-transaction.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [TransactionStatus](../-transaction-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), blockHeight: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), destinationAddresses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, resolvedAt: Instant? = null, transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, fees: [CurrencyAmount](../-currency-amount/index.md)? = null, blockHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, numConfirmations: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, channelId: [EntityId](../-entity-id/index.md)? = null)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when this transaction was initiated. |
| updatedAt | The date and time when the entity was last updated. |
| status | The current status of this transaction. |
| amount | The amount of money involved in this transaction. |
| blockHeight | The height of the block that included this transaction. This will be zero for unconfirmed transactions. |
| destinationAddresses | The Bitcoin blockchain addresses this transaction was sent to. |
| resolvedAt | The date and time when this transaction was completed or failed. |
| transactionHash | The hash of this transaction, so it can be uniquely identified on the Lightning Network. |
| fees | The fees that were paid by the wallet sending the transaction to commit it to the Bitcoin blockchain. |
| blockHash | The hash of the block that included this transaction. This will be null for unconfirmed transactions. |
| numConfirmations | The number of blockchain confirmations for this transaction in real time. |
| channelId | If known, the channel this transaction is closing. |
