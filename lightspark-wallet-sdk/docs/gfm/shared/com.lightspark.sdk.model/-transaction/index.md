//[shared](../../../index.md)/[com.lightspark.sdk.model](../index.md)/[Transaction](index.md)

# Transaction

[common]\
data class [Transaction](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amount: [CurrencyAmount](../-currency-amount/index.md), val status: TransactionStatus, val createdAt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val resolvedAt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [Transaction.Type](-type/index.md), val otherAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)

Represents a transaction on the Lightning Network.

## Constructors

| | |
|---|---|
| [Transaction](-transaction.md) | [common]<br>fun [Transaction](-transaction.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amount: [CurrencyAmount](../-currency-amount/index.md), status: TransactionStatus, createdAt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), resolvedAt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [Transaction.Type](-type/index.md), otherAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) |

## Types

| Name | Summary |
|---|---|
| [Type](-type/index.md) | [common]<br>enum [Type](-type/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Transaction.Type](-type/index.md)&gt; <br>The type of transaction. |

## Properties

| Name | Summary |
|---|---|
| [amount](amount.md) | [common]<br>val [amount](amount.md): [CurrencyAmount](../-currency-amount/index.md) |
| [createdAt](created-at.md) | [common]<br>val [createdAt](created-at.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [common]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [memo](memo.md) | [common]<br>val [memo](memo.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [otherAddress](other-address.md) | [common]<br>val [otherAddress](other-address.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [resolvedAt](resolved-at.md) | [common]<br>val [resolvedAt](resolved-at.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [status](status.md) | [common]<br>val [status](status.md): TransactionStatus |
| [transactionHash](transaction-hash.md) | [common]<br>val [transactionHash](transaction-hash.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [type](type.md) | [common]<br>val [type](type.md): [Transaction.Type](-type/index.md) |
