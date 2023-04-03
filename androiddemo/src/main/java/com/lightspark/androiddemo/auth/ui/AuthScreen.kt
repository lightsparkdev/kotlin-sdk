package com.lightspark.androiddemo.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androiddemo.settings.SavedPrefs
import com.lightspark.androiddemo.ui.LightsparkDropdown
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.ui.theme.Success
import com.lightspark.sdk.model.BitcoinNetwork
import com.lightspark.sdk.requester.ServerEnvironment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    prefs: SavedPrefs,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    oAuthIsAuthorized: Boolean = false,
    onSubmit: (tokenId: String, tokenSecret: String) -> Unit = { _, _ -> },
    onOAuthRequest: () -> Unit = {},
    onBitcoinNetworkChange: (BitcoinNetwork) -> Unit = {},
    onServerEnvironmentChange: (ServerEnvironment) -> Unit = {},
) {
    var tokenId by remember { mutableStateOf("") }
    var tokenSecret by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (oAuthIsAuthorized) {
            Text(
                "You're successfully authorized with oauth! You can overwrite that by logging in again with the " +
                    "form below or via OAuth again.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(color = Success),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 16.dp),
            )
        } else {
            Text(
                "We need some quick info from your Lightspark dashboard to get you started. Enter it below or use " +
                    "the OAuth login instead.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 16.dp),
            )
        }
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onOAuthRequest() },
            modifier = Modifier
                .fillMaxWidth(0.8f),
        ) {
            Text("Use OAuth Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            label = { Text("Token ID", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Enter token ID") },
            value = tokenId,
            onValueChange = { tokenId = it.trim() },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f),
        )
        TextField(
            label = { Text("Token Secret", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Enter token secret") },
            value = tokenSecret,
            onValueChange = { tokenSecret = it.trim() },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onSubmit(tokenId, tokenSecret) },
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
        Spacer(modifier = Modifier.height(24.dp))
        Divider()
        Text(
            text = "Bitcoin network",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        LightsparkDropdown(
            items = listOf(
                BitcoinNetwork.REGTEST,
                BitcoinNetwork.TESTNET,
                BitcoinNetwork.MAINNET,
            ).map { it.name },
            selected = prefs.bitcoinNetwork.name,
            onSelected = { onBitcoinNetworkChange(BitcoinNetwork.valueOf(it)) },
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
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    LightsparkTheme {
        AuthScreen(SavedPrefs.DEFAULT)
    }
}
