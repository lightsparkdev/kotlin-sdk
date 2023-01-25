package com.lightspark.androiddemo.accountdashboard

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.lightspark.androiddemo.R
import com.lightspark.androiddemo.model.NodeDisplayData
import com.lightspark.androiddemo.model.NodeStatistics
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.ui.theme.Success
import com.lightspark.androiddemo.util.Separator
import com.lightspark.androiddemo.util.displayString
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.LightsparkNodePurpose
import com.lightspark.api.type.LightsparkNodeStatus
import com.lightspark.sdk.model.CurrencyAmount

@Composable
fun NodeOverview(
    nodeDisplayData: NodeDisplayData,
    modifier: Modifier = Modifier,
    onWalletNodeSelected: () -> Unit = {},
) {
    val unlockButtonColor by animateColorAsState(
        targetValue = when (nodeDisplayData.lockStatus) {
            NodeDisplayData.LockStatus.UNLOCKED -> Success
            else -> MaterialTheme.colorScheme.onSurface
        }
    )
    Card(
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        modifier = modifier.heightIn(max = 500.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            NodeOverviewHeader(nodeDisplayData)
            NodeOverviewBody(nodeDisplayData)
            // NOTE: This is just temporary to test out key recovery:
            Button(
                onClick = onWalletNodeSelected,
                colors = ButtonDefaults.buttonColors(containerColor = unlockButtonColor)
            ) {
                when (nodeDisplayData.lockStatus) {
                    NodeDisplayData.LockStatus.UNLOCKED -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lock_open),
                            contentDescription = "Unlocked",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp)
                        )
                    }
                    NodeDisplayData.LockStatus.UNLOCKING -> CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .offset(y = 4.dp)
                            .padding(end = 8.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    else -> Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Locked",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                    )
                }

                Text("Set as walllet and unlock")
            }
        }
    }
}

@Composable
fun NodeOverviewHeader(nodeDisplayData: NodeDisplayData) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        NodeTypeIcon(purpose = nodeDisplayData.purpose, color = nodeDisplayData.color)
        Text(
            text = nodeDisplayData.name,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.offset(x = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        NodeStatus(status = nodeDisplayData.status)
    }
}

@Composable
fun NodeOverviewBody(nodeDisplayData: NodeDisplayData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row {
            Column {
                Text(
                    text = "Total Balance",
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    text = nodeDisplayData.totalBalance.displayString(),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(
                    text = "Available Balance",
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    text = nodeDisplayData.availableBalance.displayString(),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
        Separator()
        NodeStats(nodeDisplayData)
        Separator()
    }
}

@Composable
fun NodeStats(node: NodeDisplayData) {
    if (node.stats == null) {
        Text(text = "No stats available :-(")
        return
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        userScrollEnabled = false
    ) {
        item(key = "conductivity") { TextNodeStat(title = "Conductivity", content = "⚡⚡⚡⚡⚡") }
        item(key = "uptime") { TextNodeStat(title = "Uptime", content = "${node.stats.uptime}%") }
        item(key = "channels") {
            TextNodeStat(
                title = "Channels",
                content = node.stats.numChannels.toString()
            )
        }
        item(key = "transactions") {
            TextNodeStat(
                title = "Transactions Routed",
                content = node.stats.numTransactionsRouted.toString()
            )
        }
        item(key = "balance") {
            TextNodeStat(
                title = "Total Balance",
                content = node.totalBalance.displayString()
            )
        }
        item(key = "sent") {
            TextNodeStat(
                title = "Payments Sent",
                content = node.stats.numPaymentsSent.toString()
            )
        }
        item(key = "received") {
            TextNodeStat(
                title = "Payments Received",
                content = node.stats.numPaymentsReceived.toString()
            )
        }
        item(key = "amountRouted") {
            TextNodeStat(
                title = "Amount Routed",
                content = node.stats.amountRouted.displayString()
            )
        }
    }
}

@Composable
fun NodeTypeIcon(purpose: LightsparkNodePurpose, color: String) {
    val icon = when (purpose) {
        LightsparkNodePurpose.RECEIVE -> R.drawable.ic_arrow_receive
        LightsparkNodePurpose.SEND -> R.drawable.ic_arrow_send
        LightsparkNodePurpose.ROUTING -> R.drawable.ic_routing_arrows
        LightsparkNodePurpose.UNKNOWN__ -> TODO()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(48.dp)
            .background(color = Color(color.toColorInt()), shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = purpose.name,
            modifier = Modifier.size(36.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun NodeOverviewPreview() {
    LightsparkTheme {
        NodeOverview(
            nodeDisplayData = NodeDisplayData(
                id = "1",
                name = "Test Node",
                color = "#36ce00",
                purpose = LightsparkNodePurpose.ROUTING,
                status = LightsparkNodeStatus.READY,
                publicKey = "testfhjkhjka833h2m9d0",
                totalBalance = CurrencyAmount(1000000, CurrencyUnit.SATOSHI),
                availableBalance = CurrencyAmount(100000, CurrencyUnit.SATOSHI),
                lockStatus = NodeDisplayData.LockStatus.UNLOCKING,
                stats = NodeStatistics(
                    uptime = 99.0f,
                    numChannels = 10,
                    numTransactionsRouted = 100,
                    numPaymentsSent = 10,
                    numPaymentsReceived = 10,
                    amountRouted = CurrencyAmount(1000000, CurrencyUnit.SATOSHI)
                )
            )
        )
    }
}