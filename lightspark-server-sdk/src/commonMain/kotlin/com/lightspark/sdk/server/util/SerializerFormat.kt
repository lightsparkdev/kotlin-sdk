package com.lightspark.sdk.server.util

import com.lightspark.sdk.server.model.Account
import com.lightspark.sdk.server.model.ApiToken
import com.lightspark.sdk.server.model.Channel
import com.lightspark.sdk.server.model.ChannelClosingTransaction
import com.lightspark.sdk.server.model.ChannelOpeningTransaction
import com.lightspark.sdk.server.model.Deposit
import com.lightspark.sdk.server.model.Entity
import com.lightspark.sdk.server.model.GraphNode
import com.lightspark.sdk.server.model.Hop
import com.lightspark.sdk.server.model.IncomingPayment
import com.lightspark.sdk.server.model.IncomingPaymentAttempt
import com.lightspark.sdk.server.model.Invoice
import com.lightspark.sdk.server.model.InvoiceData
import com.lightspark.sdk.server.model.LightningTransaction
import com.lightspark.sdk.server.model.LightsparkNode
import com.lightspark.sdk.server.model.Node
import com.lightspark.sdk.server.model.OnChainTransaction
import com.lightspark.sdk.server.model.OutgoingPayment
import com.lightspark.sdk.server.model.OutgoingPaymentAttempt
import com.lightspark.sdk.server.model.PaymentRequest
import com.lightspark.sdk.server.model.PaymentRequestData
import com.lightspark.sdk.server.model.RoutingTransaction
import com.lightspark.sdk.server.model.Transaction
import com.lightspark.sdk.server.model.Withdrawal
import com.lightspark.sdk.server.model.WithdrawalRequest
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
        subclass(Withdrawal::class)
        subclass(WithdrawalRequest::class)
    }
    polymorphic(LightningTransaction::class) {
        subclass(IncomingPayment::class)
        subclass(OutgoingPayment::class)
        subclass(RoutingTransaction::class)
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
