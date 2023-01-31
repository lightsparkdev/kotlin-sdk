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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onSubmit: (tokenId: String, tokenSecret: String) -> Unit = { _, _ -> }
) {
    var tokenId by remember { mutableStateOf("") }
    var tokenSecret by remember { mutableStateOf("") }

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
        Spacer(modifier = Modifier.weight(1f))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onSubmit(tokenId, tokenSecret) },
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
