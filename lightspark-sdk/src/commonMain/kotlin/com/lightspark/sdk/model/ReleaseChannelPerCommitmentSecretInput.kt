// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param channelId The unique identifier of the channel.
 * @param perCommitmentSecret The per-commitment secret to be released.
 * @param perCommitmentIndex The index associated with the per-commitment secret.
 */
@Serializable
@SerialName("ReleaseChannelPerCommitmentSecretInput")
data class ReleaseChannelPerCommitmentSecretInput(
    val channelId: String,
    val perCommitmentSecret: String,
    val perCommitmentIndex: Long,
) {
    companion object {
    }
}
