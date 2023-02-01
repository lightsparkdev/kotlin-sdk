package com.lightspark.androiddemo.model

import com.lightspark.api.type.LightsparkNodePurpose
import com.lightspark.api.type.LightsparkNodeStatus
import com.lightspark.sdk.model.CurrencyAmount

data class NodeDisplayData(
    val id: String,
    val name: String,
    val purpose: LightsparkNodePurpose,
    val color: String,
    val status: LightsparkNodeStatus,
    val publicKey: String,
    val totalBalance: CurrencyAmount,
    val availableBalance: CurrencyAmount,
    val lockStatus: NodeLockStatus = NodeLockStatus.LOCKED,
    val stats: NodeStatistics? = null,
    // TODO(Jeremy): Add addresses?
)


data class NodeStatistics(
    val uptime: Float,
    val numChannels: Int,
    val numPaymentsSent: Int,
    val numPaymentsReceived: Int,
    val numTransactionsRouted: Int,
    val amountRouted: CurrencyAmount
)
