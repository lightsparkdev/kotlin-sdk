package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.UmaInvitation

const val FetchUmaInvitation = """
    query FetchUmaInvitation(
        ${'$'}invitation_code: String!
    ) {
        uma_invitation_by_code(code: ${'$'}invitation_code) {
            ...UmaInvitationFragment
        }
    }

${UmaInvitation.FRAGMENT}
"""
