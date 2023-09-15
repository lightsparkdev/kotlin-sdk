package com.lightspark.sdk.graphql

const val RegisterPaymentMutation = """
mutation RegisterPayment(
    ${'S'}provider: ComplianceProvider!
    ${'S'}payment_id: ID!
    ${'S'}node_pubkey: String!
    ${'S'}direction: PaymentDirection!
) {
    register_payment(input: {
        provider: ${'S'}provider
        payment_id: ${'S'}payment_id
        node_pubkey: ${'S'}node_pubkey
        direction: ${'S'}direction
    }) {
        payment {
            id
        }
    }
}
"""
