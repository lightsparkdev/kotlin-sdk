package com.lightspark.androiddemo.dashboard

import com.lightspark.androiddemo.model.Balance
import com.lightspark.androiddemo.model.NodeDisplayData

data class DashboardData(
    val accountName: String,
    val overviewNodes: List<NodeDisplayData>,
    val blockchainBalance: Balance,
    // TODO(Jeremy): Add transaction and channel stats.
)
