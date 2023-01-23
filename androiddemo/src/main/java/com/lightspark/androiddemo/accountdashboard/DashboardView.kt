package com.lightspark.androiddemo.accountdashboard

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
import com.lightspark.androiddemo.model.NodeDisplayData
import com.lightspark.androiddemo.ui.LoadingPage
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.util.displayString
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.LightsparkNodePurpose
import com.lightspark.api.type.LightsparkNodeStatus
import com.lightspark.sdk.Lce
import com.lightspark.sdk.model.CurrencyAmount

@Composable
fun DashboardView(
    dashboardData: Lce<DashboardData>,
    modifier: Modifier = Modifier,
    onNodeKeyRecoverTap: (node: NodeDisplayData) -> Unit = {},
) {
    when (dashboardData) {
        is Lce.Content -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Text(
                        text = "Hey ${dashboardData.data.accountName}!",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "You have ${dashboardData.data.blockchainBalance.displayString()} available to allocate",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                items(dashboardData.data.overviewNodes) { node ->
                    NodeOverview(node) { onNodeKeyRecoverTap(node) }
                }
            }
        }
        is Lce.Error -> {
            Text(
                text = "Error: ${dashboardData.exception}",
                style = MaterialTheme.typography.displayLarge
            )
        }
        is Lce.Loading -> LoadingPage()
    }
}

@Preview
@Composable
fun DashboardViewPreview() {
    LightsparkTheme {
        DashboardView(
            Lce.Content(
                DashboardData(
                    accountName = "John Doe",
                    blockchainBalance = CurrencyAmount(1000, CurrencyUnit.SATOSHI),
                    overviewNodes = listOf(
                        NodeDisplayData(
                            id = "1",
                            name = "Fake Node 1",
                            purpose = LightsparkNodePurpose.ROUTING,
                            status = LightsparkNodeStatus.STOPPED,
                            color = "#FF0000",
                            publicKey = "shjdyh7932302",
                            totalBalance = CurrencyAmount(1000000, CurrencyUnit.SATOSHI),
                            availableBalance = CurrencyAmount(100000, CurrencyUnit.SATOSHI),

                            ),
                        NodeDisplayData(
                            id = "2",
                            name = "Fake Node 2",
                            purpose = LightsparkNodePurpose.ROUTING,
                            status = LightsparkNodeStatus.READY,
                            color = "#00FF00",
                            publicKey = "shjdyh7932302",
                            totalBalance = CurrencyAmount(1000000, CurrencyUnit.SATOSHI),
                            availableBalance = CurrencyAmount(100000, CurrencyUnit.SATOSHI),
                        ),
                    )
                )
            )
        )
    }
}