// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("UpdateChannelPerCommitmentPointOutput")
data class UpdateChannelPerCommitmentPointOutput(
    @SerialName("update_channel_per_commitment_point_output_channel")
    val channelId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment UpdateChannelPerCommitmentPointOutputFragment on UpdateChannelPerCommitmentPointOutput {
    type: __typename
    update_channel_per_commitment_point_output_channel: channel {
        id
    }
}"""
    }
}
