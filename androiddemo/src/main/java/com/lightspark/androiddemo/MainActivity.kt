package com.lightspark.androiddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lightspark.androiddemo.dashboard.DashboardView
import com.lightspark.androiddemo.ui.theme.LightsparkTheme

class MainActivity : ComponentActivity() {
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dashboardData by viewModel.dashboardData.collectAsState()
            LightsparkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    dashboardData?.let {
                        DashboardView(
                            dashboardData = it,
                            modifier = Modifier.fillMaxSize()
                        )
                    } ?: LoadingView()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshDashboard()
    }
}

@Composable
fun LoadingView() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "Loading...")
    }
}
