// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param inviterUma The UMA of the user creating the invitation. It will be used to identify the inviter when receiving the invitation.
 * @param inviterPhoneHash The phone hash of the user creating the invitation.
 * @param inviterRegion The region of the user creating the invitation.
 */
@Serializable
@SerialName("CreateInvitationWithIncentivesInput")
data class CreateInvitationWithIncentivesInput(
    val inviterUma: String,
    val inviterPhoneHash: String,
    val inviterRegion: RegionCode,
) {
    companion object {
    }
}
