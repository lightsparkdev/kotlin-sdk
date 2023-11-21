package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.UmaInvitation

const val ClaimUmaInvitation = """
    mutation ClaimUmaInvitation(
        ${'$'}invitation_code: String!
        ${'$'}invitee_uma: String!
    ) {
        claim_uma_invitation(input: {
            invitation_code: ${'$'}invitation_code
            invitee_uma: ${'$'}invitee_uma
        }) {
            invitation {
                ...UmaInvitationFragment
            }
        }
    }

${UmaInvitation.FRAGMENT}
"""
