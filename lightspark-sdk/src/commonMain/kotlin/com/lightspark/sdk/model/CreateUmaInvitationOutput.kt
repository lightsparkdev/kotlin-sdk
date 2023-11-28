// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateUmaInvitationOutput")
data class CreateUmaInvitationOutput(
    @SerialName("create_uma_invitation_output_invitation")
    val invitationId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment CreateUmaInvitationOutputFragment on CreateUmaInvitationOutput {
    type: __typename
    create_uma_invitation_output_invitation: invitation {
        id
    }
}"""
    }
}
