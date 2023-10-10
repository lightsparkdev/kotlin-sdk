package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.IncomingPayment

const val CreateTestModePayment = """
mutation CreateTestModePayment(
    ${'$'}local_node_id: ID!
    ${'$'}encoded_invoice: String!
    ${'$'}amount_msats: Long
) {
    create_test_mode_payment(input: {
        local_node_id: ${'$'}local_node_id
        encoded_invoice: ${'$'}encoded_invoice
        amount_msats: ${'$'}amount_msats
    }) {
        incoming_payment {
            ...IncomingPaymentFragment
        }
    }
}

${IncomingPayment.FRAGMENT}
"""
