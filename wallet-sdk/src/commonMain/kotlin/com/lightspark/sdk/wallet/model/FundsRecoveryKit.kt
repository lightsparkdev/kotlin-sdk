// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName

/**
 *
 * @property bitcoinWalletAddress The bitcoin address where the funds should be sent if the recovery kit is used.
 */
interface FundsRecoveryKit {

    @SerialName("funds_recovery_kit_bitcoin_wallet_address")
    val bitcoinWalletAddress: String

    companion object {

        const val FRAGMENT = """
fragment FundsRecoveryKitFragment on FundsRecoveryKit {
    type: __typename
    ... on AmazonS3FundsRecoveryKit {
        type: __typename
        amazon_s3_funds_recovery_kit_bitcoin_wallet_address: bitcoin_wallet_address
        amazon_s3_funds_recovery_kit_s3_bucket_url: s3_bucket_url
    }
}"""
    }
}
