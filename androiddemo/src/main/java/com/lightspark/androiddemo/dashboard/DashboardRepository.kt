package com.lightspark.androiddemo.dashboard

import com.lightspark.androiddemo.LightsparkClientProvider
import com.lightspark.sdk.LightsparkClient

class DashboardRepository(private val lightsparkClient: LightsparkClient = LightsparkClientProvider.client) {
    suspend fun getDashboardData() = lightsparkClient.getDashboard()
}