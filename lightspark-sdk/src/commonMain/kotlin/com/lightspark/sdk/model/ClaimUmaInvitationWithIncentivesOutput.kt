// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param invitationId An UMA.ME invitation object.
 */
@Serializable
@SerialName("ClaimUmaInvitationWithIncentivesOutput")
data class ClaimUmaInvitationWithIncentivesOutput(
    @SerialName("claim_uma_invitation_with_incentives_output_invitation")
    val invitationId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment ClaimUmaInvitationWithIncentivesOutputFragment on ClaimUmaInvitationWithIncentivesOutput {
    type: __typename
    claim_uma_invitation_with_incentives_output_invitation: invitation {
        id
    }
}"""
    }
}
