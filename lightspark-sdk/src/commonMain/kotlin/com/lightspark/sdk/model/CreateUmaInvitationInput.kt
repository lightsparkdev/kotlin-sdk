// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param inviterUma The UMA of the user creating the invitation. It will be used to identify the inviter when receiving the invitation.
 */
@Serializable
@SerialName("CreateUmaInvitationInput")
data class CreateUmaInvitationInput(
    val inviterUma: String,
) {
    companion object {
    }
}
