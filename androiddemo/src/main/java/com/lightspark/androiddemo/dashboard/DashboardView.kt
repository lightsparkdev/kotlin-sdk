package com.lightspark.androiddemo.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androiddemo.model.Balance
import com.lightspark.androiddemo.model.NodeDisplayData
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.LightsparkNodePurpose
import com.lightspark.api.type.LightsparkNodeStatus

@Composable
fun DashboardView(
    dashboardData: DashboardData,
    modifier: Modifier = Modifier,
    onNodeKeyRecoverTap: (node: NodeDisplayData) -> Unit = {},
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Hey ${dashboardData.accountName}!",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "You have ${dashboardData.blockchainBalance} available to allocate",
                style = MaterialTheme.typography.titleSmall
            )
        }
        items(dashboardData.overviewNodes) { node ->
            NodeOverview(node) { onNodeKeyRecoverTap(node) }
        }
    }
}

@Preview
@Composable
fun DashboardViewPreview() {
    LightsparkTheme {
        DashboardView(
            DashboardData(
                accountName = "John Doe",
                blockchainBalance = Balance(1000, CurrencyUnit.SATOSHI),
                overviewNodes = listOf(
                    NodeDisplayData(
                        id = "1",
                        name = "Fake Node 1",
                        purpose = LightsparkNodePurpose.ROUTING,
                        status = LightsparkNodeStatus.STOPPED,
                        color = "#FF0000",
                        publicKey = "shjdyh7932302",
                        totalBalance = Balance(1000000, CurrencyUnit.SATOSHI),
                        availableBalance = Balance(100000, CurrencyUnit.SATOSHI),

                        ),
                    NodeDisplayData(
                        id = "2",
                        name = "Fake Node 2",
                        purpose = LightsparkNodePurpose.ROUTING,
                        status = LightsparkNodeStatus.READY,
                        color = "#00FF00",
                        publicKey = "shjdyh7932302",
                        totalBalance = Balance(1000000, CurrencyUnit.SATOSHI),
                        availableBalance = Balance(100000, CurrencyUnit.SATOSHI),
                    ),
                )
            )
        )
    }
}