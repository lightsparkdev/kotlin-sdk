// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("CreateInvitationWithIncentivesOutput")
data class CreateInvitationWithIncentivesOutput(
    @SerialName("create_invitation_with_incentives_output_invitation")
    val invitationId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment CreateInvitationWithIncentivesOutputFragment on CreateInvitationWithIncentivesOutput {
    type: __typename
    create_invitation_with_incentives_output_invitation: invitation {
        id
    }
}"""
    }
}
