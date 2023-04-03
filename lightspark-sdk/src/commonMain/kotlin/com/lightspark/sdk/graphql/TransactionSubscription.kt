package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.Transaction

const val TransactionSubscriptionQuery = """
subscription TransactionSubscription(
    ${'$'}nodeIds: [ID!]!
) {
    transactions(node_ids: ${'$'}nodeIds) {
        ...TransactionFragment
        __typename
    }
}

${Transaction.FRAGMENT}
"""
