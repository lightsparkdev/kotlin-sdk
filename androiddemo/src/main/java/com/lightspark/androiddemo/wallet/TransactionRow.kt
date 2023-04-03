package com.lightspark.androiddemo.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androiddemo.R
import com.lightspark.androiddemo.ui.theme.LightsparkBlue
import com.lightspark.androiddemo.ui.theme.NeutralGrey
import com.lightspark.androiddemo.ui.theme.Success
import com.lightspark.androiddemo.util.currencyAmountSats
import com.lightspark.androiddemo.util.displayString
import com.lightspark.sdk.model.*
import com.lightspark.sdk.util.format
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat

@Composable
fun TransactionRow(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    onTap: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onTap?.invoke() }
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(70.dp),
    ) {
        TransactionTypeIcon(transaction)
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = transaction.displayName(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = transaction.createdAt.toLocalDateTime(TimeZone.UTC).format("MMM dd, hh:mma"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = transaction.amount.displayString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

private inline fun <T> tryOrNull(block: () -> T): T? {
    return try {
        block()
    } catch (e: Exception) {
        null
    }
}

@Composable
fun TransactionTypeIcon(transaction: Transaction) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color = transaction.color()),
        contentAlignment = Alignment.Center,
    ) {
        transaction.Icon()
    }
}

@Composable
private fun Transaction.Icon() {
    when (this) {
        is IncomingPayment -> Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Incoming Payment",
            tint = Color.White,
        )
        is OutgoingPayment -> Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = "Outgoing Payment",
            tint = Color.White,
        )
        is Deposit -> Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = "Deposit",
            tint = Color.White,
        )
        is Withdrawal -> Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = "Withdrawal",
            tint = Color.White,
        )
        else -> Icon(
            painter = painterResource(id = R.drawable.ic_qr_code),
            contentDescription = "Unknown",
            tint = Color.White,
        )
    }
}

private fun Transaction.displayName(): String {
    return when (this) {
        is IncomingPayment -> "Received"
        is OutgoingPayment -> "Sent"
        is Deposit -> "Deposit"
        is Withdrawal -> "Withdrawal"
        else -> "Unknown"
    }
}

@Composable
private fun Transaction.color(): Color {
    return when (this) {
        is IncomingPayment -> Success
        is OutgoingPayment -> LightsparkBlue
        is Deposit -> Success
        is Withdrawal -> LightsparkBlue
        else -> NeutralGrey
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionRowPreview() {
    TransactionRow(
        OutgoingPayment(
            "Transaction 1",
            Instant.parse("2023-01-18T08:30:28.300854+00:00"),
            Instant.parse("2023-01-18T08:30:28.300854+00:00"),
            TransactionStatus.SUCCESS,
            currencyAmountSats(100000),
            EntityId("OriginNode"),
            Clock.System.now(),
        ),
    )
}
