package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.WithdrawalRequest

const val RequestWithdrawalMutation = """
  mutation RequestWithdrawal(
    ${'$'}node_id: ID!
    ${'$'}amount_sats: Long!
    ${'$'}bitcoin_address: String!
    ${'$'}withdrawal_mode: WithdrawalMode!
    ${'$'}idempotency_key: String
  ) {
    request_withdrawal(input: {
        node_id: ${'$'}node_id
        amount_sats: ${'$'}amount_sats
        bitcoin_address: ${'$'}bitcoin_address
        withdrawal_mode: ${'$'}withdrawal_mode
        idempotency_key: ${'$'}idempotency_key
    }) {
        request {
            ...WithdrawalRequestFragment
        }
    }
  }

  ${WithdrawalRequest.FRAGMENT}
"""
