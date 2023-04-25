// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param accountingBalanceL1 TODO write a good description
 * @param accountingBalanceL2 TODO write a good description
 * @param availableBalanceL1 TODO write a good description
 * @param availableBalanceL2 TODO write a good description
 * @param settledBalanceL1 TODO write a good description
 * @param settledBalanceL2 TODO write a good description
 */
@Serializable
@SerialName("Balances")
data class Balances(

    @SerialName("balances_accounting_balance_l1")
    val accountingBalanceL1: CurrencyAmount,
    @SerialName("balances_accounting_balance_l2")
    val accountingBalanceL2: CurrencyAmount,
    @SerialName("balances_available_balance_l1")
    val availableBalanceL1: CurrencyAmount,
    @SerialName("balances_available_balance_l2")
    val availableBalanceL2: CurrencyAmount,
    @SerialName("balances_settled_balance_l1")
    val settledBalanceL1: CurrencyAmount,
    @SerialName("balances_settled_balance_l2")
    val settledBalanceL2: CurrencyAmount,
) {

    companion object {

        const val FRAGMENT = """
fragment BalancesFragment on Balances {
    type: __typename
    balances_accounting_balance_l1: accounting_balance_l1 {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    balances_accounting_balance_l2: accounting_balance_l2 {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    balances_available_balance_l1: available_balance_l1 {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    balances_available_balance_l2: available_balance_l2 {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    balances_settled_balance_l1: settled_balance_l1 {
        type: __typename
        currency_amount_original_value: original_value
        currency_amount_original_unit: original_unit
        currency_amount_preferred_currency_unit: preferred_currency_unit
        currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
        currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
    }
    balances_settled_balance_l2: settled_balance_l2 {
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
