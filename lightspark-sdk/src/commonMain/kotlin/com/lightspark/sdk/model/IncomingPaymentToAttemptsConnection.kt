// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The connection from incoming payment to all attempts.
 * @param count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @param entities The incoming payment attempts for the current page of this connection.
 */
@Serializable
@SerialName("IncomingPaymentToAttemptsConnection")
data class IncomingPaymentToAttemptsConnection(

    @SerialName("incoming_payment_to_attempts_connection_count")
    val count: Int,
    @SerialName("incoming_payment_to_attempts_connection_entities")
    val entities: List<IncomingPaymentAttempt>,
) {

    companion object {

        const val FRAGMENT = """
fragment IncomingPaymentToAttemptsConnectionFragment on IncomingPaymentToAttemptsConnection {
    type: __typename
    incoming_payment_to_attempts_connection_count: count
    incoming_payment_to_attempts_connection_entities: entities {
        id
    }
}"""
    }
}
