//[lightspark-sdk](../../index.md)/[com.lightspark.sdk.model](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [CurrencyAmount](-currency-amount/index.md) | [common]<br>data class [CurrencyAmount](-currency-amount/index.md)(val amount: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val unit: CurrencyUnit)<br>A currency amount including the unit. |
| [FeeEstimate](-fee-estimate/index.md) | [common]<br>data class [FeeEstimate](-fee-estimate/index.md)(val feeFast: [CurrencyAmount](-currency-amount/index.md), val feeMin: [CurrencyAmount](-currency-amount/index.md)) |
| [Transaction](-transaction/index.md) | [common]<br>data class [Transaction](-transaction/index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val amount: [CurrencyAmount](-currency-amount/index.md), val status: TransactionStatus, val createdAt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val resolvedAt: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [Transaction.Type](-transaction/-type/index.md), val otherAddress: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val memo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val transactionHash: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Represents a transaction on the Lightning Network. |
| [WalletDashboardData](-wallet-dashboard-data/index.md) | [common]<br>data class [WalletDashboardData](-wallet-dashboard-data/index.md)(val accountName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val balance: [CurrencyAmount](-currency-amount/index.md), val recentTransactions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Transaction](-transaction/index.md)&gt;)<br>Represents the data that is displayed on the wallet dashboard. Includes the account name, balance, and recent transactions. |
