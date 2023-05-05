package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.CurrencyAmount

const val FundNodeMutation = """
    mutation FundNode(
        ${'$'}node_id: ID!,
        ${'$'}amount_sats: Long
    ) {
        fund_node(input: { node_id: ${'$'}node_id, amount_sats: ${'$'}amount_sats }) {
            amount {
                ...CurrencyAmountFragment
            }
        }
    }

${CurrencyAmount.FRAGMENT}
"""
