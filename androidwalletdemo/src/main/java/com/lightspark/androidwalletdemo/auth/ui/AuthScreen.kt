package com.lightspark.androidwalletdemo.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androidwalletdemo.auth.SavedCredentials
import com.lightspark.androidwalletdemo.settings.SavedPrefs
import com.lightspark.androidwalletdemo.ui.theme.LightsparkTheme
import com.lightspark.androidwalletdemo.ui.theme.Success
import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.core.requester.ServerEnvironment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    prefs: SavedPrefs,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    savedCredentials: Lce<SavedCredentials?> = Lce.Loading,
    onSubmit: (accountId: String, jwt: String) -> Unit = { _, _ -> },
    onDemoLogin: (userName: String, password: String) -> Unit = { _, _ -> },
    onServerEnvironmentChange: (ServerEnvironment) -> Unit = {},
) {
    if (savedCredentials !is Lce.Content) {
        return Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }

    var accountId by remember { mutableStateOf(savedCredentials.data?.accountId ?: "") }
    var jwt by remember { mutableStateOf(savedCredentials.data?.jwt ?: "") }
    var userName by remember { mutableStateOf(savedCredentials.data?.userName ?: "") }
    var password by remember { mutableStateOf("") }
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (savedCredentials.data != null) {
            Text(
                "You're logged in with the JWT listed below. If you'd like to log in with a different account, " +
                    "just enter your new credentials below and tap \"Submit\".",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(color = Success),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 16.dp),
            )
        } else {
            Text(
                "Please enter your account credentials below to get started.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 16.dp),
            )
        }
        Divider()
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "In reality, your server will generate this JWT for the user in your own login endpoint. " +
                "For the purposes of this demo, you can generate a jwt using the site linked below. Just swap in " +
                "your account ID and pick any random change to the sub. Also add in your private key PEM where " +
                "requested.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 24.dp)
        )
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { uriHandler.openUri("https://jwt.io/#debugger-io?token=eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJodHRwczovL2FwaS5saWdodHNwYXJrLmNvbSIsInN1YiI6IjUxMWM3ZWI4LTlhZmUtNGY2OS05ODlhLThkMTExM2EzM2YzZCIsInRlc3QiOnRydWUsImlhdCI6MTUxNjIzOTAyMiwiZXhwIjoxNzk5MzkzMzYzfQ.FGUxtfnxq1JgTJkY5GkCX-v6X82Zux2FyK8LoZPqDkGagZ5NEj89XcCm9Z8ZTof04ukSRuXsuoLeXe7sJd5Ncw&publicKey=-----BEGIN%20PUBLIC%20KEY-----%0AMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEEVs%2Fo5%2BuQbTjL3chynL4wXgUg2R9%0Aq9UU8I5mEovUf86QZ7kOBIjJwqnzD1omageEHWwHdBO6B%2BdFabmdT9POxg%3D%3D%0A-----END%20PUBLIC%20KEY-----") },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth(0.8f),
        ) {
            Text("Generate JWT")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "You can also use our demo login endpoint to generate a JWT for our demo account. " +
                "Just pick any unique username and password. See the docs for more info on how this works.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 24.dp)
        )
        TextField(
            label = { Text("User name", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Pick a unique user name") },
            value = userName,
            onValueChange = { userName = it.trim() },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f),
        )
        TextField(
            label = { Text("Password", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Anything password is ok") },
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { password = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 8.dp),
        )
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onDemoLogin(userName, password) },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth(0.8f),
        ) {
            Text("Login to demo account")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "or manually enter your account ID and JWT below.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(bottom = 12.dp)
        )
        TextField(
            label = { Text("Account ID", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Enter account ID") },
            value = accountId,
            onValueChange = { accountId = it.trim() },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f),
        )
        TextField(
            label = { Text("JWT", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Enter jwt") },
            value = jwt,
            onValueChange = { jwt = it.trim() },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = "Dev",
                style = MaterialTheme.typography.labelMedium,
            )
            Switch(
                checked = prefs.environment == ServerEnvironment.PROD,
                onCheckedChange = {
                    onServerEnvironmentChange(if (it) ServerEnvironment.PROD else ServerEnvironment.DEV)
                },
            )
            Text(
                text = "Prod",
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onSubmit(accountId, jwt) },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth(0.8f),
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Submit")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    LightsparkTheme {
        AuthScreen(SavedPrefs.DEFAULT)
    }
}
