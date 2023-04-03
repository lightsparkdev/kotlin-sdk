package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.WithdrawalRequest

const val RequestWithdrawalMutation = """
  mutation RequestWithdrawal(
    ${'$'}amount_sats: Int!
    ${'$'}bitcoin_address: String!
    ${'$'}withdrawal_mode: WithdrawalMode!
  ) {
    request_withdrawal(input: {
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
