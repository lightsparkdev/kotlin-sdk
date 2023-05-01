package com.lightspark.androidwalletdemo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androidwalletdemo.ui.theme.LightsparkTheme

@Composable
fun LightsparkDropdown(
    items: List<String>,
    selected: String,
    modifier: Modifier = Modifier,
    onSelected: (String) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(4.dp)),
    ) {
        DropdownMenuItem(
            enabled = true,
            text = { Text(selected) },
            onClick = { expanded = !expanded },
            trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, contentDescription = "select") },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(4.dp),
            ),
        )
        DropdownMenu(
            modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(selected) },
                onClick = { expanded = false },
                enabled = true,
            )
            Divider()
            items.forEach {
                if (it != selected) {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            onSelected(it)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LightsparkDropdownPreview() {
    var selected by remember { mutableStateOf("Edit") }
    LightsparkTheme {
        LightsparkDropdown(
            items = listOf("Edit", "Settings", "Send Feedback"),
            selected = selected,
            onSelected = { selected = it },
        )
    }
}
