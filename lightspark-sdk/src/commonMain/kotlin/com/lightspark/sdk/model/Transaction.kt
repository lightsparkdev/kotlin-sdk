package com.lightspark.sdk.model

import com.lightspark.api.type.TransactionStatus

/**
 * Represents a transaction on the Lightning Network.
 */
data class Transaction(
    val id: String,
    val amount: CurrencyAmount,
    val status: TransactionStatus,
    val createdAt: String,
    val resolvedAt: String,
    val type: Type,
    val otherAddress: String?,
    val memo: String?,
    val transactionHash: String?
) {
    /**
     * The type of transaction.
     */
    enum class Type {
        DEPOSIT,
        WITHDRAWAL,
        PAYMENT_REQUEST,
        PAYMENT_SENT,
        PAYMENT_RECEIVED,
        UNKNOWN
    }
}
