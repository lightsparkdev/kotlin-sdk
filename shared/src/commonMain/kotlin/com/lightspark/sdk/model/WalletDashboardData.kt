package com.lightspark.sdk.model

data class WalletDashboardData(
    val accountName: String,
    val balance: CurrencyAmount,
    val recentTransactions: List<Transaction>
)
