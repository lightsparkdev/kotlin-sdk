package com.lightspark.sdk.wallet.graphql

import com.lightspark.sdk.wallet.model.*
import com.lightspark.sdk.wallet.model.CurrencyAmount
import com.lightspark.sdk.wallet.model.WalletStatus
import com.lightspark.sdk.wallet.model.WalletToTransactionsConnection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletDashboard(
    val id: String,
    val status: WalletStatus,
    val balances: Balances?,
    @SerialName("recent_transactions")
    val recentTransactions: WalletToTransactionsConnection,
    @SerialName("payment_requests")
    val paymentRequests: WalletToPaymentRequestsConnection,
)

const val WalletDashboardQuery = """
query WalletDashboard(
    ${'$'}numTransactions: Int,
    ${'$'}numPaymentRequests: Int,
    ${'$'}transactionsAfterDate: DateTime,
    ${'$'}paymentRequestsAfterDate: DateTime,
    ${'$'}transactionTypes: [TransactionType!] = [PAYMENT, PAYMENT_REQUEST, ROUTE, L1_WITHDRAW, L1_DEPOSIT]
    ${'$'}transactionStatuses: [TransactionStatus!] = null
) {
    current_wallet {
        id
        balances {
            ...BalancesFragment
        }
        status
        recent_transactions: transactions(
            first: ${'$'}numTransactions
            types: ${'$'}transactionTypes
            statuses: ${'$'}transactionStatuses
            created_after_date: ${'$'}transactionsAfterDate
        ) {
            wallet_to_transactions_connection_count: count
            wallet_to_transactions_connection_entities: entities {
                ...TransactionFragment
            }
            wallet_to_transactions_connection_page_info: page_info {
                page_info_has_next_page: has_next_page
                page_info_has_previous_page: has_previous_page
                page_info_start_cursor: start_cursor
                page_info_end_cursor: end_cursor
            }
            type: __typename
        }
        payment_requests(
            first: ${'$'}numPaymentRequests
            created_after_date: ${'$'}paymentRequestsAfterDate
        ) {
            wallet_to_payment_requests_connection_count: count
            wallet_to_payment_requests_connection_entities: entities {
                ...PaymentRequestFragment
            }
            wallet_to_payment_requests_connection_page_info: page_info {
                page_info_has_next_page: has_next_page
                page_info_has_previous_page: has_previous_page
                page_info_start_cursor: start_cursor
                page_info_end_cursor: end_cursor
            }
        }
    }
}

${Transaction.FRAGMENT}
${Balances.FRAGMENT}
${PaymentRequest.FRAGMENT}
"""
