package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.OutgoingPayment

const val CreateTestModePayment = """
mutation CreateTestModePayment(
    ${'$'}encoded_invoice: String!
    ${'$'}amount_msats: Long
) {
    create_test_mode_payment(input: {
        encoded_invoice: ${'$'}encoded_invoice
        amount_msats: ${'$'}amount_msats
    }) {
        payment {
            ...OutgoingPaymentFragment
        }
    }
}

${OutgoingPayment.FRAGMENT}
"""
