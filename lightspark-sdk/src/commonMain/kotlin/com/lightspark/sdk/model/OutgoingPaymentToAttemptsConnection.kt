// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The connection from outgoing payment to all attempts.
 * @param count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @param pageInfo An object that holds pagination information about the objects in this connection.
 * @param entities The attempts for the current page of this connection.
 */
@Serializable
@SerialName("OutgoingPaymentToAttemptsConnection")
data class OutgoingPaymentToAttemptsConnection(

    @SerialName("outgoing_payment_to_attempts_connection_count")
    override val count: Int,
    @SerialName("outgoing_payment_to_attempts_connection_page_info")
    override val pageInfo: PageInfo,
    @SerialName("outgoing_payment_to_attempts_connection_entities")
    val entities: List<OutgoingPaymentAttempt>,
) : Connection {

    companion object {

        const val FRAGMENT = """
fragment OutgoingPaymentToAttemptsConnectionFragment on OutgoingPaymentToAttemptsConnection {
    type: __typename
    outgoing_payment_to_attempts_connection_count: count
    outgoing_payment_to_attempts_connection_page_info: page_info {
        type: __typename
        page_info_has_next_page: has_next_page
        page_info_has_previous_page: has_previous_page
        page_info_start_cursor: start_cursor
        page_info_end_cursor: end_cursor
    }
    outgoing_payment_to_attempts_connection_entities: entities {
        id
    }
}"""
    }
}
