//[lightspark-server-sdk](../../../index.md)/[com.lightspark.sdk.server.model](../index.md)/[Deposit](index.md)

# Deposit

[common]\
@Serializable

data class [Deposit](index.md)(val id: String, val createdAt: Instant, val updatedAt: Instant, val status: [TransactionStatus](../-transaction-status/index.md), val amount: [CurrencyAmount](../-currency-amount/index.md), val blockHeight: Int, val destinationAddresses: List&lt;String&gt;, val destinationId: [EntityId](../-entity-id/index.md), val resolvedAt: Instant? = null, val transactionHash: String? = null, val fees: [CurrencyAmount](../-currency-amount/index.md)? = null, val blockHash: String? = null, val numConfirmations: Int? = null) : [OnChainTransaction](../-on-chain-transaction/index.md), [Transaction](../-transaction/index.md), [Entity](../-entity/index.md)

The transaction on Bitcoin blockchain to fund the Lightspark node's wallet.

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
| destinationId | The recipient Lightspark node this deposit was sent to. |
| resolvedAt | The date and time when this transaction was completed or failed. |
| transactionHash | The hash of this transaction, so it can be uniquely identified on the Lightning Network. |
| fees | The fees that were paid by the wallet sending the transaction to commit it to the Bitcoin blockchain. |
| blockHash | The hash of the block that included this transaction. This will be null for unconfirmed transactions. |
| numConfirmations | The number of blockchain confirmations for this transaction in real time. |

## Constructors

| | |
|---|---|
| [Deposit](-deposit.md) | [common]<br>fun [Deposit](-deposit.md)(id: String, createdAt: Instant, updatedAt: Instant, status: [TransactionStatus](../-transaction-status/index.md), amount: [CurrencyAmount](../-currency-amount/index.md), blockHeight: Int, destinationAddresses: List&lt;String&gt;, destinationId: [EntityId](../-entity-id/index.md), resolvedAt: Instant? = null, transactionHash: String? = null, fees: [CurrencyAmount](../-currency-amount/index.md)? = null, blockHash: String? = null, numConfirmations: Int? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>open override val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md) |
| [blockHash](block-hash.md) | [common]<br>open override val [blockHash](block-hash.md): String? = null |
| [blockHeight](block-height.md) | [common]<br>open override val [blockHeight](block-height.md): Int |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [destinationAddresses](destination-addresses.md) | [common]<br>open override val [destinationAddresses](destination-addresses.md): List&lt;String&gt; |
| [destinationId](destination-id.md) | [common]<br>val [destinationId](destination-id.md): [EntityId](../-entity-id/index.md) |
| [fees](fees.md) | [common]<br>open override val [fees](fees.md): [CurrencyAmount](../-currency-amount/index.md)? = null |
| [id](id.md) | [common]<br>open override val [id](id.md): String |
| [numConfirmations](num-confirmations.md) | [common]<br>open override val [numConfirmations](num-confirmations.md): Int? = null |
| [resolvedAt](resolved-at.md) | [common]<br>open override val [resolvedAt](resolved-at.md): Instant? = null |
| [status](status.md) | [common]<br>open override val [status](status.md): [TransactionStatus](../-transaction-status/index.md) |
| [transactionHash](transaction-hash.md) | [common]<br>open override val [transactionHash](transaction-hash.md): String? = null |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
