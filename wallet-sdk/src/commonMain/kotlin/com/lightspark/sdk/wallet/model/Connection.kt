// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName

/**
 *
 * @property count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @property pageInfo An object that holds pagination information about the objects in this connection.
 */
interface Connection {
    @SerialName("connection_count")
    val count: Int

    @SerialName("connection_page_info")
    val pageInfo: PageInfo

    companion object {
        const val FRAGMENT = """
fragment ConnectionFragment on Connection {
    type: __typename
    ... on WalletToPaymentRequestsConnection {
        type: __typename
        wallet_to_payment_requests_connection_count: count
        wallet_to_payment_requests_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        wallet_to_payment_requests_connection_entities: entities {
            id
        }
    }
    ... on WalletToTransactionsConnection {
        type: __typename
        wallet_to_transactions_connection_count: count
        wallet_to_transactions_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        wallet_to_transactions_connection_entities: entities {
            id
        }
    }
    ... on WalletToWithdrawalRequestsConnection {
        type: __typename
        wallet_to_withdrawal_requests_connection_count: count
        wallet_to_withdrawal_requests_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        wallet_to_withdrawal_requests_connection_entities: entities {
            id
        }
    }
}"""
    }
}
