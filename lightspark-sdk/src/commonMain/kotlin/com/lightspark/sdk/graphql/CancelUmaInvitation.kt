package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.UmaInvitation

const val CancelUmaInvitation = """
    mutation CancelUmaInvitation(
        ${'$'}invite_code: String!
    ) {
        cancel_uma_invitation(input: {
            invite_code: ${'$'}invite_code
        }) {
            invitation {
                ...UmaInvitationFragment
            }
        }
    }

${UmaInvitation.FRAGMENT}
""" 