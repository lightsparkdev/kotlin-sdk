//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[OnChainTransaction](index.md)

# OnChainTransaction

[common]\
interface [OnChainTransaction](index.md) : [Transaction](../-transaction/index.md), [Entity](../-entity/index.md)

Transaction happened on Bitcoin blockchain.

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>abstract override val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md)<br>The amount of money involved in this transaction. |
| [blockHash](block-hash.md) | [common]<br>abstract val [blockHash](block-hash.md): String?<br>The hash of the block that included this transaction. This will be null for unconfirmed transactions. |
| [blockHeight](block-height.md) | [common]<br>abstract val [blockHeight](block-height.md): Int<br>The height of the block that included this transaction. This will be zero for unconfirmed transactions. |
| [createdAt](created-at.md) | [common]<br>abstract override val [createdAt](created-at.md): Instant<br>The date and time when this transaction was initiated. |
| [destinationAddresses](destination-addresses.md) | [common]<br>abstract val [destinationAddresses](destination-addresses.md): List&lt;String&gt;<br>The Bitcoin blockchain addresses this transaction was sent to. |
| [fees](fees.md) | [common]<br>abstract val [fees](fees.md): [CurrencyAmount](../-currency-amount/index.md)?<br>The fees that were paid by the wallet sending the transaction to commit it to the Bitcoin blockchain. |
| [id](id.md) | [common]<br>abstract override val [id](id.md): String<br>The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| [numConfirmations](num-confirmations.md) | [common]<br>abstract val [numConfirmations](num-confirmations.md): Int?<br>The number of blockchain confirmations for this transaction in real time. |
| [resolvedAt](resolved-at.md) | [common]<br>abstract override val [resolvedAt](resolved-at.md): Instant?<br>The date and time when this transaction was completed or failed. |
| [status](status.md) | [common]<br>abstract override val [status](status.md): [TransactionStatus](../-transaction-status/index.md)<br>The current status of this transaction. |
| [transactionHash](transaction-hash.md) | [common]<br>abstract override val [transactionHash](transaction-hash.md): String?<br>The hash of this transaction, so it can be uniquely identified on the Lightning Network. |
| [updatedAt](updated-at.md) | [common]<br>abstract override val [updatedAt](updated-at.md): Instant<br>The date and time when the entity was last updated. |

## Inheritors

| Name |
|---|
| [ChannelClosingTransaction](../-channel-closing-transaction/index.md) |
| [ChannelOpeningTransaction](../-channel-opening-transaction/index.md) |
| [Deposit](../-deposit/index.md) |
| [Withdrawal](../-withdrawal/index.md) |
