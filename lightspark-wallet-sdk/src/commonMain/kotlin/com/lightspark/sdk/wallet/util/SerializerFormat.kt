package com.lightspark.sdk.wallet.util

import com.lightspark.sdk.wallet.model.Deposit
import com.lightspark.sdk.wallet.model.Entity
import com.lightspark.sdk.wallet.model.IncomingPayment
import com.lightspark.sdk.wallet.model.Invoice
import com.lightspark.sdk.wallet.model.InvoiceData
import com.lightspark.sdk.wallet.model.LightningTransaction
import com.lightspark.sdk.wallet.model.OnChainTransaction
import com.lightspark.sdk.wallet.model.OutgoingPayment
import com.lightspark.sdk.wallet.model.PaymentRequest
import com.lightspark.sdk.wallet.model.PaymentRequestData
import com.lightspark.sdk.wallet.model.Transaction
import com.lightspark.sdk.wallet.model.Wallet
import com.lightspark.sdk.wallet.model.Withdrawal
import com.lightspark.sdk.wallet.model.WithdrawalRequest
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val serializerModule = SerializersModule {
    polymorphic(Entity::class) {
        subclass(Deposit::class)
        subclass(IncomingPayment::class)
        subclass(Invoice::class)
        subclass(OutgoingPayment::class)
        subclass(Wallet::class)
        subclass(Withdrawal::class)
        subclass(WithdrawalRequest::class)
    }
    polymorphic(LightningTransaction::class) {
        subclass(IncomingPayment::class)
        subclass(OutgoingPayment::class)
    }
    polymorphic(OnChainTransaction::class) {
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
        subclass(Deposit::class)
        subclass(IncomingPayment::class)
        subclass(OutgoingPayment::class)
        subclass(Withdrawal::class)
    }
}

internal val serializerFormat = Json {
    serializersModule = serializerModule
    ignoreUnknownKeys = true
}
