package com.lightspark.androiddemo.wallet

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androiddemo.ui.LoadingPage
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.ui.theme.Success
import com.lightspark.androiddemo.util.displayString
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.TransactionStatus
import com.lightspark.sdk.Result
import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.model.Transaction
import com.lightspark.sdk.model.WalletDashboardData

@Composable
fun WalletDashboardView(
    walletData: Result<WalletDashboardData>,
    modifier: Modifier = Modifier,
    onSendTap: (() -> Unit)? = null,
    onReceiveTap: (() -> Unit)? = null,
    onTransactionTap: ((Transaction) -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (walletData) {
            is Result.Success -> {
                WalletHeader(
                    walletData.data,
                    modifier = Modifier.weight(.4f),
                    onSendTap = onSendTap,
                    onReceiveTap = onReceiveTap
                )
                TransactionList(
                    walletData = walletData.data,
                    onTransactionTap = onTransactionTap,
                    modifier = Modifier.weight(.6f)
                )
            }
            is Result.Error -> {
                Text(
                    text = "Error: ${walletData.exception?.message ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            is Result.Loading -> {
                LoadingPage()
            }
        }
    }
}

@Composable
fun WalletHeader(
    walletData: WalletDashboardData,
    modifier: Modifier = Modifier,
    onSendTap: (() -> Unit)? = null,
    onReceiveTap: (() -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
            .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Spacer(modifier = Modifier.weight(.25f))
        WalletBalances(walletData, modifier = Modifier.weight(.4f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            modifier = Modifier.weight(.25f)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                onClick = { onSendTap?.invoke() },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Send")
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                onClick = { onReceiveTap?.invoke() },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Receive")
            }
        }
        Box(modifier = Modifier.weight(.2f), contentAlignment = Alignment.BottomCenter) {
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .width(62.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(100))
                    .background(MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}

@Composable
fun TransactionList(
    walletData: WalletDashboardData,
    modifier: Modifier = Modifier,
    onTransactionTap: ((Transaction) -> Unit)? = null
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(walletData.recentTransactions + fakeTransactions()) { transaction ->
            TransactionRow(transaction, onTap = { onTransactionTap?.invoke(transaction) })
        }
    }
}

@Composable
fun WalletBalances(walletData: WalletDashboardData, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "\$XX,XXX.XX USD",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = walletData.balance.displayString(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(text = "+ XX,XXX", style = MaterialTheme.typography.bodyMedium, color = Success)
    }
}

fun fakeTransactions() =
    List(20) {
        Transaction(
            "Transaction $it",
            CurrencyAmount(100_000L, CurrencyUnit.SATOSHI),
            TransactionStatus.knownValues()[it % (TransactionStatus.knownValues().size - 1)],
            "2023-01-18T08:30:28.300854+00:00",
            "2023-01-18T08:30:28.300854+00:00",
            Transaction.Type.values()[it % (Transaction.Type.values().size - 1)],
            null,
            null,
            null
        )
    }

@Preview
@Composable
fun WalletPreview() {
    val context = LocalContext.current
    LightsparkTheme {
        WalletDashboardView(
            Result.Success(WalletDashboardData(
                "My Wallet",
                CurrencyAmount(100L, CurrencyUnit.BITCOIN),
                fakeTransactions()
            )),
            onSendTap = {
                Toast.makeText(context, "Can't send yet!", Toast.LENGTH_SHORT).show()
            },
            onReceiveTap = {
                Toast.makeText(context, "Can't Receive yet!", Toast.LENGTH_SHORT).show()
            },
            onTransactionTap = {
                Toast.makeText(context, "No transaction details yet!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}