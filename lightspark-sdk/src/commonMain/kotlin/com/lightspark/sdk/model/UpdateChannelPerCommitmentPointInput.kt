// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("UpdateChannelPerCommitmentPointInput")
data class UpdateChannelPerCommitmentPointInput(
    val channelId: String,
    val perCommitmentPoint: String,
    val perCommitmentPointIndex: Long,
) {
    companion object {
    }
}
