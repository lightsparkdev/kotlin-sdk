// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param invitationCode The unique code that identifies this invitation and was shared by the inviter.
 * @param inviteeUma The UMA of the user claiming the invitation. It will be sent to the inviter so that they can start transacting with the invitee.
 * @param inviteePhoneHash The phone hash of the user getting the invitation.
 * @param inviteeRegion The region of the user getting the invitation.
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
