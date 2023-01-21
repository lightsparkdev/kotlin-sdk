package com.lightspark.androiddemo.wallet

import android.annotation.SuppressLint
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
import com.lightspark.androiddemo.ui.theme.Warning
import com.lightspark.androiddemo.util.displayString
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.TransactionStatus
import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.model.Transaction
import java.text.SimpleDateFormat

@Composable
fun TransactionRow(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    onTap: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onTap?.invoke() }
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(70.dp)
    ) {
        TransactionTypeIcon(transaction.type)
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = transaction.type.displayName(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = transaction.createdAt.formatDate(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = transaction.amount.displayString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@SuppressLint("SimpleDateFormat")
private fun String.formatDate(): String {
    val inputFormatWithMs = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS+00:00")
    val inputFormatWithoutMs = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00")
    val date = tryOrNull {
        inputFormatWithMs.parse(this)
    } ?: tryOrNull {
        inputFormatWithoutMs.parse(this)
    } ?: return this
    val formatter = SimpleDateFormat("MMM dd, hh:mma")
    return formatter.format(date)
}

private inline fun <T> tryOrNull(block: () -> T): T? {
    return try {
        block()
    } catch (e: Exception) {
        null
    }
}

@Composable
fun TransactionTypeIcon(type: Transaction.Type) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color = type.color()),
        contentAlignment = Alignment.Center
    ) {
        type.icon()
    }
}

@Composable
private fun Transaction.Type.icon() {
    when (this) {
        Transaction.Type.PAYMENT_RECEIVED -> Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = name,
            tint = Color.White
        )
        Transaction.Type.PAYMENT_SENT -> Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = name,
            tint = Color.White
        )
        Transaction.Type.DEPOSIT -> Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = name,
            tint = Color.White
        )
        Transaction.Type.WITHDRAWAL -> Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = name,
            tint = Color.White
        )
        Transaction.Type.PAYMENT_REQUEST -> Icon(
            painter = painterResource(id = R.drawable.ic_qr_code),
            contentDescription = name,
            tint = Color.White
        )
        Transaction.Type.UNKNOWN -> Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = name,
            tint = Color.White
        )
    }
}

private fun Transaction.Type.displayName(): String {
    return when (this) {
        Transaction.Type.PAYMENT_RECEIVED -> "Received"
        Transaction.Type.PAYMENT_SENT -> "Sent"
        Transaction.Type.DEPOSIT -> "Deposit"
        Transaction.Type.WITHDRAWAL -> "Withdrawal"
        Transaction.Type.PAYMENT_REQUEST -> "Created Invoice"
        Transaction.Type.UNKNOWN -> "Unknown"
    }
}

@Composable
private fun Transaction.Type.color(): Color {
    return when (this) {
        Transaction.Type.PAYMENT_RECEIVED -> Success
        Transaction.Type.PAYMENT_SENT -> LightsparkBlue
        Transaction.Type.DEPOSIT -> Success
        Transaction.Type.WITHDRAWAL -> LightsparkBlue
        Transaction.Type.PAYMENT_REQUEST -> NeutralGrey
        Transaction.Type.UNKNOWN -> Warning
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionRowPreview() {
    TransactionRow(
        transaction = Transaction(
            "2",
            CurrencyAmount(10000, CurrencyUnit.SATOSHI),
            TransactionStatus.SUCCESS,
            "2023-01-18T08:30:28.300854+00:00",
            "2023-01-18T08:30:28.300854+00:00",
            Transaction.Type.PAYMENT_RECEIVED,
            null,
            null,
            null
        )
    )
}