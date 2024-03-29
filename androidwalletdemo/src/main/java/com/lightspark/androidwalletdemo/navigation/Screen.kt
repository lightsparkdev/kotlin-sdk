package com.lightspark.androidwalletdemo.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.lightspark.androidwalletdemo.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Settings : Screen("Settings", R.string.settings, Icons.Outlined.Settings)
    object Wallet : Screen("Wallet", R.string.wallet, Icons.Rounded.MailOutline)
    object RequestPayment : Screen("RequestPayment", R.string.request_payment, Icons.Outlined.Info)
    object SendPayment : Screen("SendPayment", R.string.send_payment, Icons.Outlined.Info)
}
