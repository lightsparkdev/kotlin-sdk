package com.lightspark.androiddemo.model

import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.model.LightsparkNodePurpose
import com.lightspark.sdk.model.LightsparkNodeStatus

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
    // TODO: Add addresses?
)

data class NodeStatistics(
    val uptime: Float,
    val numChannels: Int,
    val numPaymentsSent: Int,
    val numPaymentsReceived: Int,
    val numTransactionsRouted: Int,
    val amountRouted: CurrencyAmount,
)
