package com.lightspark.sdk.wallet.util

import com.lightspark.sdk.wallet.model.AmazonS3FundsRecoveryKit
import com.lightspark.sdk.wallet.model.ChannelClosingTransaction
import com.lightspark.sdk.wallet.model.ChannelOpeningTransaction
import com.lightspark.sdk.wallet.model.Connection
import com.lightspark.sdk.wallet.model.Deposit
import com.lightspark.sdk.wallet.model.Entity
import com.lightspark.sdk.wallet.model.FundsRecoveryKit
import com.lightspark.sdk.wallet.model.GraphNode
import com.lightspark.sdk.wallet.model.IncomingPayment
import com.lightspark.sdk.wallet.model.Invoice
import com.lightspark.sdk.wallet.model.InvoiceData
import com.lightspark.sdk.wallet.model.LightningTransaction
import com.lightspark.sdk.wallet.model.Node
import com.lightspark.sdk.wallet.model.OnChainTransaction
import com.lightspark.sdk.wallet.model.OutgoingPayment
import com.lightspark.sdk.wallet.model.PaymentRequest
import com.lightspark.sdk.wallet.model.PaymentRequestData
import com.lightspark.sdk.wallet.model.Transaction
import com.lightspark.sdk.wallet.model.Wallet
import com.lightspark.sdk.wallet.model.WalletToPaymentRequestsConnection
import com.lightspark.sdk.wallet.model.WalletToTransactionsConnection
import com.lightspark.sdk.wallet.model.WalletToWithdrawalRequestsConnection
import com.lightspark.sdk.wallet.model.Withdrawal
import com.lightspark.sdk.wallet.model.WithdrawalRequest
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val serializerModule =
    SerializersModule {
        polymorphic(Connection::class) {
            subclass(WalletToPaymentRequestsConnection::class)
            subclass(WalletToTransactionsConnection::class)
            subclass(WalletToWithdrawalRequestsConnection::class)
        }
        polymorphic(Entity::class) {
            subclass(ChannelClosingTransaction::class)
            subclass(ChannelOpeningTransaction::class)
            subclass(Deposit::class)
            subclass(GraphNode::class)
            subclass(IncomingPayment::class)
            subclass(Invoice::class)
            subclass(OutgoingPayment::class)
            subclass(Wallet::class)
            subclass(Withdrawal::class)
            subclass(WithdrawalRequest::class)
        }
        polymorphic(FundsRecoveryKit::class) {
            subclass(AmazonS3FundsRecoveryKit::class)
        }
        polymorphic(LightningTransaction::class) {
            subclass(IncomingPayment::class)
            subclass(OutgoingPayment::class)
        }
        polymorphic(Node::class) {
            subclass(GraphNode::class)
        }
        polymorphic(OnChainTransaction::class) {
            subclass(ChannelClosingTransaction::class)
            subclass(ChannelOpeningTransaction::class)
            subclass(Deposit::class)
            subclass(Withdrawal::class)
        }
        polymorphic(PaymentRequest::class) {
            subclass(Invoice::class)
        }
        polymorphic(PaymentRequestData::class) {
            subclass(InvoiceData::class)
        }
        polymorphic(Transaction::class) {
            subclass(ChannelClosingTransaction::class)
            subclass(ChannelOpeningTransaction::class)
            subclass(Deposit::class)
            subclass(IncomingPayment::class)
            subclass(OutgoingPayment::class)
            subclass(Withdrawal::class)
        }
    }

internal val serializerFormat =
    Json {
        serializersModule = serializerModule
        ignoreUnknownKeys = true
    }
