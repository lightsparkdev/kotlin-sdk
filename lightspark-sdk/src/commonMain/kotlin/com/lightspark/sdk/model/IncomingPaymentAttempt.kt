// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * This object represents any attempted payment sent to a Lightspark node on the Lightning Network. You can retrieve this object to receive payment related information about a specific incoming payment attempt.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param status The status of the incoming payment attempt.
 * @param amount The total amount of that was attempted to send.
 * @param channelId The channel this attempt was made on.
 * @param resolvedAt The time the incoming payment attempt failed or succeeded.
 */
@Serializable
@SerialName("IncomingPaymentAttempt")
data class IncomingPaymentAttempt(

    @SerialName("incoming_payment_attempt_id")
    override val id: String,
    @SerialName("incoming_payment_attempt_created_at")
    override val createdAt: Instant,
    @SerialName("incoming_payment_attempt_updated_at")
    override val updatedAt: Instant,
    @SerialName("incoming_payment_attempt_status")
    val status: IncomingPaymentAttemptStatus,
    @SerialName("incoming_payment_attempt_amount")
    val amount: CurrencyAmount,
    @SerialName("incoming_payment_attempt_channel")
    val channelId: EntityId,
    @SerialName("incoming_payment_attempt_resolved_at")
    val resolvedAt: Instant? = null,
) : Entity {

    companion object {
        @JvmStatic
        fun getIncomingPaymentAttemptQuery(id: String): Query<IncomingPaymentAttempt> {
            return Query(
                queryPayload = """
query GetIncomingPaymentAttempt(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on IncomingPaymentAttempt {
            ...IncomingPaymentAttemptFragment
        }
    }
}

$FRAGMENT
""",
                variableBuilder = { add("id", id) },
            ) {
                val entity = requireNotNull(it["entity"]) { "Entity not found" }
                serializerFormat.decodeFromJsonElement(entity)
            }
        }

        const val FRAGMENT = """
fragment IncomingPaymentAttemptFragment on IncomingPaymentAttempt {
    type: __typename
    incoming_payment_attempt_id: id
    incoming_payment_attempt_created_at: created_at
    incoming_payment_attempt_updated_at: updated_at
    incoming_payment_attempt_status: status
    incoming_payment_attempt_resolved_at: resolved_at
    incoming_payment_attempt_amount: amount {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    incoming_payment_attempt_channel: channel {
        id
    }
}"""
    }
}
