// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import com.lightspark.sdk.core.util.EnumSerializer
import kotlinx.serialization.Serializable

@Serializable(with = KeyTypeSerializer::class)
enum class KeyType(
    val rawValue: String,
) {
    RSA_OAEP("RSA_OAEP"),

    ELLIPTIC_CURVE("ELLIPTIC_CURVE"),

    ED25519("ED25519"),

    /**
     * This is an enum value that represents values that could be added in the future.
     * Clients should support unknown values as more of them could be added without notice.
     */
    FUTURE_VALUE("FUTURE_VALUE"),
}

object KeyTypeSerializer :
    EnumSerializer<KeyType>(
        KeyType::class,
        { rawValue ->
            KeyType.values().firstOrNull { it.rawValue == rawValue } ?: KeyType.FUTURE_VALUE
        },
    )
