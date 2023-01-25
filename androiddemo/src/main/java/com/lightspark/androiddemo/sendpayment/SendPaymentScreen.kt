package com.lightspark.androiddemo.sendpayment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androiddemo.ui.LoadingPage
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.ui.theme.SurfaceDarker
import com.lightspark.androiddemo.util.displayString
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.sdk.Lce
import com.lightspark.sdk.model.CurrencyAmount

@Composable
fun SendPaymentScreen(
    uiState: Lce<SendPaymentUiState>,
    modifier: Modifier = Modifier,
    onPaymentSendTapped: (() -> Unit)? = null,
    onQrCodeRecognized: ((encodedData: String) -> Unit)? = null,
) {
    when (uiState) {
        is Lce.Content -> {
            when (uiState.data.paymentStatus) {
                PaymentStatus.SUCCESS -> SuccessScreen()
                PaymentStatus.FAILURE -> FailedScreen(uiState.data)
                PaymentStatus.NOT_STARTED,
                PaymentStatus.PENDING -> when (uiState.data.inputType) {
                    InputType.SCAN_QR -> InvoiceQrScanner(onInvoiceScanned = onQrCodeRecognized)
                    InputType.MANUAL_ENTRY -> PaymentEntryScreen(
                        uiState = uiState.data,
                        modifier = modifier,
                        onPaymentSendTapped = onPaymentSendTapped,
                    )
                }
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
    modifier: Modifier = Modifier,
    onPaymentSendTapped: (() -> Unit)? = null,
) {
    if (uiState.destinationAddress == null) {
        AddressEntryScreen(modifier = modifier)
    } else {
        AmountEntryScreen(
            uiState = uiState,
            modifier = modifier,
            onPaymentSendTapped = onPaymentSendTapped
        )
    }
}

@Composable
fun AddressEntryScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Address Entry Screen goes here",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AmountEntryScreen(
    uiState: SendPaymentUiState,
    modifier: Modifier = Modifier,
    onPaymentSendTapped: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(50))
                .background(SurfaceDarker)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = uiState.destinationAddress ?: "No address",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        CurrenyAmountInput(amount = uiState.amount)
        Button(
            onClick = { onPaymentSendTapped?.invoke() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
        ) {
            Text(text = "Send Bitcoin")
        }
    }
}

@Composable
private fun CurrenyAmountInput(amount: CurrencyAmount, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "\$XX,XXX.XX USD",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = amount.displayString(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun SuccessScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = "Success",
            tint = Color.Green,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "Payment Sent!",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FailedScreen(uiState: SendPaymentUiState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = "Failed",
            tint = Color.Red,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "Payment failed :-(",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SendPaymentScreenPreview() {
    LightsparkTheme {
        AmountEntryScreen(
            uiState = SendPaymentUiState(
                destinationAddress = "bc1q9qyqgj5xq5vqpwsp5kkq9zslawv9f0hnw3v3h7",
                amount = CurrencyAmount(100000, CurrencyUnit.SATOSHI),
                inputType = InputType.MANUAL_ENTRY,
                paymentStatus = PaymentStatus.NOT_STARTED
            )
        )
    }
}
