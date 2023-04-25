package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.InitializeWalletOutput

const val InitializeWallet = """
  query InitializeWallet(${'$'}key_type: KeyType!, ${'$'}signing_public_key: String!) {
    initialize_wallet(input: { key_type: ${'$'}key_type, public_key: ${'$'}signing_public_key }) {
        ...InitializeWalletOutputFragment
    }
  }

${InitializeWalletOutput.FRAGMENT}
"""
