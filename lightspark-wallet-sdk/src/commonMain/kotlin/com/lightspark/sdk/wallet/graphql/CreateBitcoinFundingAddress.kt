package com.lightspark.sdk.wallet.graphql

const val CreateBitcoinFundingAddress = """
  mutation CreateBitcoinFundingAddress() {
    create_bitcoin_funding_address {
        bitcoin_address
    }
  }
"""
