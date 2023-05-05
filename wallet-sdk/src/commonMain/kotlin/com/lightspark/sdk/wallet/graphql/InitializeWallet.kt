package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.InitializeWalletOutput

const val InitializeWallet = """
  mutation InitializeWallet(${'$'}key_type: KeyType!, ${'$'}signing_public_key: String!) {
    initialize_wallet(input: {
        signing_public_key: { type: ${'$'}key_type, public_key: ${'$'}signing_public_key }
    }) {
        ...InitializeWalletOutputFragment
    }
  }

${InitializeWalletOutput.FRAGMENT}
"""
