// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.util.serializerFormat
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.decodeFromJsonElement

/**
 *
 * @property id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @property createdAt The date and time when the entity was first created.
 * @property updatedAt The date and time when the entity was last updated.
 */
interface LightsparkNodeOwner : Entity {

    @SerialName("lightspark_node_owner_id")
    override val id: String

    @SerialName("lightspark_node_owner_created_at")
    override val createdAt: Instant

    @SerialName("lightspark_node_owner_updated_at")
    override val updatedAt: Instant

    companion object {
        @JvmStatic
        fun getLightsparkNodeOwnerQuery(id: String): Query<LightsparkNodeOwner> {
            return Query(
                queryPayload = """
query GetLightsparkNodeOwner(${'$'}id: ID!) {
    entity(id: ${'$'}id) {
        ... on LightsparkNodeOwner {
            ...LightsparkNodeOwnerFragment
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
fragment LightsparkNodeOwnerFragment on LightsparkNodeOwner {
    type: __typename
    ... on Account {
        type: __typename
        account_id: id
        account_created_at: created_at
        account_updated_at: updated_at
        account_name: name
    }
    ... on Wallet {
        type: __typename
        wallet_id: id
        wallet_created_at: created_at
        wallet_updated_at: updated_at
        wallet_last_login_at: last_login_at
        wallet_balances: balances {
            type: __typename
            balances_owned_balance: owned_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            balances_available_to_send_balance: available_to_send_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
            balances_available_to_withdraw_balance: available_to_withdraw_balance {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
        }
        wallet_third_party_identifier: third_party_identifier
        wallet_status: status
    }
}"""
    }
}
