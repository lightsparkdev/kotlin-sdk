package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.FeeEstimate

const val BitcoinFeeEstimateQuery = """
  query BitcoinFeeEstimate {
    bitcoin_fee_estimate {
      ...FeeEstimateFragment
    }
  }

${FeeEstimate.FRAGMENT}
"""
