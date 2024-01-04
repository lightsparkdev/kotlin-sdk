// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

/** This is an enum identifying the payment direction. **/
@Serializable(with = LightningPaymentDirectionSerializer::class)
enum class LightningPaymentDirection(val rawValue: String) {
    /** A payment that is received by the node. **/
    INCOMING("INCOMING"),

    /** A payment that is sent by the node. **/
    OUTGOING("OUTGOING"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object LightningPaymentDirectionSerializer :
    EnumSerializer<LightningPaymentDirection>(
        LightningPaymentDirection::class,
        { rawValue ->
            LightningPaymentDirection.values().firstOrNull { it.rawValue == rawValue } ?: LightningPaymentDirection.FUTURE_VALUE
        },
    )
