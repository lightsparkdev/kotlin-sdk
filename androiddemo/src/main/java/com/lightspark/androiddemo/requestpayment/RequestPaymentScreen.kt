package com.lightspark.androiddemo.requestpayment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androiddemo.R
import com.lightspark.androiddemo.ui.LoadingPage
import com.lightspark.androiddemo.ui.theme.LightsparkTheme
import com.lightspark.androiddemo.util.displayString
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.composeqr.DotShape
import com.lightspark.composeqr.QrCodeColors
import com.lightspark.composeqr.QrCodeView
import com.lightspark.sdk.Lce
import com.lightspark.sdk.model.CurrencyAmount

@Composable
fun RequestPaymentScreen(
    uiState: Lce<RequestPaymentsUiState>,
    modifier: Modifier = Modifier,
    createInvoice: ((CurrencyAmount) -> Unit)? = null,
    onShare: ((encodedInvoice: String) -> Unit)? = null,
    onCopy: ((encodedInvoice: String) -> Unit)? = null
) {
    when (uiState) {
        is Lce.Loading -> LoadingPage()
        is Lce.Content -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                QrCodeContainer(
                    data = uiState.data.encodedQrData,
                    walletAddress = uiState.data.walletAddress,
                    invoiceAmount = uiState.data.invoiceAmount,
                    onAddAmount = {
                        createInvoice?.invoke(CurrencyAmount(1000, CurrencyUnit.SATOSHI))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 500.dp)
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                ActionButtonRow(onCopy, uiState, onShare)
            }
        }
        is Lce.Error -> {
            Text(
                text = "Error: ${uiState.exception}",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun ActionButtonRow(
    onCopy: ((encodedInvoice: String) -> Unit)?,
    uiState: Lce.Content<RequestPaymentsUiState>,
    onShare: ((encodedInvoice: String) -> Unit)?
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onCopy?.invoke(uiState.data.encodedQrData) }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Copy",
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp)
            )
            Text(text = "Copy")
        }
        Button(
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onShare?.invoke(uiState.data.encodedQrData) }) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Share",
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp)
            )
            Text(text = "Share")
        }
    }
}

@Composable
fun QrCodeContainer(
    data: String,
    walletAddress: String,
    invoiceAmount: CurrencyAmount?,
    modifier: Modifier = Modifier,
    onAddAmount: (() -> Unit)? = null
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Lightning",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            QrCodeView(
                data = data,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(16.dp),
                colors = QrCodeColors(
                    background = MaterialTheme.colorScheme.surfaceVariant,
                    foreground = MaterialTheme.colorScheme.onSurface
                ),
                dotShape = DotShape.Circle
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lightspark_logo),
                        contentDescription = "Check",
                        modifier = Modifier.fillMaxSize(fraction = 0.5f),
                        tint = Color.Black
                    )
                }
            }
            Text(
                text = data.truncateMiddle(),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (invoiceAmount != null && invoiceAmount.amount > 0) {
                Text(
                    text = invoiceAmount.displayString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            } else {
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
                    onClick = onAddAmount ?: {},
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Text(text = "Add Amount")
                }
            }
        }
    }
}

private fun String.truncateMiddle(): String {
    if (this.length <= 40) return this
    return "${substring(0, 20)}...${substring(this.length - 20, this.length)}"
}

@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun QrCodeContainerPreview() {
    LightsparkTheme {
        RequestPaymentScreen(
            Lce.Content(
                RequestPaymentsUiState(
                    encodedQrData = "lightspark://pay?amount=100&currency=USD&message=Hello%20World",
                    walletAddress = "bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh",
//                    invoiceAmount = CurrencyAmount(100, CurrencyUnit.SATOSHI)
                )
            )
        )
    }
}