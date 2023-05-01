package com.lightspark.androidwalletdemo.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androidwalletdemo.ui.theme.LightsparkTheme

@Composable
fun Separator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(1.dp)
            .background(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
    )
}

@Preview
@Composable
fun SeparatorPreview() {
    LightsparkTheme {
        Box(modifier = Modifier.height(100.dp).background(MaterialTheme.colorScheme.surface)) {
            Separator()
        }
    }
}
