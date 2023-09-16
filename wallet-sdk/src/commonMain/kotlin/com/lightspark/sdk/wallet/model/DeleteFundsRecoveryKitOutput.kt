// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("DeleteFundsRecoveryKitOutput")
data class DeleteFundsRecoveryKitOutput(
    @SerialName("delete_funds_recovery_kit_output_wallet")
    val walletId: EntityId,
) {
    companion object {
        const val FRAGMENT = """
fragment DeleteFundsRecoveryKitOutputFragment on DeleteFundsRecoveryKitOutput {
    type: __typename
    delete_funds_recovery_kit_output_wallet: wallet {
        id
    }
}"""
    }
}
