package com.lightspark.androiddemo.auth.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.lightspark.androiddemo.ui.theme.LightsparkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodePasswordDialog(
    open: Boolean = false,
    nodeName: String = "a node",
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
            Text("Unlock $nodeName")
        },
        text = {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                placeholder = { Text("Enter node password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.secondary,
                ),
            )
        },
        icon = { Icon(Icons.Default.Lock, "Lock") },
    )
}

@Preview(showBackground = true)
@Composable
fun NodePasswordDialogPreview() {
    LightsparkTheme {
        NodePasswordDialog(open = true)
    }
}
