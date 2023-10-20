// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param channelId The channel object after the per-commitment secret release operation.
 */
@Serializable
@SerialName("ReleaseChannelPerCommitmentSecretOutput")
data class ReleaseChannelPerCommitmentSecretOutput(
    @SerialName("release_channel_per_commitment_secret_output_channel")
    val channelId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment ReleaseChannelPerCommitmentSecretOutputFragment on ReleaseChannelPerCommitmentSecretOutput {
    type: __typename
    release_channel_per_commitment_secret_output_channel: channel {
        id
    }
}"""
    }
}
