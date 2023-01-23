package com.lightspark.androiddemo.requestpayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightspark.sdk.Lce
import com.lightspark.sdk.model.CurrencyAmount
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class RequestPaymentViewModel(
    private val nodeId: String,
    private val repository: RequestPaymentRepository = RequestPaymentRepository()
) : ViewModel() {
    private val invoiceAmount = MutableStateFlow<CurrencyAmount?>(null)
    private val invoice = invoiceAmount.flatMapLatest { amount ->
        if (amount == null) {
            flowOf(Lce.Content(null))
        } else {
            repository.createInvoice(nodeId, amount)
        }
    }

    val uiState: StateFlow<Lce<RequestPaymentsUiState>> get() = _uiState
    private val _uiState = combine(
        invoice,
        repository.getWalletAddress(nodeId)
    ) { invoice, walletAddress ->
        when {
            invoice is Lce.Loading || walletAddress is Lce.Loading -> Lce.Loading
            walletAddress is Lce.Error -> Lce.Error(walletAddress.exception)
            invoice is Lce.Error -> Lce.Error(invoice.exception)
            else -> {
                invoice as Lce.Content
                walletAddress as Lce.Content
                invoice.data?.let {
                    Lce.Content(
                        RequestPaymentsUiState(
                            invoice.data!!.invoice_data.invoice_data_encoded_payment_request,
                            walletAddress.data,
                            invoiceAmount.value
                        )
                    )
                } ?: Lce.Content(RequestPaymentsUiState(walletAddress.data, walletAddress.data))
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    fun createInvoice(amount: CurrencyAmount) {
        invoiceAmount.tryEmit(amount)
    }
}