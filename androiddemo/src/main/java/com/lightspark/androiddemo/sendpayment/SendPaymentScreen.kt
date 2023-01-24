package com.lightspark.androiddemo.sendpayment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.lightspark.androiddemo.ui.LoadingPage
import com.lightspark.sdk.Lce

@Composable
fun SendPaymentScreen(
    uiState: Lce<SendPaymentUiState>,
    modifier: Modifier = Modifier,
    onPaymentSendTapped: (() -> Unit)? = null,
    onQrCodeRecognized: ((encodedData: String) -> Unit)? = null,
) {
    when (uiState) {
        is Lce.Content -> {
            when (uiState.data.inputType) {
                InputType.SCAN_QR -> InvoiceQrScanner(onInvoiceScanned = onQrCodeRecognized)
                InputType.MANUAL_ENTRY -> PaymentEntryScreen(
                    uiState = uiState.data,
                    onPaymentSendTapped = onPaymentSendTapped
                )
            }
        }
        is Lce.Error -> {
            Text(
                text = "Error: ${uiState.exception}",
                style = MaterialTheme.typography.displayLarge
            )
        }
        is Lce.Loading -> LoadingPage()
    }
}

@Composable
fun PaymentEntryScreen(
    uiState: SendPaymentUiState,
    onPaymentSendTapped: (() -> Unit)? = null,
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Payment entry coming soon!",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
    }
}