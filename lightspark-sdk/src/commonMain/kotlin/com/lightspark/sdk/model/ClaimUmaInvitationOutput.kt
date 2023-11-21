// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("ClaimUmaInvitationOutput")
data class ClaimUmaInvitationOutput(
    @SerialName("claim_uma_invitation_output_invitation")
    val invitationId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment ClaimUmaInvitationOutputFragment on ClaimUmaInvitationOutput {
    type: __typename
    claim_uma_invitation_output_invitation: invitation {
        id
    }
}"""
    }
}
