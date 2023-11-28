// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum of the potential event types that can be associated with your Lightspark wallets. **/
@Serializable(with = WebhookEventTypeSerializer::class)
enum class WebhookEventType(val rawValue: String) {
    PAYMENT_FINISHED("PAYMENT_FINISHED"),

    FORCE_CLOSURE("FORCE_CLOSURE"),

    WITHDRAWAL_FINISHED("WITHDRAWAL_FINISHED"),

    FUNDS_RECEIVED("FUNDS_RECEIVED"),

    NODE_STATUS("NODE_STATUS"),

    UMA_INVITATION_CLAIMED("UMA_INVITATION_CLAIMED"),

    WALLET_STATUS("WALLET_STATUS"),

    WALLET_OUTGOING_PAYMENT_FINISHED("WALLET_OUTGOING_PAYMENT_FINISHED"),

    WALLET_INCOMING_PAYMENT_FINISHED("WALLET_INCOMING_PAYMENT_FINISHED"),

    WALLET_WITHDRAWAL_FINISHED("WALLET_WITHDRAWAL_FINISHED"),

    WALLET_FUNDS_RECEIVED("WALLET_FUNDS_RECEIVED"),

    REMOTE_SIGNING("REMOTE_SIGNING"),

    LOW_BALANCE("LOW_BALANCE"),

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
