// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum representing a particular reason why an htlc sent over the Lightning Network may have failed. **/
@Serializable(with = HtlcAttemptFailureCodeSerializer::class)
enum class HtlcAttemptFailureCode(val rawValue: String) {
    INCORRECT_OR_UNKNOWN_PAYMENT_DETAILS("INCORRECT_OR_UNKNOWN_PAYMENT_DETAILS"),

    INCORRECT_PAYMENT_AMOUNT("INCORRECT_PAYMENT_AMOUNT"),

    FINAL_INCORRECT_CLTV_EXPIRY("FINAL_INCORRECT_CLTV_EXPIRY"),

    FINAL_INCORRECT_HTLC_AMOUNT("FINAL_INCORRECT_HTLC_AMOUNT"),

    FINAL_EXPIRY_TOO_SOON("FINAL_EXPIRY_TOO_SOON"),

    INVALID_REALM("INVALID_REALM"),

    EXPIRY_TOO_SOON("EXPIRY_TOO_SOON"),

    INVALID_ONION_VERSION("INVALID_ONION_VERSION"),

    INVALID_ONION_HMAC("INVALID_ONION_HMAC"),

    INVALID_ONION_KEY("INVALID_ONION_KEY"),

    AMOUNT_BELOW_MINIMUM("AMOUNT_BELOW_MINIMUM"),

    FEE_INSUFFICIENT("FEE_INSUFFICIENT"),

    INCORRECT_CLTV_EXPIRY("INCORRECT_CLTV_EXPIRY"),

    CHANNEL_DISABLED("CHANNEL_DISABLED"),

    TEMPORARY_CHANNEL_FAILURE("TEMPORARY_CHANNEL_FAILURE"),

    REQUIRED_NODE_FEATURE_MISSING("REQUIRED_NODE_FEATURE_MISSING"),

    REQUIRED_CHANNEL_FEATURE_MISSING("REQUIRED_CHANNEL_FEATURE_MISSING"),

    UNKNOWN_NEXT_PEER("UNKNOWN_NEXT_PEER"),

    TEMPORARY_NODE_FAILURE("TEMPORARY_NODE_FAILURE"),

    PERMANENT_NODE_FAILURE("PERMANENT_NODE_FAILURE"),

    PERMANENT_CHANNEL_FAILURE("PERMANENT_CHANNEL_FAILURE"),

    EXPIRY_TOO_FAR("EXPIRY_TOO_FAR"),

    MPP_TIMEOUT("MPP_TIMEOUT"),

    INVALID_ONION_PAYLOAD("INVALID_ONION_PAYLOAD"),

    INTERNAL_FAILURE("INTERNAL_FAILURE"),

    UNKNOWN_FAILURE("UNKNOWN_FAILURE"),

    UNREADABLE_FAILURE("UNREADABLE_FAILURE"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object HtlcAttemptFailureCodeSerializer :
    EnumSerializer<HtlcAttemptFailureCode>(
        HtlcAttemptFailureCode::class,
        { rawValue ->
            HtlcAttemptFailureCode.values().firstOrNull { it.rawValue == rawValue } ?: HtlcAttemptFailureCode.FUTURE_VALUE
        },
    )
