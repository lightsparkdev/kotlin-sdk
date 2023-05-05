package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.Withdrawal

const val WithdrawFundsQuery = """
    mutation WithdrawFunds(
        ${'$'}node_id: ID!
        ${'$'}bitcoin_address: String!
        ${'$'}amount_sats: Long!
    ) {
        withdraw_funds(input: {
            node_id: ${'$'}node_id
            bitcoin_address: ${'$'}bitcoin_address
            amount_sats: ${'$'}amount_sats
        }) {
            transaction {
                ...WithdrawalFragment
            }
        }
    }

${Withdrawal.FRAGMENT}
"""
