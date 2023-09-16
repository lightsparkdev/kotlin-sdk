// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param payloadIds List of payload ids to decline to sign because validation failed.
 */
@Serializable
@SerialName("DeclineToSignMessagesInput")
data class DeclineToSignMessagesInput(
    val payloadIds: List<String>,
) {
    companion object {
    }
}
