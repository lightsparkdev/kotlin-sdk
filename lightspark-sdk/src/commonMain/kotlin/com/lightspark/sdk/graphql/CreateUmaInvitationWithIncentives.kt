package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.UmaInvitation

const val CreateUmaInvitationWithIncentives = """
    mutation CreateUmaInvitationWithIncentives(
        ${'$'}inviter_uma: String!
        ${'$'}inviter_phone_hash: String!
        ${'$'}inviter_region: RegionCode!
    ) {
        create_uma_invitation_with_incentives(input: {
            inviter_uma: ${'$'}inviter_uma
            inviter_phone_hash: ${'$'}inviter_phone_hash
            inviter_region: ${'$'}inviter_region
        }) {
            invitation {
                ...UmaInvitationFragment
            }
        }
    }

${UmaInvitation.FRAGMENT}
"""
