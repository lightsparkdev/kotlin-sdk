package com.lightspark.sdk.server.graphql

import com.lightspark.sdk.server.model.Transaction

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
