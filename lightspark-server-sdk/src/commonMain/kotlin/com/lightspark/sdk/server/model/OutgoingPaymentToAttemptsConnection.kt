// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The connection from outgoing payment to all attempts.
 * @param count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @param entities The attempts for the current page of this connection.
 */
@Serializable
@SerialName("OutgoingPaymentToAttemptsConnection")
data class OutgoingPaymentToAttemptsConnection(

    @SerialName("outgoing_payment_to_attempts_connection_count")
    val count: Int,
    @SerialName("outgoing_payment_to_attempts_connection_entities")
    val entities: List<OutgoingPaymentAttempt>,
) {

    companion object {

        const val FRAGMENT = """
fragment OutgoingPaymentToAttemptsConnectionFragment on OutgoingPaymentToAttemptsConnection {
    type: __typename
    outgoing_payment_to_attempts_connection_count: count
    outgoing_payment_to_attempts_connection_entities: entities {
        id
    }
}"""
    }
}
