package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.DeployWalletOutput

const val DeployWallet = """
  mutation DeployWallet {
    deploy_wallet {
      ...DeployWalletOutputFragment
    }
  }
  
    ${DeployWalletOutput.FRAGMENT}
"""
