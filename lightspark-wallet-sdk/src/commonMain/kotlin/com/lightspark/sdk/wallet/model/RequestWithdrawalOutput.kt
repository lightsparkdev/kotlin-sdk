// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param requestId The request that is created for this withdrawal.
 */
@Serializable
@SerialName("RequestWithdrawalOutput")
data class RequestWithdrawalOutput(

    @SerialName("request_withdrawal_output_request")
    val requestId: EntityId,
) {

    companion object {

        const val FRAGMENT = """
fragment RequestWithdrawalOutputFragment on RequestWithdrawalOutput {
    type: __typename
    request_withdrawal_output_request: request {
        id
    }
}"""
    }
}
