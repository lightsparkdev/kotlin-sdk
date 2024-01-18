package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.WithdrawalFeeEstimateOutput

const val WithdrawalFeeEstimateQuery = """
  query WithdrawalFeeEstimate(
    ${'$'}node_id: ID!
    ${'$'}amount_sats: Long!
    ${'$'}withdrawal_mode: WithdrawalMode!
  ) {
    withdrawal_fee_estimate(input: {
      node_id: ${'$'}node_id,
      amount_sats: ${'$'}amount_sats,
      withdrawal_mode: ${'$'}withdrawal_mode
    }) {
      ...WithdrawalFeeEstimateOutputFragment
    }
  }

${WithdrawalFeeEstimateOutput.FRAGMENT}
"""
