// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.server.util.serializerFormat
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.jvm.JvmStatic

/**
 * One hop signifies a payment moving one node ahead on a payment route; a list of sequential hops defines the path from sender node to recipient node for a payment attempt.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param index The zero-based index position of this hop in the path
 * @param destinationId The destination node of the hop.
 * @param publicKey The public key of the node to which the hop is bound.
 * @param amountToForward The amount that is to be forwarded to the destination node.
 * @param fee The fees to be collected by the source node for forwarding the payment over the hop.
 * @param expiryBlockHeight The block height at which an unsettled HTLC is considered expired.
 */
@Serializable
@SerialName("Hop")
data class Hop(

    @SerialName("hop_id")
    override val id: String,
    @SerialName("hop_created_at")
    override val createdAt: Instant,
    @SerialName("hop_updated_at")
    override val updatedAt: Instant,
    @SerialName("hop_index")
    val index: Int,
    @SerialName("hop_destination")
    val destinationId: EntityId? = null,
    @SerialName("hop_public_key")
    val publicKey: String? = null,
    @SerialName("hop_amount_to_forward")
    val amountToForward: CurrencyAmount? = null,
    @SerialName("hop_fee")
    val fee: CurrencyAmount? = null,
    @SerialName("hop_expiry_block_height")
    val expiryBlockHeight: Int? = null,
) : Entity {

    companion object {
        @JvmStatic
        fun getHopQuery(id: String): Query<Hop> {
            return Query(
                queryPayload = """
query GetHop(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on Hop {
            ...HopFragment
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
fragment HopFragment on Hop {
    type: __typename
    hop_id: id
    hop_created_at: created_at
    hop_updated_at: updated_at
    hop_destination: destination {
        id
    }
    hop_index: index
    hop_public_key: public_key
    hop_amount_to_forward: amount_to_forward {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    hop_fee: fee {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    hop_expiry_block_height: expiry_block_height
}"""
    }
}
