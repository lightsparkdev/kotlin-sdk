package com.lightspark.sdk.model

import com.lightspark.api.type.TransactionType

data class Transaction(
    val id: String,
    val amount: CurrencyAmount,
    val status: String,
    val createdAt: String,
    val resolvedAt: String,
    val transactionHash: String,
    val type: TransactionType,
    val destination: String,
    val memo: String
)
