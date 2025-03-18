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
 * This object represents a BOLT #12 offer (https://github.com/lightning/bolts/blob/master/12-offer-encoding.md) created by a Lightspark Node.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param dataId The details of the offer.
 * @param encodedOffer The BOLT12 encoded offer. Starts with 'lno'.
 * @param amount The amount of the offer. If null, the payer chooses the amount.
 * @param description The description of the offer.
 */
@Serializable
@SerialName("Offer")
data class Offer(
    @SerialName("offer_id")
    override val id: String,
    @SerialName("offer_created_at")
    override val createdAt: Instant,
    @SerialName("offer_updated_at")
    override val updatedAt: Instant,
    @SerialName("offer_data")
    val dataId: EntityId,
    @SerialName("offer_encoded_offer")
    val encodedOffer: String,
    @SerialName("offer_amount")
    val amount: CurrencyAmount? = null,
    @SerialName("offer_description")
    val description: String? = null,
) : Entity {
    companion object {
        @JvmStatic
        fun getOfferQuery(id: String): Query<Offer> = Query(
            queryPayload = """
query GetOffer(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on Offer {
            ...OfferFragment
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

        const val FRAGMENT = """
fragment OfferFragment on Offer {
    type: __typename
    offer_id: id
    offer_created_at: created_at
    offer_updated_at: updated_at
    offer_data: data {
        id
    }
    offer_encoded_offer: encoded_offer
    offer_amount: amount {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    offer_description: description
}"""
    }
}
