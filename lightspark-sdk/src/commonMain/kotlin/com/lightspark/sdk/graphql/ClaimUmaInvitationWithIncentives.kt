package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.UmaInvitation

const val ClaimUmaInvitationWithIncentives = """
    mutation ClaimUmaInvitationWithIncentives(
        ${'$'}invitation_code: String!
        ${'$'}invitee_uma: String!
        ${'$'}invitee_phone_hash: String!
        ${'$'}invitee_region: RegionCode!
    ) {
        claim_uma_invitation_with_incentives(input: {
            invitation_code: ${'$'}invitation_code
            invitee_uma: ${'$'}invitee_uma
            invitee_phone_hash: ${'$'}invitee_phone_hash
            invitee_region: ${'$'}invitee_region
        }) {
            invitation {
                ...UmaInvitationFragment
            }
        }
    }

${UmaInvitation.FRAGMENT}
"""
