package com.lightspark.androidwalletdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lightspark.androidwalletdemo.auth.AuthState
import com.lightspark.androidwalletdemo.auth.ui.AuthScreen
import com.lightspark.androidwalletdemo.navigation.Screen
import com.lightspark.androidwalletdemo.requestpayment.RequestPaymentScreen
import com.lightspark.androidwalletdemo.requestpayment.RequestPaymentViewModel
import com.lightspark.androidwalletdemo.sendpayment.SendPaymentScreen
import com.lightspark.androidwalletdemo.sendpayment.SendPaymentViewModel
import com.lightspark.androidwalletdemo.ui.LoadingPage
import com.lightspark.androidwalletdemo.ui.theme.LightsparkTheme
import com.lightspark.androidwalletdemo.ui.theme.Success
import com.lightspark.androidwalletdemo.wallet.WalletDashboardView
import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.wallet.graphql.WalletDashboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val EXTRA_AUTH_FLOW = "isAuthFlow"
private const val EXTRA_AUTH_CANCELED = "authCanceled"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            Log.i("MainActivity", "Permission granted")
        } else {
            Log.w("MainActivity", "Permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val walletDashboardData by viewModel.walletDashboardData.collectAsState()
            val tokenState by viewModel.tokenState.collectAsState()

            LightsparkTheme {
                when (tokenState) {
                    is Lce.Loading -> LoadingPage()
                    is Lce.Error -> {
                        Log.e(
                            "MainActivity",
                            "Error: ${(tokenState as Lce.Error).exception?.message}",
                        )
                        LoadingPage()
                    }

                    is Lce.Content -> {
                        val token = (tokenState as Lce.Content).data
                        MainAppView(
                            navController,
                            walletDashboardData,
                            if (token == AuthState.NO_TOKEN) Screen.Settings.route else Screen.Wallet.route,
                        )
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun MainAppView(
        navController: NavHostController,
        walletDashboardData: Lce<WalletDashboard>,
        startRoute: String,
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(key1 = null) {
            viewModel.authStatusChange.collect {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        visuals = SnackbarVisualsWithError(it.message, it.isError),
                    )
                }
            }
        }

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    val isError = (data.visuals as? SnackbarVisualsWithError)?.isError ?: false

                    Snackbar(modifier = Modifier.fillMaxWidth(0.9f)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            if (isError) {
                                Icon(
                                    Icons.Filled.Warning,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                )
                            } else {
                                Icon(
                                    Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                )
                            }
                            Text(data.visuals.message)
                        }
                    }
                }
            },
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    val navItems = listOf(Screen.Settings, Screen.Wallet)
                    navItems.forEach { screen ->
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onSurface,
                                selectedTextColor = Color.Black,
                                unselectedIconColor = MaterialTheme.colorScheme.onBackground.copy(
                                    alpha = 0.4f,
                                ),
                                unselectedTextColor = Color.Green,
                                indicatorColor = Success.copy(alpha = 0.2f)
                                    .compositeOver(MaterialTheme.colorScheme.background),
                            ),
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            },
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = startRoute,
                Modifier.padding(innerPadding),
            ) {
                composable(Screen.Settings.route) {
                    val tokenState by viewModel.tokenState.collectAsState()
                    val prefs by viewModel.preferences.collectAsState()
                    val savedCredentials by viewModel.jwtInfo.collectAsState()
                    AuthScreen(
                        prefs = prefs,
                        savedCredentials = savedCredentials,
                        isLoading = tokenState is Lce.Loading,
                        modifier = Modifier.fillMaxSize(),
                        onSubmit = viewModel::onApiTokenInfoSubmitted,
                        onServerEnvironmentChange = viewModel::onServerEnvironmentSelected,
                    )
                }
                composable(Screen.Wallet.route) {
                    LaunchedEffect(true) { viewModel.refreshWalletData() }
                    val unlockStatus by viewModel.walletUnlockStatus.collectAsState()
                    WalletDashboardView(
                        walletData = walletDashboardData,
                        walletUnlockStatus = unlockStatus,
                        navController = navController,
                        modifier = Modifier.fillMaxSize(),
                        onRefreshData = viewModel::refreshWalletData,
                        onUnlockRequest = viewModel::unlockWallet,
                        onDeployWallet = viewModel::deployWallet,
                        onInitializeWallet = viewModel::initializeWallet,
                        attemptKeyStoreUnlock = viewModel::attemptKeyStoreUnlock,
                    )
                }
                composable(Screen.SendPayment.route) {
                    requestCameraPermission()
                    val viewModel: SendPaymentViewModel = hiltViewModel()
                    val uiState by viewModel.uiState.collectAsState()
                    SendPaymentScreen(
                        uiState = uiState,
                        onQrCodeRecognized = viewModel::onQrCodeRecognized,
                        onManualAddressEntryTapped = viewModel::onManualAddressEntryTapped,
                        onInvoiceManuallyEntered = viewModel::onInvoiceManuallyEntered,
                        onPaymentSendTapped = viewModel::onPaymentSendTapped,
                    )
                }
                composable(Screen.RequestPayment.route) {
                    val viewModel: RequestPaymentViewModel = hiltViewModel()
                    val uiState by viewModel.uiState.collectAsState()
                    val clipboardManager = LocalClipboardManager.current
                    RequestPaymentScreen(
                        uiState = uiState,
                        createInvoice = viewModel::createInvoice,
                        onCopy = { clipboardManager.setText(AnnotatedString(it)) },
                        onShare = {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, it)
                                type = "text/plain"
                            }
                            startActivity(shareIntent)
                        },
                    )
                }
            }
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("kilo", "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA,
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}

private class SnackbarVisualsWithError(
    override val message: String,
    val isError: Boolean,
) : SnackbarVisuals {
    override val actionLabel: String
        get() = ""
    override val withDismissAction: Boolean
        get() = true
    override val duration: SnackbarDuration
        get() = SnackbarDuration.Short
}
