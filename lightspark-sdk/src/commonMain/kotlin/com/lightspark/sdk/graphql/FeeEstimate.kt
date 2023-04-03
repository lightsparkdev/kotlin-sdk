package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.FeeEstimate

const val FeeEstimateQuery = """
  query FeeEstimate(${'$'}bitcoin_network: BitcoinNetwork!) {
    fee_estimate(network: ${'$'}bitcoin_network) {
      ...FeeEstimateFragment
    }
  }

${FeeEstimate.FRAGMENT}
"""
