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
 * This object represents the data associated with a BOLT #12 offer. You can retrieve this object to receive the relevant data associated with a specific offer.
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param encodedOffer The Bech32 encoded offer.
 * @param bitcoinNetworks The bitcoin networks supported by the offer.
 * @param amount The requested amount in this invoice. If it is equal to 0, the sender should choose the amount to send.
 * @param expiresAt The date and time when this invoice will expire.
 */
@Serializable
@SerialName("OfferData")
data class OfferData(
    @SerialName("offer_data_id")
    override val id: String,
    @SerialName("offer_data_created_at")
    override val createdAt: Instant,
    @SerialName("offer_data_updated_at")
    override val updatedAt: Instant,
    @SerialName("offer_data_encoded_offer")
    val encodedOffer: String,
    @SerialName("offer_data_bitcoin_networks")
    val bitcoinNetworks: List<BitcoinNetwork>,
    @SerialName("offer_data_amount")
    val amount: CurrencyAmount? = null,
    @SerialName("offer_data_expires_at")
    val expiresAt: Instant? = null,
) : Entity {
    companion object {
        @JvmStatic
        fun getOfferDataQuery(id: String): Query<OfferData> = Query(
            queryPayload = """
query GetOfferData(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on OfferData {
            ...OfferDataFragment
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
fragment OfferDataFragment on OfferData {
    type: __typename
    offer_data_id: id
    offer_data_created_at: created_at
    offer_data_updated_at: updated_at
    offer_data_amount: amount {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    offer_data_encoded_offer: encoded_offer
    offer_data_bitcoin_networks: bitcoin_networks
    offer_data_expires_at: expires_at
}"""
    }
}
