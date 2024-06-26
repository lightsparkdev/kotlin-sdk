package com.lightspark.sdk.util

import com.lightspark.sdk.model.Account
import com.lightspark.sdk.model.AccountToApiTokensConnection
import com.lightspark.sdk.model.AccountToChannelsConnection
import com.lightspark.sdk.model.AccountToNodesConnection
import com.lightspark.sdk.model.AccountToPaymentRequestsConnection
import com.lightspark.sdk.model.AccountToTransactionsConnection
import com.lightspark.sdk.model.AccountToWalletsConnection
import com.lightspark.sdk.model.AccountToWithdrawalRequestsConnection
import com.lightspark.sdk.model.ApiToken
import com.lightspark.sdk.model.AuditLogActor
import com.lightspark.sdk.model.Channel
import com.lightspark.sdk.model.ChannelClosingTransaction
import com.lightspark.sdk.model.ChannelOpeningTransaction
import com.lightspark.sdk.model.ChannelSnapshot
import com.lightspark.sdk.model.Connection
import com.lightspark.sdk.model.Deposit
import com.lightspark.sdk.model.Entity
import com.lightspark.sdk.model.GraphNode
import com.lightspark.sdk.model.Hop
import com.lightspark.sdk.model.IncomingPayment
import com.lightspark.sdk.model.IncomingPaymentAttempt
import com.lightspark.sdk.model.IncomingPaymentToAttemptsConnection
import com.lightspark.sdk.model.Invoice
import com.lightspark.sdk.model.InvoiceData
import com.lightspark.sdk.model.LightningTransaction
import com.lightspark.sdk.model.LightsparkNode
import com.lightspark.sdk.model.LightsparkNodeOwner
import com.lightspark.sdk.model.LightsparkNodeToChannelsConnection
import com.lightspark.sdk.model.LightsparkNodeWithOSK
import com.lightspark.sdk.model.LightsparkNodeWithRemoteSigning
import com.lightspark.sdk.model.Node
import com.lightspark.sdk.model.OnChainTransaction
import com.lightspark.sdk.model.OutgoingPayment
import com.lightspark.sdk.model.OutgoingPaymentAttempt
import com.lightspark.sdk.model.OutgoingPaymentAttemptToHopsConnection
import com.lightspark.sdk.model.OutgoingPaymentToAttemptsConnection
import com.lightspark.sdk.model.PaymentRequest
import com.lightspark.sdk.model.PaymentRequestData
import com.lightspark.sdk.model.RoutingTransaction
import com.lightspark.sdk.model.Signable
import com.lightspark.sdk.model.SignablePayload
import com.lightspark.sdk.model.Transaction
import com.lightspark.sdk.model.UmaInvitation
import com.lightspark.sdk.model.Wallet
import com.lightspark.sdk.model.WalletToPaymentRequestsConnection
import com.lightspark.sdk.model.WalletToTransactionsConnection
import com.lightspark.sdk.model.WalletToWithdrawalRequestsConnection
import com.lightspark.sdk.model.Withdrawal
import com.lightspark.sdk.model.WithdrawalRequest
import com.lightspark.sdk.model.WithdrawalRequestToChannelClosingTransactionsConnection
import com.lightspark.sdk.model.WithdrawalRequestToChannelOpeningTransactionsConnection
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val serializerModule =
    SerializersModule {
        polymorphic(AuditLogActor::class) {
            subclass(ApiToken::class)
        }
        polymorphic(Connection::class) {
            subclass(AccountToApiTokensConnection::class)
            subclass(AccountToChannelsConnection::class)
            subclass(AccountToNodesConnection::class)
            subclass(AccountToPaymentRequestsConnection::class)
            subclass(AccountToTransactionsConnection::class)
            subclass(AccountToWalletsConnection::class)
            subclass(AccountToWithdrawalRequestsConnection::class)
            subclass(IncomingPaymentToAttemptsConnection::class)
            subclass(LightsparkNodeToChannelsConnection::class)
            subclass(OutgoingPaymentAttemptToHopsConnection::class)
            subclass(OutgoingPaymentToAttemptsConnection::class)
            subclass(WalletToPaymentRequestsConnection::class)
            subclass(WalletToTransactionsConnection::class)
            subclass(WalletToWithdrawalRequestsConnection::class)
            subclass(WithdrawalRequestToChannelClosingTransactionsConnection::class)
            subclass(WithdrawalRequestToChannelOpeningTransactionsConnection::class)
        }
        polymorphic(Entity::class) {
            subclass(Account::class)
            subclass(ApiToken::class)
            subclass(Channel::class)
            subclass(ChannelClosingTransaction::class)
            subclass(ChannelOpeningTransaction::class)
            subclass(ChannelSnapshot::class)
            subclass(Deposit::class)
            subclass(GraphNode::class)
            subclass(Hop::class)
            subclass(IncomingPayment::class)
            subclass(IncomingPaymentAttempt::class)
            subclass(Invoice::class)
            subclass(LightsparkNodeWithOSK::class)
            subclass(LightsparkNodeWithRemoteSigning::class)
            subclass(OutgoingPayment::class)
            subclass(OutgoingPaymentAttempt::class)
            subclass(RoutingTransaction::class)
            subclass(Signable::class)
            subclass(SignablePayload::class)
            subclass(UmaInvitation::class)
            subclass(Wallet::class)
            subclass(Withdrawal::class)
            subclass(WithdrawalRequest::class)
        }
        polymorphic(LightningTransaction::class) {
            subclass(IncomingPayment::class)
            subclass(OutgoingPayment::class)
            subclass(RoutingTransaction::class)
        }
        polymorphic(LightsparkNode::class) {
            subclass(LightsparkNodeWithOSK::class)
            subclass(LightsparkNodeWithRemoteSigning::class)
        }
        polymorphic(LightsparkNodeOwner::class) {
            subclass(Account::class)
            subclass(Wallet::class)
        }
        polymorphic(Node::class) {
            subclass(GraphNode::class)
            subclass(LightsparkNodeWithOSK::class)
            subclass(LightsparkNodeWithRemoteSigning::class)
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
            subclass(RoutingTransaction::class)
            subclass(Withdrawal::class)
        }
    }

internal val serializerFormat =
    Json {
        serializersModule = serializerModule
        ignoreUnknownKeys = true
    }
