package com.lightspark.androidwalletdemo.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androidwalletdemo.ui.theme.LightsparkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeKeyDialog(
    open: Boolean = false,
    onDismiss: () -> Unit = {},
    onSubmit: (String) -> Unit = {},
) {
    var password by remember { mutableStateOf("") }
    if (!open) return
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onSubmit(password)
                    onDismiss()
                    password = ""
                },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onBackground),
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onBackground),
            ) {
                Text("Cancel")
            }
        },
        title = {
            Text("Unlock your wallet")
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    "It seems like the private key for this wallet wasn't saved in the Android KeyStore. " +
                        "Please enter your signing private key PEM below to unlock your wallet. If you don't have " +
                        "it, you should probably generate a new wallet :-(.",
                    modifier = Modifier.padding(bottom = 12.dp),
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Signing Private Key PEM") },
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = { Text("Enter signing private key") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        cursorColor = MaterialTheme.colorScheme.secondary,
                    ),
                )
            }
        },
        icon = { Icon(Icons.Default.Lock, "Lock") },
    )
}

@Preview(showBackground = true)
@Composable
fun NodePasswordDialogPreview() {
    LightsparkTheme {
        NodeKeyDialog(open = true)
    }
}
