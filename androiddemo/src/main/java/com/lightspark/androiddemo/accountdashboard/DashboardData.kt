package com.lightspark.androiddemo.accountdashboard

import com.lightspark.androiddemo.model.NodeDisplayData
import com.lightspark.sdk.server.model.CurrencyAmount

data class DashboardData(
    val accountName: String,
    val overviewNodes: List<NodeDisplayData>,
    val blockchainBalance: CurrencyAmount,
    // TODO: Add transaction and channel stats.
)
