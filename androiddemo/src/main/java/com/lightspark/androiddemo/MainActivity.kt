package com.lightspark.androiddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lightspark.androiddemo.dashboard.DashboardView
import com.lightspark.androiddemo.navigation.Screen
import com.lightspark.androiddemo.profile.ProfileScreen
import com.lightspark.androiddemo.requestpayment.RequestPaymentScreen
import com.lightspark.androiddemo.requestpayment.RequestPaymentViewModel
import com.lightspark.androiddemo.sendpayment.SendPaymentScreen
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.ui.theme.Success
import com.lightspark.androiddemo.wallet.WalletDashboardView

class MainActivity : ComponentActivity() {
    private val viewModel = MainViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val advancedDashboardData by viewModel.advancedDashboardData.collectAsState()
            val walletDashboardData by viewModel.walletDashboardData.collectAsState()

            LightsparkTheme {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {
                        NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            val navItems = listOf(Screen.Settings, Screen.Wallet, Screen.Account)
                            navItems.forEach { screen ->
                                NavigationBarItem(
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onSurface,
                                        selectedTextColor = Color.Black, //MaterialTheme.colorScheme.onSurface,
                                        unselectedIconColor = MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.4f
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
                                    }
                                )
                            }
                        }
                    }) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Wallet.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Settings.route) {
                            LaunchedEffect(true) { viewModel.refreshAdvancedDashboardData() }
                            DashboardView(
                                dashboardData = advancedDashboardData,
                                modifier = Modifier.fillMaxSize(),
                                onNodeKeyRecoverTap = viewModel::requestKeyRecovery,
                            )
                        }
                        composable(Screen.Wallet.route) {
                            WalletDashboardView(
                                walletData = walletDashboardData,
                                navController = navController,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        composable(Screen.Account.route) {
                            ProfileScreen()
                        }
                        composable(Screen.SendPayment.route) {
                            SendPaymentScreen()
                        }
                        composable(Screen.RequestPayment.route) {
                            val viewModel: RequestPaymentViewModel = viewModel(
                                key = "nodeId_$NODE_ID",
                                factory = viewModelFactory {
                                    initializer { RequestPaymentViewModel(NODE_ID) }
                                }
                            )
                            val uiState by viewModel.uiState.collectAsState()
                            RequestPaymentScreen(
                                uiState = uiState,
                                createInvoice = viewModel::createInvoice
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshWalletData(NODE_ID)
    }
}
