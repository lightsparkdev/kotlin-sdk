// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("UpdateFundsRecoveryKitOutput")
data class UpdateFundsRecoveryKitOutput(
    @SerialName("update_funds_recovery_kit_output_wallet")
    val walletId: EntityId,
    @SerialName("update_funds_recovery_kit_output_funds_recovery_kit")
    val fundsRecoveryKit: FundsRecoveryKit,
) {
    companion object {
        const val FRAGMENT = """
fragment UpdateFundsRecoveryKitOutputFragment on UpdateFundsRecoveryKitOutput {
    type: __typename
    update_funds_recovery_kit_output_wallet: wallet {
        id
    }
    update_funds_recovery_kit_output_funds_recovery_kit: funds_recovery_kit {
        type: __typename
        ... on AmazonS3FundsRecoveryKit {
            type: __typename
            amazon_s3_funds_recovery_kit_bitcoin_wallet_address: bitcoin_wallet_address
            amazon_s3_funds_recovery_kit_s3_bucket_url: s3_bucket_url
        }
    }
}"""
    }
}
