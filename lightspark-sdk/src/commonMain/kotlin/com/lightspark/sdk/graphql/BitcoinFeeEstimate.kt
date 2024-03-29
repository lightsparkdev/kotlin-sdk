package com.lightspark.sdk.graphql

import com.lightspark.sdk.model.FeeEstimate

const val BitcoinFeeEstimateQuery = """
  query BitcoinFeeEstimate(${'$'}bitcoin_network: BitcoinNetwork!) {
    bitcoin_fee_estimate(network: ${'$'}bitcoin_network) {
      ...FeeEstimateFragment
    }
  }

${FeeEstimate.FRAGMENT}
"""
