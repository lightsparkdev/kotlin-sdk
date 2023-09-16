// Copyright ©, 2023-present, Lightspark Group, Inc. - All Rights Reserved
@file:Suppress("ktlint:standard:max-line-length")

package com.lightspark.sdk.model

import kotlinx.serialization.SerialName

/**
 *
 * @property count The total count of objects in this connection, using the current filters. It is different from the number of objects returned in the current page (in the `entities` field).
 * @property pageInfo An object that holds pagination information about the objects in this connection.
 */
interface Connection {
    @SerialName("connection_count")
    val count: Int

    @SerialName("connection_page_info")
    val pageInfo: PageInfo

    companion object {
        const val FRAGMENT = """
fragment ConnectionFragment on Connection {
    type: __typename
    ... on AccountToApiTokensConnection {
        type: __typename
        account_to_api_tokens_connection_count: count
        account_to_api_tokens_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        account_to_api_tokens_connection_entities: entities {
            id
        }
    }
    ... on AccountToNodesConnection {
        type: __typename
        account_to_nodes_connection_count: count
        account_to_nodes_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        account_to_nodes_connection_entities: entities {
            id
        }
    }
    ... on AccountToPaymentRequestsConnection {
        type: __typename
        account_to_payment_requests_connection_count: count
        account_to_payment_requests_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        account_to_payment_requests_connection_entities: entities {
            id
        }
    }
    ... on AccountToTransactionsConnection {
        type: __typename
        account_to_transactions_connection_count: count
        account_to_transactions_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        account_to_transactions_connection_profit_loss: profit_loss {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        account_to_transactions_connection_average_fee_earned: average_fee_earned {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        account_to_transactions_connection_total_amount_transacted: total_amount_transacted {
            type: __typename
            currency_amount_original_value: original_value
            currency_amount_original_unit: original_unit
            currency_amount_preferred_currency_unit: preferred_currency_unit
            currency_amount_preferred_currency_value_rounded: preferred_currency_value_rounded
            currency_amount_preferred_currency_value_approx: preferred_currency_value_approx
        }
        account_to_transactions_connection_entities: entities {
            id
        }
    }
    ... on AccountToWalletsConnection {
        type: __typename
        account_to_wallets_connection_count: count
        account_to_wallets_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        account_to_wallets_connection_entities: entities {
            id
        }
    }
    ... on IncomingPaymentToAttemptsConnection {
        type: __typename
        incoming_payment_to_attempts_connection_count: count
        incoming_payment_to_attempts_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        incoming_payment_to_attempts_connection_entities: entities {
            id
        }
    }
    ... on LightsparkNodeToChannelsConnection {
        type: __typename
        lightspark_node_to_channels_connection_count: count
        lightspark_node_to_channels_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        lightspark_node_to_channels_connection_entities: entities {
            id
        }
    }
    ... on OutgoingPaymentAttemptToHopsConnection {
        type: __typename
        outgoing_payment_attempt_to_hops_connection_count: count
        outgoing_payment_attempt_to_hops_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        outgoing_payment_attempt_to_hops_connection_entities: entities {
            id
        }
    }
    ... on OutgoingPaymentToAttemptsConnection {
        type: __typename
        outgoing_payment_to_attempts_connection_count: count
        outgoing_payment_to_attempts_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        outgoing_payment_to_attempts_connection_entities: entities {
            id
        }
    }
    ... on WalletToPaymentRequestsConnection {
        type: __typename
        wallet_to_payment_requests_connection_count: count
        wallet_to_payment_requests_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        wallet_to_payment_requests_connection_entities: entities {
            id
        }
    }
    ... on WalletToTransactionsConnection {
        type: __typename
        wallet_to_transactions_connection_count: count
        wallet_to_transactions_connection_page_info: page_info {
            type: __typename
            page_info_has_next_page: has_next_page
            page_info_has_previous_page: has_previous_page
            page_info_start_cursor: start_cursor
            page_info_end_cursor: end_cursor
        }
        wallet_to_transactions_connection_entities: entities {
            id
        }
    }
}"""
    }
}
