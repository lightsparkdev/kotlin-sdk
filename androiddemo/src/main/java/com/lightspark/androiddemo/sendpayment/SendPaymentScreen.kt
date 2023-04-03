package com.lightspark.androiddemo.sendpayment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androiddemo.ui.LoadingPage
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.ui.theme.SurfaceDarker
import com.lightspark.androiddemo.util.CurrencyAmountArg
import com.lightspark.androiddemo.util.displayString
import com.lightspark.sdk.Lce
import com.lightspark.sdk.model.CurrencyUnit

@Composable
fun SendPaymentScreen(
    uiState: Lce<SendPaymentUiState>,
    modifier: Modifier = Modifier,
    onPaymentSendTapped: (() -> Unit)? = null,
    onManualAddressEntryTapped: (() -> Unit)? = null,
    onInvoiceManuallyEntered: ((String) -> Unit)? = null,
    onQrCodeRecognized: ((encodedData: String) -> Unit)? = null,
) {
    when (uiState) {
        is Lce.Content -> {
            when (uiState.data.paymentStatus) {
                PaymentStatus.SUCCESS -> SuccessScreen()
                PaymentStatus.FAILURE -> FailedScreen(uiState.data)
                PaymentStatus.NOT_STARTED,
                PaymentStatus.PENDING,
                -> when (uiState.data.inputType) {
                    InputType.SCAN_QR -> InvoiceQrScanner(
                        onInvoiceScanned = onQrCodeRecognized,
                        onManualEntryRequest = onManualAddressEntryTapped,
                    )
                    InputType.MANUAL_ENTRY -> PaymentEntryScreen(
                        uiState = uiState.data,
                        modifier = modifier,
                        onPaymentSendTapped = onPaymentSendTapped,
                        onInvoiceManuallyEntered = onInvoiceManuallyEntered,
                    )
                }
            }
        }
        is Lce.Error -> {
            Text(
                text = "Error: ${uiState.exception}",
                style = MaterialTheme.typography.displayLarge,
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
    onInvoiceManuallyEntered: ((String) -> Unit)? = null,
) {
    if (uiState.destinationAddress == null) {
        ManualInvoiceEntryScreen(modifier = modifier, onInvoiceManuallyEntered)
    } else {
        AmountEntryScreen(
            uiState = uiState,
            modifier = modifier,
            onPaymentSendTapped = onPaymentSendTapped,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualInvoiceEntryScreen(
    modifier: Modifier = Modifier,
    onInvoiceManuallyEntered: ((String) -> Unit)? = null,
) {
    var invoiceText by remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Send to",
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
        )
        // TODO: This could use some nice styling and focus handling.
        TextField(
            value = invoiceText,
            onValueChange = { invoiceText = it },
            label = { Text(text = "Invoice") },
            placeholder = { Text(text = "Enter invoice") },
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onInvoiceManuallyEntered?.invoke(invoiceText) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
        ) {
            Text(text = "Next")
        }
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(50))
                .background(SurfaceDarker)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = uiState.destinationAddress ?: "No address",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        CurrencyAmountInputField(amount = uiState.amount)
        val isPaymentLoading = uiState.paymentStatus == PaymentStatus.PENDING
        Button(
            onClick = { onPaymentSendTapped?.invoke() },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isPaymentLoading) Color.White else Color.Black,
                contentColor = if (isPaymentLoading) Color.Black else Color.White,
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = SolidColor(Color.Black),
                width = 2.dp,
            ),
        ) {
            if (isPaymentLoading) {
                CircularProgressIndicator(
                    color = Color.Black,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .size(24.dp)
                        .offset(y = (-2).dp)
                        .padding(end = 8.dp),
                )
                Text(text = "Sending")
            } else {
                Text(text = "Send Bitcoin")
            }
        }
    }
}

@Composable
private fun CurrencyAmountInputField(amount: CurrencyAmountArg, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Text(
            text = "\$XX,XXX.XX USD",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = amount.displayString(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
fun SuccessScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = "Success",
            tint = Color.Green,
            modifier = Modifier.size(64.dp),
        )
        Text(
            text = "Payment Sent!",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun FailedScreen(uiState: SendPaymentUiState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = "Failed",
            tint = Color.Red,
            modifier = Modifier.size(64.dp),
        )
        Text(
            text = "Payment failed :-(",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
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
                amount = CurrencyAmountArg(100000, CurrencyUnit.SATOSHI),
                inputType = InputType.MANUAL_ENTRY,
                paymentStatus = PaymentStatus.PENDING,
            ),
        )
    }
}
