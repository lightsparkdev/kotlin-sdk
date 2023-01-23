package com.lightspark.androiddemo.qrcode

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Defines the color palette for a QR code.
 */
data class QrCodeColors(
    val background: Color, val foreground: Color
) {
    companion object {
        @Composable
        fun default() = QrCodeColors(
            background = MaterialTheme.colorScheme.background,
            foreground = MaterialTheme.colorScheme.onBackground
        )
    }
}