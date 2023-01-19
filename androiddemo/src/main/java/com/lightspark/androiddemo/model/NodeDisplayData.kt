package com.lightspark.androiddemo.model

import com.lightspark.api.type.LightsparkNodePurpose
import com.lightspark.api.type.LightsparkNodeStatus

data class NodeDisplayData(
    val id: String,
    val name: String,
    val purpose: LightsparkNodePurpose,
    val color: String,
    val status: LightsparkNodeStatus,
    val publicKey: String,
    val totalBalance: Balance,
    val availableBalance: Balance,
    val stats: NodeStatistics? = null
    // TODO(Jeremy): Add addresses?
)

data class NodeStatistics(
    val uptime: Float,
    val numChannels: Int,
    val numPaymentsSent: Int,
    val numPaymentsReceived: Int,
    val numTransactionsRouted: Int,
    val amountRouted: Balance
)
