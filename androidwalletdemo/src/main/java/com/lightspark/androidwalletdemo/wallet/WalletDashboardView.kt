package com.lightspark.androidwalletdemo.wallet

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.lightspark.androidwalletdemo.R
import com.lightspark.androidwalletdemo.auth.ui.MissingCredentialsScreen
import com.lightspark.androidwalletdemo.auth.ui.NodePasswordDialog
import com.lightspark.androidwalletdemo.model.WalletLockStatus
import com.lightspark.androidwalletdemo.navigation.Screen
import com.lightspark.androidwalletdemo.ui.LoadingPage
import com.lightspark.androidwalletdemo.ui.theme.LightsparkTheme
import com.lightspark.androidwalletdemo.ui.theme.Success
import com.lightspark.androidwalletdemo.util.currencyAmountSats
import com.lightspark.androidwalletdemo.util.displayString
import com.lightspark.androidwalletdemo.util.zeroCurrencyAmount
import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.core.LightsparkErrorCode
import com.lightspark.sdk.core.LightsparkException
import com.lightspark.sdk.wallet.graphql.WalletDashboard
import com.lightspark.sdk.wallet.model.Balances
import com.lightspark.sdk.wallet.model.OutgoingPayment
import com.lightspark.sdk.wallet.model.PageInfo
import com.lightspark.sdk.wallet.model.Transaction
import com.lightspark.sdk.wallet.model.TransactionStatus
import com.lightspark.sdk.wallet.model.WalletStatus
import com.lightspark.sdk.wallet.model.WalletToPaymentRequestsConnection
import com.lightspark.sdk.wallet.model.WalletToTransactionsConnection
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.math.max

@Composable
fun WalletDashboardView(
    walletData: Lce<WalletDashboard>,
    walletUnlockStatus: WalletLockStatus,
    navController: NavController,
    modifier: Modifier = Modifier,
    onRefreshData: (() -> Unit)? = null,
    onSendTap: (() -> Unit)? = null,
    onReceiveTap: (() -> Unit)? = null,
    onDeployWallet: () -> Unit = {},
    onInitializeWallet: () -> Unit = {},
    onTransactionTap: ((Transaction) -> Unit)? = null,
    onUnlockRequest: ((password: String) -> Unit)? = null,
    attemptKeyStoreUnlock: () -> Boolean = { false },
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
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (walletData) {
            is Lce.Content -> {
                when (walletData.data.status) {
                    WalletStatus.READY -> {
                        ReadyWallet(
                            walletData.data,
                            walletUnlockStatus,
                            navController,
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
                            scrollState = scrollState,
                            onTransactionTap = onTransactionTap,
                            attemptKeyStoreUnlock = attemptKeyStoreUnlock,
                        )
                    }
                    in setOf(WalletStatus.NOT_SETUP, WalletStatus.FAILED) -> {
                        NotSetupWallet(onDeployWallet)
                    }
                    WalletStatus.DEPLOYED -> {
                        NotInitializedWallet(onInitializeWallet)
                    }
                    else -> {
                        Text(
                            text = "Wallet status: ${walletData.data.status}",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(48.dp),
                        )
                        CircularProgressIndicator()
                    }
                }
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
                            },
                        )
                    }

                    else -> {
                        Log.e(
                            "WalletDashboardView",
                            "Error: ${walletData.exception?.message ?: "Unknown"}",
                            walletData.exception,
                        )
                        Text(
                            text = "Error: ${walletData.exception?.message ?: "Unknown"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
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
fun ColumnScope.ReadyWallet(
    walletData: WalletDashboard,
    walletUnlockStatus: WalletLockStatus,
    navController: NavController,
    scrollOffset: Float,
    onSendTap: (() -> Unit)? = null,
    onReceiveTap: (() -> Unit)? = null,
    onUnlockRequest: ((password: String) -> Unit)? = null,
    attemptKeyStoreUnlock: () -> Boolean = { false },
    onTransactionTap: ((Transaction) -> Unit)? = null,
    scrollState: LazyListState = rememberLazyListState(),
) {
    WalletHeader(
        walletData,
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
        attemptKeyStoreUnlock = attemptKeyStoreUnlock,
    )
    TransactionList(
        walletData = walletData,
        scrollState = scrollState,
        onTransactionTap = onTransactionTap,
        modifier = Modifier.weight(.6f),
    )
}

@Composable
fun ColumnScope.NotSetupWallet(onDeployWallet: () -> Unit) {
    Text(
        text = "Wallet not yet deployed",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(48.dp),
    )
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
        onClick = onDeployWallet,
        modifier = Modifier
            .fillMaxWidth(0.8f),
    ) {
        Text("Deploy wallet")
    }
}

@Composable
fun ColumnScope.NotInitializedWallet(onInitializeWallet: () -> Unit) {
    Text(
        text = "Now you need to initialize your wallet with a generated keys.",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(48.dp),
    )
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
        onClick = onInitializeWallet,
        modifier = Modifier
            .fillMaxWidth(0.8f),
    ) {
        Text("Initialize wallet")
    }
}

@Composable
fun WalletHeader(
    walletData: WalletDashboard,
    walletUnlockStatus: WalletLockStatus,
    scrollOffset: Float,
    modifier: Modifier = Modifier,
    onSendTap: (() -> Unit)? = null,
    onReceiveTap: (() -> Unit)? = null,
    onUnlockRequest: ((password: String) -> Unit)? = null,
    attemptKeyStoreUnlock: () -> Boolean = { false },
) {
    var passwordDialogOpen by remember { mutableStateOf(false) }
    val offsetDp = with(LocalDensity.current) { scrollOffset.toDp() }
    val headerHeight by animateDpAsState(targetValue = max(120.dp, 350.dp - offsetDp))
    val buttonAlpha by animateFloatAsState(targetValue = max(0f, 1f - offsetDp.value / 50f))
    val buttonHeightFactor by animateFloatAsState(
        targetValue = if (offsetDp.value < 50f) {
            1f
        } else {
            max(
                0f,
                2f - offsetDp.value / 50f,
            )
        },
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight)
            .shadow(6.dp, RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
            .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
            .background(color = MaterialTheme.colorScheme.surface),
    ) {
        Spacer(modifier = Modifier.weight(.2f))
        UnlockButton(walletUnlockStatus, buttonHeightFactor, buttonAlpha) {
            if (walletUnlockStatus == WalletLockStatus.LOCKED && !attemptKeyStoreUnlock()) {
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
                    .background(MaterialTheme.colorScheme.onBackground),
            )
        }
    }
    NodePasswordDialog(
        open = passwordDialogOpen,
        onDismiss = { passwordDialogOpen = false },
    ) {
        onUnlockRequest?.invoke(it)
    }
}

@Composable
private fun UnlockButton(
    walletUnlockStatus: WalletLockStatus,
    buttonHeightFactor: Float,
    buttonAlpha: Float,
    onClick: () -> Unit,
) {
    FilledIconButton(
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = if (walletUnlockStatus == WalletLockStatus.UNLOCKED) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            contentColor = MaterialTheme.colorScheme.background,
        ),
        enabled = walletUnlockStatus != WalletLockStatus.UNLOCKING,
        modifier = Modifier
            .height(40.dp * buttonHeightFactor)
            .alpha(buttonAlpha),
    ) {
        when (walletUnlockStatus) {
            WalletLockStatus.LOCKED -> Icon(Icons.Filled.Lock, contentDescription = "Locked")
            WalletLockStatus.UNLOCKING -> CircularProgressIndicator(
                color = MaterialTheme.colorScheme.background,
            )

            WalletLockStatus.UNLOCKED -> Icon(
                painterResource(id = R.drawable.ic_lock_open),
                contentDescription = "Unlocked",
            )
        }
    }
}

@Composable
private fun ColumnScope.PaymentButtons(
    scrollOffsetDp: Dp,
    onSendTap: (() -> Unit)?,
    onReceiveTap: (() -> Unit)?,
) {
    if (scrollOffsetDp.value >= 100f) return
    val buttonAlpha by animateFloatAsState(targetValue = max(0f, 1f - scrollOffsetDp.value / 50f))
    val buttonHeightFactor by animateFloatAsState(
        targetValue = if (scrollOffsetDp.value < 50f) {
            1f
        } else {
            max(
                0f,
                2f - scrollOffsetDp.value / 50f,
            )
        },
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        modifier = Modifier.Companion
            .weight(.25f * buttonHeightFactor)
            .alpha(buttonAlpha),
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onSendTap?.invoke() },
            modifier = Modifier.padding(end = 8.dp),
        ) {
            Text(text = "Send")
        }
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onReceiveTap?.invoke() },
            modifier = Modifier.padding(end = 8.dp),
        ) {
            Text(text = "Receive")
        }
    }
}

@Composable
fun TransactionList(
    walletData: WalletDashboard,
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
    onTransactionTap: ((Transaction) -> Unit)? = null,
) {
    if (walletData.recentTransactions.entities.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "No transactions")
        }
        return
    }
    LazyColumn(
        state = scrollState,
        modifier = modifier.fillMaxWidth(),
    ) {
        items(walletData.recentTransactions.entities) { transaction ->
            TransactionRow(transaction, onTap = { onTransactionTap?.invoke(transaction) })
        }
    }
}

@Composable
fun WalletBalances(walletData: WalletDashboard, scrollOffset: Float, modifier: Modifier) {
    val offsetDp = with(LocalDensity.current) { scrollOffset.toDp() }
    val diffAlpha by animateFloatAsState(targetValue = max(0f, 1f - offsetDp.value / 50f))
    val endScroll = 230.dp.value
    val yIntercept = 1f
    val balanceSize by animateFloatAsState(
        targetValue = max(
            .75f,
            yIntercept - offsetDp.value * .25f / endScroll,
        ),
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.graphicsLayer { scaleX = balanceSize; scaleY = balanceSize },
    ) {
        Text(
            text = "\$XX,XXX.XX USD",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = (
                walletData.balances?.accountingBalanceL2 ?: zeroCurrencyAmount()
                ).displayString(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "+ XX,XXX",
            style = MaterialTheme.typography.bodyMedium,
            color = Success.copy(alpha = diffAlpha),
        )
    }
}

fun fakeTransactions() =
    List(20) {
        OutgoingPayment(
            "Transaction $it",
            Instant.parse("2023-01-18T08:30:28.300854+00:00"),
            Instant.parse("2023-01-18T08:30:28.300854+00:00"),
            TransactionStatus.values()[it % (TransactionStatus.values().size - 1)],
            currencyAmountSats(100_000),
            Clock.System.now(),
        )
    }

@Preview
@Composable
fun WalletPreview() {
    val context = LocalContext.current
    LightsparkTheme {
        WalletDashboardView(
            Lce.Content(
                WalletDashboard(
                    "My Wallet",
                    status = WalletStatus.READY,
                    balances = Balances(
                        accountingBalanceL1 = currencyAmountSats(100000),
                        accountingBalanceL2 = currencyAmountSats(500000),
                        availableBalanceL1 = currencyAmountSats(100000),
                        availableBalanceL2 = currencyAmountSats(500000),
                        settledBalanceL1 = currencyAmountSats(100000),
                        settledBalanceL2 = currencyAmountSats(500000),
                    ),
                    recentTransactions = WalletToTransactionsConnection(
                        PageInfo(),
                        20,
                        fakeTransactions(),
                    ),
                    paymentRequests = WalletToPaymentRequestsConnection(
                        PageInfo(),
                        0,
                        emptyList(),
                    ),
                ),
            ),
            walletUnlockStatus = WalletLockStatus.UNLOCKED,
            onSendTap = {
                Toast.makeText(context, "Can't send yet!", Toast.LENGTH_SHORT).show()
            },
            onReceiveTap = {
                Toast.makeText(context, "Can't Receive yet!", Toast.LENGTH_SHORT).show()
            },
            onTransactionTap = {
                Toast.makeText(context, "No transaction details yet!", Toast.LENGTH_SHORT).show()
            },
            navController = rememberNavController(),
        )
    }
}
