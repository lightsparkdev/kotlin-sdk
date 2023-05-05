package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.LightningFeeEstimateOutput

const val LightningFeeEstimateForNodeQuery = """
  query LightningFeeEstimateForNode(
    ${'$'}destination_node_public_key: String!
    ${'$'}amount_msats: Long!
  ) {
    lightning_fee_estimate_for_node(input: {
      destination_node_public_key: ${'$'}destination_node_public_key,
      amount_msats: ${'$'}amount_msats
    }) {
      ...LightningFeeEstimateOutputFragment
    }
  }

${LightningFeeEstimateOutput.FRAGMENT}
"""
