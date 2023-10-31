// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param id The id of the message.
 * @param signature The signature of the message.
 */
@Serializable
@SerialName("IdAndSignature")
data class IdAndSignature(
    val id: String,
    val signature: String,
) {
    companion object {
    }
}
