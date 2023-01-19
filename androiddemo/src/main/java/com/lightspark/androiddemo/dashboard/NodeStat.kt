package com.lightspark.androiddemo.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androiddemo.ui.theme.LightsparkTheme

@Composable
fun NodeStat(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(8.dp),
    ) {
        Text(text = title, style = MaterialTheme.typography.labelSmall)
        content()
    }
}

@Composable
fun TextNodeStat(title: String, content: String, modifier: Modifier = Modifier) {
    NodeStat(title = title, modifier = modifier) {
        NodeStatTextContent(text = content)
    }
}

@Composable
fun NodeStatTextContent(text: String, modifier: Modifier = Modifier) {
    Text(text = text, style = MaterialTheme.typography.titleMedium, modifier = modifier)
}

@Preview
@Composable
fun NodeStatPreview() {
    LightsparkTheme {
        TextNodeStat(title = "Uptime", content = "99%")
    }
}