package com.lightspark.sdk.model

/**
 * Represents the data that is displayed on the wallet dashboard. Includes the account name, balance, and recent
 * transactions.
 */
data class WalletDashboardData(
    val accountName: String,
    val balance: CurrencyAmount,
    val recentTransactions: List<Transaction>
)
