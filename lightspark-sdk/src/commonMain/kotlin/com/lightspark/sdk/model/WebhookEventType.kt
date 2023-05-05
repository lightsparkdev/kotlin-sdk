// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = WebhookEventTypeSerializer::class)
enum class WebhookEventType(val rawValue: String) {

    PAYMENT_FINISHED("PAYMENT_FINISHED"),

    NODE_STATUS("NODE_STATUS"),

    WALLET_STATUS("WALLET_STATUS"),

    WALLET_OUTGOING_PAYMENT_FINISHED("WALLET_OUTGOING_PAYMENT_FINISHED"),

    WALLET_INCOMING_PAYMENT_FINISHED("WALLET_INCOMING_PAYMENT_FINISHED"),

    WALLET_WITHDRAWAL_FINISHED("WALLET_WITHDRAWAL_FINISHED"),

    WALLET_FUNDS_RECEIVED("WALLET_FUNDS_RECEIVED"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object WebhookEventTypeSerializer :
    EnumSerializer<WebhookEventType>(
        WebhookEventType::class,
        { rawValue ->
            WebhookEventType.values().firstOrNull { it.rawValue == rawValue } ?: WebhookEventType.FUTURE_VALUE
        },
    )
