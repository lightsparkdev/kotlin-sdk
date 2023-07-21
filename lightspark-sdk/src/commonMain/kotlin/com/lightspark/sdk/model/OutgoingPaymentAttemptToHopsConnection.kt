// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The connection from an outgoing payment attempt to the list of sequential hops that define the path from sender node to recipient node.
 * @param count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @param entities The hops for the current page of this connection.
 */
@Serializable
@SerialName("OutgoingPaymentAttemptToHopsConnection")
data class OutgoingPaymentAttemptToHopsConnection(

    @SerialName("outgoing_payment_attempt_to_hops_connection_count")
    val count: Int,
    @SerialName("outgoing_payment_attempt_to_hops_connection_entities")
    val entities: List<Hop>,
) {

    companion object {

        const val FRAGMENT = """
fragment OutgoingPaymentAttemptToHopsConnectionFragment on OutgoingPaymentAttemptToHopsConnection {
    type: __typename
    outgoing_payment_attempt_to_hops_connection_count: count
    outgoing_payment_attempt_to_hops_connection_entities: entities {
        id
    }
}"""
    }
}
