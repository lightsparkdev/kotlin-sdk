// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @param entities The withdrawals for the current page of this connection.
 */
@Serializable
@SerialName("WithdrawalRequestToWithdrawalsConnection")
data class WithdrawalRequestToWithdrawalsConnection(
    @SerialName("withdrawal_request_to_withdrawals_connection_count")
    val count: Int,
    @SerialName("withdrawal_request_to_withdrawals_connection_entities")
    val entities: List<Withdrawal>,
) {
    companion object {
        const val FRAGMENT = """
fragment WithdrawalRequestToWithdrawalsConnectionFragment on WithdrawalRequestToWithdrawalsConnection {
    type: __typename
    withdrawal_request_to_withdrawals_connection_count: count
    withdrawal_request_to_withdrawals_connection_entities: entities {
        id
    }
}"""
    }
}
