package com.lightspark.androiddemo.accountdashboard

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.lightspark.androiddemo.ui.theme.Danger
import com.lightspark.androiddemo.ui.theme.PendingOrgange
import com.lightspark.androiddemo.ui.theme.Success
import com.lightspark.sdk.model.LightsparkNodeStatus

@Composable
fun NodeStatus(status: LightsparkNodeStatus, modifier: Modifier = Modifier) {
    Text(
        buildAnnotatedString {
            append("Status: ")
            withStyle(style = SpanStyle(color = status.color())) {
                append(status.description())
            }
        },
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier,
    )
}

fun LightsparkNodeStatus.color() = when (this) {
    LightsparkNodeStatus.READY -> Success
    LightsparkNodeStatus.CREATED,
    LightsparkNodeStatus.DEPLOYED,
    LightsparkNodeStatus.STARTED,
    LightsparkNodeStatus.SYNCING,
    -> PendingOrgange
    LightsparkNodeStatus.STOPPED,
    LightsparkNodeStatus.WALLET_LOCKED,
    LightsparkNodeStatus.FAILED_TO_DEPLOY,
    -> Danger
    LightsparkNodeStatus.TERMINATED -> Color.Black
    else -> Color.Black
}

fun LightsparkNodeStatus.description() = when (this) {
    LightsparkNodeStatus.READY -> "OK"
    LightsparkNodeStatus.DEPLOYED,
    LightsparkNodeStatus.STARTED,
    LightsparkNodeStatus.SYNCING,
    -> "Initializing"
    LightsparkNodeStatus.CREATED -> "Deploying"
    LightsparkNodeStatus.STOPPED -> "Offline"
    LightsparkNodeStatus.WALLET_LOCKED -> "Locked"
    LightsparkNodeStatus.FAILED_TO_DEPLOY -> "Failed"
    LightsparkNodeStatus.TERMINATED -> "Terminated"
    else -> "Unknown"
}
