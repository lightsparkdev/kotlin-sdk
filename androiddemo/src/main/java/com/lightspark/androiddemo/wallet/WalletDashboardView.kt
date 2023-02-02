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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.lightspark.androiddemo.R
import com.lightspark.androiddemo.auth.ui.MissingCredentialsScreen
import com.lightspark.androiddemo.auth.ui.NodePasswordDialog
import com.lightspark.androiddemo.model.NodeLockStatus
import com.lightspark.androiddemo.navigation.Screen
import com.lightspark.androiddemo.ui.LoadingPage
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.ui.theme.Success
import com.lightspark.androiddemo.util.displayString
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.TransactionStatus
import com.lightspark.sdk.Lce
import com.lightspark.sdk.LightsparkErrorCode
import com.lightspark.sdk.LightsparkException
import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.model.Transaction
import com.lightspark.sdk.model.WalletDashboardData
import kotlin.math.max

@Composable
fun WalletDashboardView(
    walletData: Lce<WalletDashboardData>,
    walletUnlockStatus: NodeLockStatus,
    navController: NavController,
    modifier: Modifier = Modifier,
    onRefreshData: (() -> Unit)? = null,
    onSendTap: (() -> Unit)? = null,
    onReceiveTap: (() -> Unit)? = null,
    onTransactionTap: ((Transaction) -> Unit)? = null,
    onUnlockRequest: ((password: String) -> Unit)? = null,
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
                    walletUnlockStatus,
                    scrollOffset,
                    onSendTap = {
                        navController.navigate(Screen.SendPayment.route)
                        onSendTap?.invoke()
                    },
                    onReceiveTap = {
                        navController.navigate(Screen.RequestPayment.route)
                        onReceiveTap?.invoke()
                    },
                    onUnlockRequest = onUnlockRequest,
                )
                TransactionList(
                    walletData = walletData.data,
                    scrollState = scrollState,
                    onTransactionTap = onTransactionTap,
                    modifier = Modifier.weight(.6f)
                )
            }
            is Lce.Error -> {
                when ((walletData.exception as? LightsparkException)?.errorCode) {
                    LightsparkErrorCode.NO_CREDENTIALS -> {
                        MissingCredentialsScreen(
                            modifier = modifier.fillMaxSize(),
                            buttonText = "Log in",
                            onSettingsTapped = {
                                navController.navigate(Screen.Settings.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                    LightsparkErrorCode.MISSING_WALLET_ID -> {
                        MissingCredentialsScreen(
                            modifier = modifier.fillMaxSize(),
                            textOverride = "It looks like you haven't chosen a default wallet node yet. You can pick one to unlock in the account page.",
                            buttonText = "Select wallet node",
                            onSettingsTapped = {
                                navController.navigate(Screen.Account.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                    else -> {
                        Text(
                            text = "Error: ${walletData.exception?.message ?: "Unknown"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
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
    walletUnlockStatus: NodeLockStatus,
    scrollOffset: Float,
    modifier: Modifier = Modifier,
    onSendTap: (() -> Unit)? = null,
    onReceiveTap: (() -> Unit)? = null,
    onUnlockRequest: ((password: String) -> Unit)? = null
) {
    var passwordDialogOpen by remember { mutableStateOf(false) }
    val offsetDp = with(LocalDensity.current) { scrollOffset.toDp() }
    val headerHeight by animateDpAsState(targetValue = max(120.dp, 350.dp - offsetDp))
    val buttonAlpha by animateFloatAsState(targetValue = max(0f, 1f - offsetDp.value / 50f))
    val buttonHeightFactor by animateFloatAsState(
        targetValue = if (offsetDp.value < 50f) 1f else max(
            0f,
            2f - offsetDp.value / 50f
        )
    )
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
        Spacer(modifier = Modifier.weight(.2f))
        UnlockButton(walletUnlockStatus, buttonHeightFactor, buttonAlpha) {
            if (walletUnlockStatus == NodeLockStatus.LOCKED) {
                passwordDialogOpen = true
            }
        }
        WalletBalances(walletData, scrollOffset, modifier = Modifier.weight(.4f))
        PaymentButtons(offsetDp, onSendTap, onReceiveTap)
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
    NodePasswordDialog(
        nodeName = walletData.nodeDisplayName,
        open = passwordDialogOpen,
        onDismiss = { passwordDialogOpen = false }) {
        onUnlockRequest?.invoke(it)
    }
}

@Composable
private fun UnlockButton(
    walletUnlockStatus: NodeLockStatus,
    buttonHeightFactor: Float,
    buttonAlpha: Float,
    onClick: () -> Unit
) {
    FilledIconButton(
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = if (walletUnlockStatus == NodeLockStatus.UNLOCKED) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            contentColor = MaterialTheme.colorScheme.background
        ),
        enabled = walletUnlockStatus != NodeLockStatus.UNLOCKING,
        modifier = Modifier
            .height(40.dp * buttonHeightFactor)
            .alpha(buttonAlpha)
    ) {
        when (walletUnlockStatus) {
            NodeLockStatus.LOCKED -> Icon(Icons.Filled.Lock, contentDescription = "Locked")
            NodeLockStatus.UNLOCKING -> CircularProgressIndicator(
                color = MaterialTheme.colorScheme.background
            )
            NodeLockStatus.UNLOCKED -> Icon(
                painterResource(id = R.drawable.ic_lock_open),
                contentDescription = "Unlocked"
            )
        }
    }
}

@Composable
private fun ColumnScope.PaymentButtons(
    scrollOffsetDp: Dp,
    onSendTap: (() -> Unit)?,
    onReceiveTap: (() -> Unit)?
) {
    if (scrollOffsetDp.value >= 100f) return
    val buttonAlpha by animateFloatAsState(targetValue = max(0f, 1f - scrollOffsetDp.value / 50f))
    val buttonHeightFactor by animateFloatAsState(
        targetValue = if (scrollOffsetDp.value < 50f) 1f else max(
            0f,
            2f - scrollOffsetDp.value / 50f
        )
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        modifier = Modifier.Companion
            .weight(.25f * buttonHeightFactor)
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

@Composable
fun TransactionList(
    walletData: WalletDashboardData,
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
    onTransactionTap: ((Transaction) -> Unit)? = null
) {
    if (walletData.recentTransactions.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No transactions")
        }
        return
    }
    LazyColumn(
        state = scrollState,
        modifier = modifier.fillMaxWidth()
    ) {
        items(walletData.recentTransactions) { transaction ->
            TransactionRow(transaction, onTap = { onTransactionTap?.invoke(transaction) })
        }
    }
}

@Composable
fun WalletBalances(walletData: WalletDashboardData, scrollOffset: Float, modifier: Modifier) {
    val offsetDp = with(LocalDensity.current) { scrollOffset.toDp() }
    val diffAlpha by animateFloatAsState(targetValue = max(0f, 1f - offsetDp.value / 50f))
    val endScroll = 230.dp.value
    val yIntercept = 1f
    val balanceSize by animateFloatAsState(
        targetValue = max(
            .75f,
            yIntercept - offsetDp.value * .25f / endScroll
        )
    )
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
                    "Crazy Wallet",
                    CurrencyAmount(100L, CurrencyUnit.BITCOIN),
                    fakeTransactions()
                )
            ),
            walletUnlockStatus = NodeLockStatus.UNLOCKED,
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