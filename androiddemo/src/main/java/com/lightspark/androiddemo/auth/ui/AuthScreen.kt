package com.lightspark.androiddemo.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.ui.theme.Success

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    oAuthIsAuthorized: Boolean = false,
    onSubmit: (tokenId: String, tokenSecret: String, defaultWalletId: String?) -> Unit = { _, _, _ -> },
    onOAuthRequest: () -> Unit = {},
) {
    var tokenId by remember { mutableStateOf("") }
    var tokenSecret by remember { mutableStateOf("") }
    var nodeID by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (oAuthIsAuthorized) {
            Text(
                "You're successfully authorized with oauth! You can overwrite that by logging in again with the form below or via OAuth again.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(color = Success),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 16.dp)
            )
        } else {
            Text(
                "We need some quick info from your Lightspark dashboard to get you started. Enter it below or use the OAuth login instead.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 16.dp)
            )
        }
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onOAuthRequest() },
            modifier = Modifier
                .fillMaxWidth(0.8f)
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
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        TextField(
            label = { Text("Token Secret", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Enter token secret") },
            value = tokenSecret,
            onValueChange = { tokenSecret = it.trim() },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        TextField(
            label = { Text("Node ID for Wallet", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Enter wallet node ID") },
            value = nodeID,
            onValueChange = { nodeID = it.trim() },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onSubmit(tokenId, tokenSecret, nodeID.takeIf { it.isNotBlank() }) },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Submit")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    LightsparkTheme {
        AuthScreen()
    }
}
