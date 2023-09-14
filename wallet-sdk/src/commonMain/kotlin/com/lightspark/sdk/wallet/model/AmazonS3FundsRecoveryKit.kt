// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param bitcoinWalletAddress The bitcoin address where the funds should be sent if the recovery kit is used.
 * @param s3BucketUrl The URL of the Amazon S3 bucket URL where we should upload the funds recovery kit.
 */
@Serializable
@SerialName("AmazonS3FundsRecoveryKit")
data class AmazonS3FundsRecoveryKit(
    @SerialName("amazon_s3_funds_recovery_kit_bitcoin_wallet_address")
    override val bitcoinWalletAddress: String,
    @SerialName("amazon_s3_funds_recovery_kit_s3_bucket_url")
    val s3BucketUrl: String,
) : FundsRecoveryKit {
    companion object {
        const val FRAGMENT = """
fragment AmazonS3FundsRecoveryKitFragment on AmazonS3FundsRecoveryKit {
    type: __typename
    amazon_s3_funds_recovery_kit_bitcoin_wallet_address: bitcoin_wallet_address
    amazon_s3_funds_recovery_kit_s3_bucket_url: s3_bucket_url
}"""
    }
}
