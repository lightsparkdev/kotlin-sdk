package com.lightspark.sdk.util

import com.lightspark.sdk.model.Account
import com.lightspark.sdk.model.ApiToken
import com.lightspark.sdk.model.Channel
import com.lightspark.sdk.model.ChannelClosingTransaction
import com.lightspark.sdk.model.ChannelOpeningTransaction
import com.lightspark.sdk.model.Deposit
import com.lightspark.sdk.model.Entity
import com.lightspark.sdk.model.GraphNode
import com.lightspark.sdk.model.Hop
import com.lightspark.sdk.model.IncomingPayment
import com.lightspark.sdk.model.IncomingPaymentAttempt
import com.lightspark.sdk.model.Invoice
import com.lightspark.sdk.model.InvoiceData
import com.lightspark.sdk.model.LightningTransaction
import com.lightspark.sdk.model.LightsparkNode
import com.lightspark.sdk.model.LightsparkNodeOwner
import com.lightspark.sdk.model.Node
import com.lightspark.sdk.model.OnChainTransaction
import com.lightspark.sdk.model.OutgoingPayment
import com.lightspark.sdk.model.OutgoingPaymentAttempt
import com.lightspark.sdk.model.PaymentRequest
import com.lightspark.sdk.model.PaymentRequestData
import com.lightspark.sdk.model.RoutingTransaction
import com.lightspark.sdk.model.Transaction
import com.lightspark.sdk.model.Wallet
import com.lightspark.sdk.model.Withdrawal
import com.lightspark.sdk.model.WithdrawalRequest
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val serializerModule = SerializersModule {
    polymorphic(Entity::class) {
        subclass(Account::class)
        subclass(ApiToken::class)
        subclass(Channel::class)
        subclass(ChannelClosingTransaction::class)
        subclass(ChannelOpeningTransaction::class)
        subclass(Deposit::class)
        subclass(GraphNode::class)
        subclass(Hop::class)
        subclass(IncomingPayment::class)
        subclass(IncomingPaymentAttempt::class)
        subclass(Invoice::class)
        subclass(LightsparkNode::class)
        subclass(OutgoingPayment::class)
        subclass(OutgoingPaymentAttempt::class)
        subclass(RoutingTransaction::class)
        subclass(Wallet::class)
        subclass(Withdrawal::class)
        subclass(WithdrawalRequest::class)
    }
    polymorphic(LightningTransaction::class) {
        subclass(IncomingPayment::class)
        subclass(OutgoingPayment::class)
        subclass(RoutingTransaction::class)
    }
    polymorphic(LightsparkNodeOwner::class) {
        subclass(Account::class)
        subclass(Wallet::class)
    }
    polymorphic(Node::class) {
        subclass(GraphNode::class)
        subclass(LightsparkNode::class)
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

internal val serializerFormat = Json {
    serializersModule = serializerModule
    ignoreUnknownKeys = true
}
