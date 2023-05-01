package com.lightspark.androidwalletdemo.requestpayment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lightspark.androidwalletdemo.R
import com.lightspark.androidwalletdemo.ui.LoadingPage
import com.lightspark.androidwalletdemo.ui.theme.LightsparkTheme
import com.lightspark.androidwalletdemo.util.CurrencyAmountArg
import com.lightspark.androidwalletdemo.util.displayString
import com.lightspark.composeqr.DotShape
import com.lightspark.composeqr.QrCodeColors
import com.lightspark.composeqr.QrCodeView
import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.wallet.model.CurrencyUnit

@Composable
fun RequestPaymentScreen(
    uiState: Lce<RequestPaymentUiState>,
    modifier: Modifier = Modifier,
    createInvoice: ((CurrencyAmountArg) -> Unit)? = null,
    onShare: ((encodedInvoice: String) -> Unit)? = null,
    onCopy: ((encodedInvoice: String) -> Unit)? = null,
) {
    var isEnteringAmount by remember { mutableStateOf(false) }

    if (isEnteringAmount) {
        return RequestPaymentAmountScreen(
            onAmountEntered = { amount ->
                createInvoice?.invoke(amount)
                isEnteringAmount = false
            },
            onBack = { isEnteringAmount = false },
            modifier = modifier,
        )
    }

    when (uiState) {
        is Lce.Loading -> LoadingPage()
        is Lce.Content -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                QrCodeContainer(
                    data = uiState.data.encodedQrData,
                    invoiceAmount = uiState.data.invoiceAmount,
                    onAddAmount = {
                        isEnteringAmount = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 500.dp)
                        .padding(16.dp),
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
                    .padding(16.dp),
            )
        }
    }
}

@Composable
fun RequestPaymentAmountScreen(
    onAmountEntered: (CurrencyAmountArg) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var amount by remember { mutableStateOf(CurrencyAmountArg(0, CurrencyUnit.SATOSHI)) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = androidx.appcompat.R.drawable.abc_ic_ab_back_material),
                contentDescription = "back",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onBack() },
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BasicTextField(
            value = amount.value.toString() + " SATs",
            onValueChange = { amount = CurrencyAmountArg(it.removeSuffix(" SATs").toLong(), CurrencyUnit.SATOSHI) },
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
            textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center),
            singleLine = true,
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onAmountEntered(amount) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.width(150.dp),
        ) {
            Text(text = "Create Invoice")
        }
    }
}

@Composable
private fun ActionButtonRow(
    onCopy: ((encodedInvoice: String) -> Unit)?,
    uiState: Lce.Content<RequestPaymentUiState>,
    onShare: ((encodedInvoice: String) -> Unit)?,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Button(
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onCopy?.invoke(uiState.data.encodedQrData) },
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Copy",
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp),
            )
            Text(text = "Copy")
        }
        Button(
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            onClick = { onShare?.invoke(uiState.data.encodedQrData) },
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Share",
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp),
            )
            Text(text = "Share")
        }
    }
}

@Composable
fun QrCodeContainer(
    data: String,
    invoiceAmount: CurrencyAmountArg?,
    modifier: Modifier = Modifier,
    onAddAmount: (() -> Unit)? = null,
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(
                text = "Lightning",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
            QrCodeView(
                data = data,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(16.dp),
                colors = QrCodeColors(
                    background = MaterialTheme.colorScheme.surfaceVariant,
                    foreground = MaterialTheme.colorScheme.onSurface,
                ),
                dotShape = DotShape.Circle,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lightspark_logo),
                        contentDescription = "Check",
                        modifier = Modifier.fillMaxSize(fraction = 0.5f),
                        tint = Color.Black,
                    )
                }
            }
            Text(
                text = data.truncateMiddle(),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp),
            )
            if (invoiceAmount != null && invoiceAmount.value > 0) {
                Text(
                    text = invoiceAmount.displayString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
            } else {
                Button(
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
                    onClick = onAddAmount ?: {},
                    border = ButtonDefaults.outlinedButtonBorder,
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
                RequestPaymentUiState(
                    encodedQrData = "lightspark://pay?amount=100&currency=USD&message=Hello%20World",
//                    invoiceAmount = CurrencyAmount(100, CurrencyUnit.SATOSHI)
                ),
            ),
        )
    }
}
