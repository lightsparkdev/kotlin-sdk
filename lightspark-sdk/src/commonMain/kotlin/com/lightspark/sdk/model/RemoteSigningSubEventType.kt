// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum of the potential sub-event types for Remote Signing webook events. **/
@Serializable(with = RemoteSigningSubEventTypeSerializer::class)
enum class RemoteSigningSubEventType(val rawValue: String) {
    ECDH("ECDH"),

    GET_PER_COMMITMENT_POINT("GET_PER_COMMITMENT_POINT"),

    RELEASE_PER_COMMITMENT_SECRET("RELEASE_PER_COMMITMENT_SECRET"),

    SIGN_INVOICE("SIGN_INVOICE"),

    DERIVE_KEY_AND_SIGN("DERIVE_KEY_AND_SIGN"),

    RELEASE_PAYMENT_PREIMAGE("RELEASE_PAYMENT_PREIMAGE"),

    REQUEST_INVOICE_PAYMENT_HASH("REQUEST_INVOICE_PAYMENT_HASH"),

    REVEAL_COUNTERPARTY_PER_COMMITMENT_SECRET("REVEAL_COUNTERPARTY_PER_COMMITMENT_SECRET"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object RemoteSigningSubEventTypeSerializer :
    EnumSerializer<RemoteSigningSubEventType>(
        RemoteSigningSubEventType::class,
        { rawValue ->
            RemoteSigningSubEventType.values().firstOrNull { it.rawValue == rawValue } ?: RemoteSigningSubEventType.FUTURE_VALUE
        },
    )
