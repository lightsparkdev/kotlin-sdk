// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = InvoiceTypeSerializer::class)
enum class InvoiceType(val rawValue: String) {
    /** A standard Bolt 11 invoice. **/
    STANDARD("STANDARD"),

    /** An AMP (Atomic Multi-path Payment) invoice. **/
    AMP("AMP"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object InvoiceTypeSerializer :
    EnumSerializer<InvoiceType>(
        InvoiceType::class,
        { rawValue ->
            InvoiceType.values().firstOrNull { it.rawValue == rawValue } ?: InvoiceType.FUTURE_VALUE
        },
    )
