//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[ChannelOpeningTransaction](index.md)

# ChannelOpeningTransaction

[common]\
@Serializable

data class [ChannelOpeningTransaction](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val status: [TransactionStatus](../-transaction-status/index.md), val amount: [CurrencyAmount](../-currency-amount/index.md), val blockHeight: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val destinationAddresses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val resolvedAt: Instant? = null, val transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val fees: [CurrencyAmount](../-currency-amount/index.md)? = null, val blockHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val numConfirmations: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, val channelId: [EntityId](../-entity-id/index.md)? = null) : [OnChainTransaction](../-on-chain-transaction/index.md), [Transaction](../-transaction/index.md), [Entity](../-entity/index.md)

The transaction on Bitcoin blockchain to open a channel on Lightning Network funded by the local Lightspark node.

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
| channelId | If known, the channel this transaction is opening. |

## Constructors

| | |
|---|---|
| [ChannelOpeningTransaction](-channel-opening-transaction.md) | [common]<br>fun [ChannelOpeningTransaction](-channel-opening-transaction.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [TransactionStatus](../-transaction-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), blockHeight: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), destinationAddresses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, resolvedAt: Instant? = null, transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, fees: [CurrencyAmount](../-currency-amount/index.md)? = null, blockHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, numConfirmations: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, channelId: [EntityId](../-entity-id/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>open override val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md) |
| [blockHash](block-hash.md) | [common]<br>open override val [blockHash](block-hash.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [blockHeight](block-height.md) | [common]<br>open override val [blockHeight](block-height.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [channelId](channel-id.md) | [common]<br>val [channelId](channel-id.md): [EntityId](../-entity-id/index.md)? = null |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [destinationAddresses](destination-addresses.md) | [common]<br>open override val [destinationAddresses](destination-addresses.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [fees](fees.md) | [common]<br>open override val [fees](fees.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [numConfirmations](num-confirmations.md) | [common]<br>open override val [numConfirmations](num-confirmations.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null |
| [resolvedAt](resolved-at.md) | [common]<br>open override val [resolvedAt](resolved-at.md): Instant? = null |
| [status](status.md) | [common]<br>open override val [status](status.md): [TransactionStatus](../-transaction-status/index.md) |
| [transactionHash](transaction-hash.md) | [common]<br>open override val [transactionHash](transaction-hash.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |