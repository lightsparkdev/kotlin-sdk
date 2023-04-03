// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = TransactionStatusSerializer::class)
enum class TransactionStatus(val rawValue: String) {
    /** Transaction succeeded.. **/
    SUCCESS("SUCCESS"),

    /** Transaction failed. **/
    FAILED("FAILED"),

    /** Transaction has been initiated and is currently in-flight. **/
    PENDING("PENDING"),

    /** For transaction type PAYMENT_REQUEST only. No payments have been made to a payment request. **/
    NOT_STARTED("NOT_STARTED"),

    /** For transaction type PAYMENT_REQUEST only. A payment request has expired. **/
    EXPIRED("EXPIRED"),

    /** For transaction type PAYMENT_REQUEST only. **/
    CANCELLED("CANCELLED"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object TransactionStatusSerializer :
    EnumSerializer<TransactionStatus>(
        TransactionStatus::class,
        { rawValue ->
            TransactionStatus.values().firstOrNull { it.rawValue == rawValue } ?: TransactionStatus.FUTURE_VALUE
        },
    )
