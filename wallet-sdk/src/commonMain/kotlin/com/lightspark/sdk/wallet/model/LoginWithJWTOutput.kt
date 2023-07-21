// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.wallet.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
@SerialName("LoginWithJWTOutput")
data class LoginWithJWTOutput(

    @SerialName("login_with_j_w_t_output_access_token")
    val accessToken: String,
    @SerialName("login_with_j_w_t_output_wallet")
    val wallet: Wallet,
    @SerialName("login_with_j_w_t_output_valid_until")
    val validUntil: Instant,
) {

    companion object {

        const val FRAGMENT = """
fragment LoginWithJWTOutputFragment on LoginWithJWTOutput {
    type: __typename
    login_with_j_w_t_output_access_token: access_token
    login_with_j_w_t_output_wallet: wallet {
        type: __typename
        wallet_id: id
        wallet_created_at: created_at
        wallet_updated_at: updated_at
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
        wallet_status: status
    }
    login_with_j_w_t_output_valid_until: valid_until
}"""
    }
}
