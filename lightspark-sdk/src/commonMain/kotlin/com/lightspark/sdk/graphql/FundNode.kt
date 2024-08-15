package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.CurrencyAmount

const val FundNodeMutation = """
    mutation FundNode(
        ${'$'}node_id: ID!,
        ${'$'}amount_sats: Long
        ${'$'}funding_address: String
    ) {
        fund_node(input: { 
            node_id: ${'$'}node_id, 
            amount_sats: ${'$'}amount_sats,
            funding_address: ${'$'}funding_address
        }) {
            amount {
                ...CurrencyAmountFragment
            }
        }
    }

${CurrencyAmount.FRAGMENT}
"""
