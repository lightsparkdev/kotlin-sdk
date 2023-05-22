// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.model

import com.lightspark.sdk.core.requester.Query
import com.lightspark.sdk.util.serializerFormat
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

/**
 *
 * @param id The unique identifier of this entity across all Lightspark systems. Should be treated as an opaque string.
 * @param createdAt The date and time when the entity was first created.
 * @param updatedAt The date and time when the entity was last updated.
 * @param thirdPartyIdentifier The unique identifier of this wallet, as provided by the Lightspark Customer during login.
 * @param lastLoginAt The date and time when the wallet user last logged in.
 * @param balances The balances that describe the funds in this wallet.
 */
@Serializable
@SerialName("Wallet")
data class Wallet(

    @SerialName("wallet_id")
    override val id: String,
    @SerialName("wallet_created_at")
    override val createdAt: Instant,
    @SerialName("wallet_updated_at")
    override val updatedAt: Instant,
    @SerialName("wallet_third_party_identifier")
    val thirdPartyIdentifier: String,
    @SerialName("wallet_last_login_at")
    val lastLoginAt: Instant? = null,
    @SerialName("wallet_balances")
    val balances: Balances? = null,
) : Entity {
    @JvmOverloads
    fun getTotalAmountReceivedQuery(createdAfterDate: Instant? = null, createdBeforeDate: Instant? = null): Query<CurrencyAmount> {
        return Query(
            queryPayload = """
query FetchWalletTotalAmountReceived(${'$'}created_after_date: DateTime, ${'$'}created_before_date: DateTime) {
    current_wallet {
        ... on Wallet {
            total_amount_received(, created_after_date: ${'$'}created_after_date, created_before_date: ${'$'}created_before_date) {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
        }
    }
}
""",
            variableBuilder = {
                add("created_after_date", createdAfterDate)
                add("created_before_date", createdBeforeDate)
            }
        ) {
            val connection =
                requireNotNull(it["current_wallet"]?.jsonObject?.get("total_amount_received")) { "total_amount_received not found" }
            return@Query serializerFormat.decodeFromJsonElement<CurrencyAmount>(connection)
        }
    }

    @JvmOverloads
    fun getTotalAmountSentQuery(createdAfterDate: Instant? = null, createdBeforeDate: Instant? = null): Query<CurrencyAmount> {
        return Query(
            queryPayload = """
query FetchWalletTotalAmountSent(${'$'}created_after_date: DateTime, ${'$'}created_before_date: DateTime) {
    current_wallet {
        ... on Wallet {
            total_amount_sent(, created_after_date: ${'$'}created_after_date, created_before_date: ${'$'}created_before_date) {
                type: __typename
                currency_amount_original_value: original_value
                currency_amount_original_unit: original_unit
                currency_amount_preferred_currency_unit: preferred_currency_unit
                currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
                currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
            }
        }
    }
}
""",
            variableBuilder = {
                add("created_after_date", createdAfterDate)
                add("created_before_date", createdBeforeDate)
            }
        ) {
            val connection =
                requireNotNull(it["current_wallet"]?.jsonObject?.get("total_amount_sent")) { "total_amount_sent not found" }
            return@Query serializerFormat.decodeFromJsonElement<CurrencyAmount>(connection)
        }
    }

    companion object {
        @JvmStatic
        fun getWalletQuery(): Query<Wallet> {
            return Query(
                queryPayload = """
query GetWallet {
    current_wallet {
        ... on Wallet {
            ...WalletFragment
        }
    }
}

$FRAGMENT
""",
                variableBuilder = { },
            ) {
                val entity = requireNotNull(it["current_wallet"]) { "Entity not found" }
                serializerFormat.decodeFromJsonElement(entity)
            }
        }

        const val FRAGMENT = """
fragment WalletFragment on Wallet {
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
}"""
    }
}
