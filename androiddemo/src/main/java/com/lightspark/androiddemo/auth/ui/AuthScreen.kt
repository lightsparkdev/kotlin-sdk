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
        Text(
            "We need some quick info from your Lightspark dashboard to get you started. Real users will use oauth instead of this flow :-p",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 16.dp)
        )
        TextField(
            label = { Text("Token ID", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Enter token ID") },
            value = tokenId,
            onValueChange = { tokenId = it.trim() },
            singleLine = true
        )
        TextField(
            label = { Text("Token Secret", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Enter token secret") },
            value = tokenSecret,
            onValueChange = { tokenSecret = it.trim() },
            singleLine = false
        )
        TextField(
            label = { Text("Node ID for Wallet", style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text("Enter wallet node ID") },
            value = nodeID,
            onValueChange = { nodeID = it.trim() },
            singleLine = false
        )
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onOAuthRequest() },
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            Text("Use OAuth Login")
        }
        if (oAuthIsAuthorized) {
            Text(
                "OAuth is authorized already! You can overwrite that by logging in again with the form above or via OAuth again.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(color = Success),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 16.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onSubmit(tokenId, tokenSecret, nodeID.takeIf { it.isNotBlank() }) },
            enabled = !isLoading,
            modifier = Modifier
                .offset(y = (-16).dp)
                .fillMaxWidth(0.8f)
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
        AuthScreen()
    }
}
