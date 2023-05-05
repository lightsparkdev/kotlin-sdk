package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.Wallet

const val CurrentWalletQuery = """
query CurrentWallet {
    current_wallet {
        ...WalletFragment
    }
}

${Wallet.FRAGMENT}
"""
