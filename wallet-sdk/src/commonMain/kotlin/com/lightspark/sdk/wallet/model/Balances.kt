// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @param ownedBalance This represents the balance that should be displayed when asked "how much do I own right now?".

It represents the amount currently owned, including things that may not be owned soon (e.g. in-flight outgoing payments, in-flight withdrawals, commit fees, etc.). It really is a snapshot of what is officially owned at this instant.
 * @param availableToSendBalance This represents the balance that should be displayed when asked "how much can I send on Lightning right now?".

It represents the amount currently available to be sent on the Lightning network. We remove from the balance all the funds that are temporarily locked (e.g. channel reserves).
 * @param availableToWithdrawBalance This represents the balance that should be displayed when asked "how much money can I withdraw on the Bitcoin network right now?".

It represents the amount currently available to withdraw and is usually equal to the `owned_balance` but it does not include in-flight operations (which would likely succeed and therefore likely make your withdrawal fail).
 */
@Serializable
@SerialName("Balances")
data class Balances(

    @SerialName("balances_owned_balance")
    val ownedBalance: CurrencyAmount,
    @SerialName("balances_available_to_send_balance")
    val availableToSendBalance: CurrencyAmount,
    @SerialName("balances_available_to_withdraw_balance")
    val availableToWithdrawBalance: CurrencyAmount,
) {

    companion object {

        const val FRAGMENT = """
fragment BalancesFragment on Balances {
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
}"""
    }
}