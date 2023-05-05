package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.LightningFeeEstimateOutput

const val LightningFeeEstimateForNodeQuery = """
  query LightningFeeEstimateForNode(
    ${'$'}node_id: ID!
    ${'$'}destination_node_public_key: String!
    ${'$'}amount_msats: Long!
  ) {
    lightning_fee_estimate_for_node(input: {
      node_id: ${'$'}node_id,
      destination_node_public_key: ${'$'}destination_node_public_key,
      amount_msats: ${'$'}amount_msats
    }) {
      ...LightningFeeEstimateOutputFragment
    }
  }

${LightningFeeEstimateOutput.FRAGMENT}
"""
