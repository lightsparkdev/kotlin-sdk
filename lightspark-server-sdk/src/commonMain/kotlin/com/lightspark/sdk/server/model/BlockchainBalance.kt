// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.server.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This object provides a detailed breakdown of a `LightsparkNode`'s current balance on the Bitcoin Network.
 * @param totalBalance The total wallet balance, including unconfirmed UTXOs.
 * @param confirmedBalance The balance of confirmed UTXOs in the wallet.
 * @param unconfirmedBalance The balance of unconfirmed UTXOs in the wallet.
 * @param lockedBalance The balance that's locked by an on-chain transaction.
 * @param requiredReserve Funds required to be held in reserve for channel bumping.
 * @param availableBalance Funds available for creating channels or withdrawing.
 */
@Serializable
@SerialName("BlockchainBalance")
data class BlockchainBalance(

    @SerialName("blockchain_balance_total_balance")
    val totalBalance: CurrencyAmount? = null,
    @SerialName("blockchain_balance_confirmed_balance")
    val confirmedBalance: CurrencyAmount? = null,
    @SerialName("blockchain_balance_unconfirmed_balance")
    val unconfirmedBalance: CurrencyAmount? = null,
    @SerialName("blockchain_balance_locked_balance")
    val lockedBalance: CurrencyAmount? = null,
    @SerialName("blockchain_balance_required_reserve")
    val requiredReserve: CurrencyAmount? = null,
    @SerialName("blockchain_balance_available_balance")
    val availableBalance: CurrencyAmount? = null,
) {

    companion object {

        const val FRAGMENT = """
fragment BlockchainBalanceFragment on BlockchainBalance {
    type: __typename
    blockchain_balance_total_balance: total_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    blockchain_balance_confirmed_balance: confirmed_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    blockchain_balance_unconfirmed_balance: unconfirmed_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    blockchain_balance_locked_balance: locked_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    blockchain_balance_required_reserve: required_reserve {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    blockchain_balance_available_balance: available_balance {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
}"""
    }
}
