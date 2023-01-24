package com.lightspark.androiddemo.sendpayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightspark.androiddemo.wallet.PaymentRepository
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.sdk.Lce
import com.lightspark.sdk.model.CurrencyAmount
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class SendPaymentViewModel(
    private val repository: PaymentRepository = PaymentRepository()
) : ViewModel() {
    private val encodedScannedQrData =
        MutableSharedFlow<String>(onBufferOverflow = BufferOverflow.DROP_LATEST, replay = 1)

    val uiState: StateFlow<Lce<SendPaymentUiState>> get() = _uiState
    private val _uiState = encodedScannedQrData.flatMapLatest {
        repository.decodeInvoice(it)
    }.map { decodedInvoiceLce ->
        when (decodedInvoiceLce) {
            is Lce.Content -> {
                val paymentRequest = decodedInvoiceLce.data
                Lce.Content(
                    SendPaymentUiState(
                        InputType.MANUAL_ENTRY,
                        // TODO: Should this be an address instead?
                        paymentRequest.invoice_data_destination.onLightsparkNode?.lightspark_node_display_name
                            ?: "",
                        CurrencyAmount(
                            paymentRequest.invoice_data_amount.currency_amount_value,
                            paymentRequest.invoice_data_amount.currency_amount_unit
                        ),
                        PaymentStatus.NOT_STARTED
                    )
                )
            }
            is Lce.Error -> Lce.Error(decodedInvoiceLce.exception)
            is Lce.Loading -> Lce.Loading
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            Lce.Content(
                SendPaymentUiState(
                    InputType.SCAN_QR,
                    null,
                    CurrencyAmount(0, CurrencyUnit.SATOSHI),
                    PaymentStatus.NOT_STARTED
                )
            )
        )


    fun onQrCodeRecognized(encodedData: String) {
        encodedScannedQrData.tryEmit(encodedData)
    }
}