package com.lightspark.androiddemo.dashboard

import com.lightspark.androiddemo.model.NodeDisplayData
import com.lightspark.sdk.model.CurrencyAmount

data class DashboardData(
    val accountName: String,
    val overviewNodes: List<NodeDisplayData>,
    val blockchainBalance: CurrencyAmount,
    // TODO(Jeremy): Add transaction and channel stats.
)
