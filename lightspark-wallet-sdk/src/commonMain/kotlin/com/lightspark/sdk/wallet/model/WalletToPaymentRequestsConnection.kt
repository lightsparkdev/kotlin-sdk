// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param pageInfo An object that holds pagination information about the objects in this connection.
 * @param count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @param entities The payment requests for the current page of this connection.
 */
@Serializable
@SerialName("WalletToPaymentRequestsConnection")
data class WalletToPaymentRequestsConnection(

    @SerialName("wallet_to_payment_requests_connection_page_info")
    val pageInfo: PageInfo,
    @SerialName("wallet_to_payment_requests_connection_count")
    val count: Int,
    @SerialName("wallet_to_payment_requests_connection_entities")
    val entities: List<PaymentRequest>,
) {

    companion object {

        const val FRAGMENT = """
fragment WalletToPaymentRequestsConnectionFragment on WalletToPaymentRequestsConnection {
    type: __typename
    wallet_to_payment_requests_connection_page_info: page_info {
        type: __typename
        page_info_has_next_page: has_next_page
        page_info_has_previous_page: has_previous_page
        page_info_start_cursor: start_cursor
        page_info_end_cursor: end_cursor
    }
    wallet_to_payment_requests_connection_count: count
    wallet_to_payment_requests_connection_entities: entities {
        id
    }
}"""
    }
}