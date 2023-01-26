package com.lightspark.androiddemo.sendpayment

import android.util.Log
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
    private val encodedInvoiceData = MutableStateFlow<String?>(null)

    private val inputType = MutableStateFlow(InputType.SCAN_QR)

    private val sendPaymentPressed = MutableSharedFlow<Unit>(
        onBufferOverflow = BufferOverflow.DROP_LATEST,
        extraBufferCapacity = 1
    )

    private val paymentAmountFlow = MutableStateFlow(CurrencyAmount(0, CurrencyUnit.SATOSHI))

    private val sendPaymentResult =
        combine(
            sendPaymentPressed,
            encodedInvoiceData,
        ) { _, encodedInvoiceData -> encodedInvoiceData }.flatMapLatest { encodedInvoiceData ->
            if (encodedInvoiceData == null) {
                flowOf(null)
            } else {
                repository.payInvoice(encodedInvoiceData)
            }
        }.filterNotNull().map {
            when (it) {
                is Lce.Content -> PaymentStatus.SUCCESS
                is Lce.Error -> {
                    Log.e("SendPaymentViewModel", "Error sending payment", it.exception)
                    PaymentStatus.FAILURE
                }
                is Lce.Loading -> PaymentStatus.PENDING
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            PaymentStatus.NOT_STARTED
        )

    private val decodedInvoice = encodedInvoiceData.flatMapLatest {
        it?.let { repository.decodeInvoice(it) } ?: flowOf(Lce.Content(null))
    }
        .onEach {
            (it as? Lce.Content)?.let { invoiceData ->
                invoiceData.data?.invoice_data_amount?.let { decodedInvoiceAmount ->
                    paymentAmountFlow.tryEmit(
                        CurrencyAmount(
                            decodedInvoiceAmount.currency_amount_value,
                            decodedInvoiceAmount.currency_amount_unit
                        )
                    )
                }
            }
        }
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    val uiState: StateFlow<Lce<SendPaymentUiState>> get() = _uiState
    private val _uiState =
        combine(
            decodedInvoice,
            sendPaymentResult,
            paymentAmountFlow,
            inputType
        ) { decodedInvoiceLce, paymentStatus, paymentAmount, paymentInputType ->
            when (decodedInvoiceLce) {
                is Lce.Content -> {
                    val paymentRequest = decodedInvoiceLce.data
                    Lce.Content(
                        SendPaymentUiState(
                            paymentInputType,
                            paymentRequest?.invoice_data_destination?.onLightsparkNode?.lightspark_node_display_name,
                            paymentAmount,
                            paymentStatus
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
        encodedInvoiceData.tryEmit(encodedData)
        inputType.tryEmit(InputType.MANUAL_ENTRY)
    }

    fun onPaymentSendTapped() {
        sendPaymentPressed.tryEmit(Unit)
    }

    fun onManualAddressEntryTapped() {
        inputType.tryEmit(InputType.MANUAL_ENTRY)
    }

    fun onInvoiceManuallyEntered(encodedData: String) {
        encodedInvoiceData.tryEmit(encodedData)
    }
}