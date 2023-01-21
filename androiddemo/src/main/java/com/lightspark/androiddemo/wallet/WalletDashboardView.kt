package com.lightspark.androiddemo.wallet

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.lightspark.androiddemo.navigation.Screen
import com.lightspark.androiddemo.ui.LoadingPage
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.ui.theme.Success
import com.lightspark.androiddemo.util.displayString
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.TransactionStatus
import com.lightspark.sdk.Lce
import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.model.Transaction
import com.lightspark.sdk.model.WalletDashboardData
import kotlin.math.max

@Composable
fun WalletDashboardView(
    walletData: Lce<WalletDashboardData>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onSendTap: (() -> Unit)? = null,
    onReceiveTap: (() -> Unit)? = null,
    onTransactionTap: ((Transaction) -> Unit)? = null
) {
    val itemHeight = with(LocalDensity.current) { 60.dp.toPx() }
    val scrollState = rememberLazyListState()
    val scrollOffset by remember(scrollState) {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset + scrollState.firstVisibleItemIndex * itemHeight
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (walletData) {
            is Lce.Content -> {
                WalletHeader(
                    walletData.data,
                    scrollOffset,
                    onSendTap = {
                        navController.navigate(Screen.SendPayment.route)
                        onSendTap?.invoke()
                    },
                    onReceiveTap = {
                        navController.navigate(Screen.RequestPayment.route)
                        onReceiveTap?.invoke()
                    }
                )
                TransactionList(
                    walletData = walletData.data,
                    scrollState = scrollState,
                    onTransactionTap = onTransactionTap,
                    modifier = Modifier.weight(.6f)
                )
            }
            is Lce.Error -> {
                Text(
                    text = "Error: ${walletData.exception?.message ?: "Unknown"}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            is Lce.Loading -> {
                LoadingPage()
            }
        }
    }
}

@Composable
fun WalletHeader(
    walletData: WalletDashboardData,
    scrollOffset: Float,
    modifier: Modifier = Modifier,
    onSendTap: (() -> Unit)? = null,
    onReceiveTap: (() -> Unit)? = null
) {
    val offsetDp = with(LocalDensity.current) { scrollOffset.toDp() }
    val headerHeight by animateDpAsState(targetValue = max(120.dp, 350.dp - offsetDp))
    val buttonAlpha by animateFloatAsState(targetValue = max(0f, 1f - offsetDp.value / 100f))
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight)
            .shadow(6.dp, RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
            .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Spacer(modifier = Modifier.weight(.25f))
        WalletBalances(walletData, scrollOffset, modifier = Modifier.weight(.4f))
        if (buttonAlpha > 0f) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                modifier = Modifier
                    .weight(.25f * buttonAlpha)
                    .alpha(buttonAlpha)
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
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
    onTransactionTap: ((Transaction) -> Unit)? = null
) {
    LazyColumn(
        state = scrollState,
        modifier = modifier.fillMaxWidth()
    ) {
        items(walletData.recentTransactions + fakeTransactions()) { transaction ->
            TransactionRow(transaction, onTap = { onTransactionTap?.invoke(transaction) })
        }
    }
}

@Composable
fun WalletBalances(walletData: WalletDashboardData, scrollOffset: Float, modifier: Modifier) {
    val offsetDp = with(LocalDensity.current) { scrollOffset.toDp() }
    val diffAlpha by animateFloatAsState(targetValue = max(0f, 1f - offsetDp.value / 100f))
    val balanceSize by animateFloatAsState(targetValue = max(.75f, 1f - 0.25f * offsetDp.value / 100f))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.graphicsLayer { scaleX = balanceSize; scaleY = balanceSize }
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
        Text(
            text = "+ XX,XXX",
            style = MaterialTheme.typography.bodyMedium,
            color = Success.copy(alpha = diffAlpha)
        )
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
            Lce.Content(
                WalletDashboardData(
                    "My Wallet",
                    CurrencyAmount(100L, CurrencyUnit.BITCOIN),
                    fakeTransactions()
                )
            ),
            onSendTap = {
                Toast.makeText(context, "Can't send yet!", Toast.LENGTH_SHORT).show()
            },
            onReceiveTap = {
                Toast.makeText(context, "Can't Receive yet!", Toast.LENGTH_SHORT).show()
            },
            onTransactionTap = {
                Toast.makeText(context, "No transaction details yet!", Toast.LENGTH_SHORT).show()
            },
            navController = rememberNavController()
        )
    }
}