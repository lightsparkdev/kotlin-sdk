package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.UmaInvitation

const val CreateUmaInvitationWithPayment = """
    mutation CreateUmaInvitationWithPayment(
        ${'$'}inviter_uma: String!
        ${'$'}payment_amount: Long!
        ${'$'}payment_currency: PaymentCurrencyInput!
        ${'$'}expires_at: DateTime!
    ) {
        create_uma_invitation_with_payment(input: {
            inviter_uma: ${'$'}inviter_uma
            payment_amount: ${'$'}payment_amount
            payment_currency: ${'$'}payment_currency
            expires_at: ${'$'}expires_at
        }) {
            invitation {
                ...UmaInvitationFragment
            }
        }
    }

${UmaInvitation.FRAGMENT}
""" 