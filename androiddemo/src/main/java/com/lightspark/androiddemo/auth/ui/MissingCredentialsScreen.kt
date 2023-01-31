package com.lightspark.androiddemo.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lightspark.androiddemo.navigation.Screen
import com.lightspark.androiddemo.ui.theme.LightsparkTheme

@Composable
fun MissingCredentialsScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "It looks like you haven't set up your account credentials yet. Head over to settings to set that up.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 16.dp)
        )
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { navController?.navigate(Screen.Settings.route) },
            modifier = Modifier
                .offset(y = 32.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text("Settings")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MissingCredentialsScreenPreview() {
    LightsparkTheme {
        MissingCredentialsScreen()
    }
}
