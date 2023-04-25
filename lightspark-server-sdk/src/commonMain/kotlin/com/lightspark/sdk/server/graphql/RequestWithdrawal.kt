package com.lightspark.sdk.server.graphql

import com.lightspark.sdk.server.model.WithdrawalRequest

const val RequestWithdrawalMutation = """
  mutation RequestWithdrawal(
    ${'$'}node_id: String!
    ${'$'}amount_sats: Int!
    ${'$'}bitcoin_address: String!
    ${'$'}withdrawal_mode: WithdrawalMode!
  ) {
    request_withdrawal(input: {
        node_id: ${'$'}node_id
        amount_sats: ${'$'}amount_sats
        bitcoin_address: ${'$'}bitcoin_address
        withdrawal_mode: ${'$'}withdrawal_mode
    }) {
        request {
            ...WithdrawalRequestFragment
        }
    }
  }

  ${WithdrawalRequest.FRAGMENT}
"""
