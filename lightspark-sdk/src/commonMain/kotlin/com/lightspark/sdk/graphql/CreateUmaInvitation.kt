package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.UmaInvitation

const val CreateUmaInvitation = """
    mutation CreateUmaInvitation(
        ${'$'}inviter_uma: String!
    ) {
        create_uma_invitation(input: {
            inviter_uma: ${'$'}inviter_uma
        }) {
            invitation {
                ...UmaInvitationFragment
            }
        }
    }

${UmaInvitation.FRAGMENT}
"""
