package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.TerminateWalletOutput

const val TerminateWallet = """
  mutation TerminateWallet() {
    terminate_wallet {
      ...TerminateWalletOutputFragment
    }
  }
  
    ${TerminateWalletOutput.FRAGMENT}
"""
