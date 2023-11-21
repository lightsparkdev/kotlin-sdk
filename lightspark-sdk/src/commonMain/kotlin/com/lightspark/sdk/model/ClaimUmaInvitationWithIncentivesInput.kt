// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("ClaimUmaInvitationWithIncentivesInput")
data class ClaimUmaInvitationWithIncentivesInput(
    val invitationCode: String,
    val inviteeUma: String,
    val inviteePhoneHash: String,
    val inviteeRegion: RegionCode,
) {
    companion object {
    }
}
