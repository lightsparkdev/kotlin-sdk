package com.lightspark.androidwalletdemo.sendpayment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightspark.androidwalletdemo.util.CurrencyAmountArg
import com.lightspark.androidwalletdemo.util.currencyAmountSats
import com.lightspark.androidwalletdemo.util.zeroCurrencyAmount
import com.lightspark.androidwalletdemo.util.zeroCurrencyAmountArg
import com.lightspark.androidwalletdemo.wallet.PaymentRepository
import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.wallet.model.TransactionStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class SendPaymentViewModel @Inject constructor(
    private val repository: PaymentRepository,
) : ViewModel() {
    private val encodedInvoiceData = MutableStateFlow<String?>(null)

    private val inputType = MutableStateFlow(InputType.SCAN_QR)

    private val sendPaymentPressed = MutableSharedFlow<Unit>(
        onBufferOverflow = BufferOverflow.DROP_LATEST,
        extraBufferCapacity = 1,
    )

    private val paymentAmountFlow = MutableStateFlow(zeroCurrencyAmount())

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
                is Lce.Content -> {
                    when (it.data.status) {
                        TransactionStatus.PENDING -> PaymentStatus.PENDING
                        TransactionStatus.SUCCESS -> PaymentStatus.SUCCESS
                        else -> {
                            Log.e("SendPaymentViewModel", "Error sending payment")
                            PaymentStatus.FAILURE
                        }
                    }
                }
                is Lce.Error -> {
                    Log.e("SendPaymentViewModel", "Error sending payment", it.exception)
                    PaymentStatus.FAILURE
                }

                is Lce.Loading -> PaymentStatus.PENDING
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            PaymentStatus.NOT_STARTED,
        )

    private val decodedInvoice = encodedInvoiceData.flatMapLatest {
        it?.let { repository.decodeInvoice(it) } ?: flowOf(Lce.Content(null))
    }
        .onEach {
            (it as? Lce.Content)?.let { invoiceData ->
                invoiceData.data?.amount?.let { decodedInvoiceAmount ->
                    // Default to paying 10 sats for 0 amount invoices.
                    val amount = if (decodedInvoiceAmount.originalValue > 0) decodedInvoiceAmount else currencyAmountSats(10)
                    paymentAmountFlow.tryEmit(amount)
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
            inputType,
        ) { decodedInvoiceLce, paymentStatus, paymentAmount, paymentInputType ->
            when (decodedInvoiceLce) {
                is Lce.Content -> {
                    val paymentRequest = decodedInvoiceLce.data
                    Lce.Content(
                        SendPaymentUiState(
                            paymentInputType,
                            paymentRequest?.memo,
                            CurrencyAmountArg(
                                paymentAmount.preferredCurrencyValueApprox.toLong(),
                                paymentAmount.preferredCurrencyUnit,
                            ),
                            paymentStatus,
                        ),
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
                        zeroCurrencyAmountArg(),
                        PaymentStatus.NOT_STARTED,
                    ),
                ),
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
