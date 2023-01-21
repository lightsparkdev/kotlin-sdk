package com.lightspark.androiddemo.requestpayment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RequestPaymentScreen(
    modifier: Modifier = Modifier,
    encodedInvoiceData: String? = null,
    requestInvoice: (() -> Unit)? = null,
    onShare: ((encodedInvoice: String) -> Unit)? = null,
    onCopy: ((encodedInvoice: String) -> Unit)? = null
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Invoices coming soon!",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
    }
}