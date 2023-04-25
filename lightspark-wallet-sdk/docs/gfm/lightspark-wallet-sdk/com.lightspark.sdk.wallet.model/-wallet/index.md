//[lightspark-wallet-sdk](../../../index.md)/[com.lightspark.sdk.wallet.model](../index.md)/[Wallet](index.md)

# Wallet

[common]\
@Serializable

data class [Wallet](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createdAt: Instant, val updatedAt: Instant, val status: [WalletStatus](../-wallet-status/index.md), val balances: [Balances](../-balances/index.md)? = null) : [Entity](../-entity/index.md)

#### Parameters

common

| | |
|---|---|
| id | The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string. |
| createdAt | The date and time when the entity was first created. |
| updatedAt | The date and time when the entity was last updated. |
| status | The status of this wallet. |
| balances | The balances that describe the funds in this wallet. |

## Constructors

| | |
|---|---|
| [Wallet](-wallet.md) | [common]<br>fun [Wallet](-wallet.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createdAt: Instant, updatedAt: Instant, status: [WalletStatus](../-wallet-status/index.md), balances: [Balances](../-balances/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [common]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getPaymentRequestsQuery](get-payment-requests-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getPaymentRequestsQuery](get-payment-requests-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, after: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, createdAfterDate: Instant? = null, createdBeforeDate: Instant? = null): Query&lt;[WalletToPaymentRequestsConnection](../-wallet-to-payment-requests-connection/index.md)&gt; |
| [getTransactionsQuery](get-transactions-query.md) | [common]<br>@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)<br>fun [getTransactionsQuery](get-transactions-query.md)(first: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, after: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, createdAfterDate: Instant? = null, createdBeforeDate: Instant? = null, statuses: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TransactionStatus](../-transaction-status/index.md)&gt;? = null, types: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TransactionType](../-transaction-type/index.md)&gt;? = null): Query&lt;[WalletToTransactionsConnection](../-wallet-to-transactions-connection/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [balances](balances.md) | [common]<br>val [balances](balances.md): [Balances](../-balances/index.md)? = null |
| [createdAt](created-at.md) | [common]<br>open override val [createdAt](created-at.md): Instant |
| [id](id.md) | [common]<br>open override val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [status](status.md) | [common]<br>val [status](status.md): [WalletStatus](../-wallet-status/index.md) |
| [updatedAt](updated-at.md) | [common]<br>open override val [updatedAt](updated-at.md): Instant |
