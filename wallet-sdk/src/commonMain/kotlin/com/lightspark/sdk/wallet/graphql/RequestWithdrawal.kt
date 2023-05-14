package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.WithdrawalRequest

const val RequestWithdrawalMutation = """
  mutation RequestWithdrawal(
    ${'$'}amount_sats: Long!
    ${'$'}bitcoin_address: String!
  ) {
    request_withdrawal(input: {
        amount_sats: ${'$'}amount_sats
        bitcoin_address: ${'$'}bitcoin_address
    }) {
        request {
            ...WithdrawalRequestFragment
        }
    }
  }

  ${WithdrawalRequest.FRAGMENT}
"""
