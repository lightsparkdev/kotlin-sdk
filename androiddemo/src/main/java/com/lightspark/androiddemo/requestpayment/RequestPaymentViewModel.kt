package com.lightspark.androiddemo.requestpayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightspark.androiddemo.wallet.PaymentRepository
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.sdk.Lce
import com.lightspark.sdk.model.CurrencyAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class RequestPaymentViewModel @Inject constructor(
    private val repository: PaymentRepository
) : ViewModel() {
    private val invoiceAmount = MutableStateFlow<CurrencyAmount?>(null)
    private val invoice = invoiceAmount.flatMapLatest { amount ->
        if (amount == null) {
            repository.createInvoice(CurrencyAmount(0, CurrencyUnit.SATOSHI))
        } else {
            repository.createInvoice(amount)
        }
    }

    val uiState: StateFlow<Lce<RequestPaymentsUiState>> get() = _uiState
    private val _uiState = combine(
        invoice,
        repository.getWalletAddress()
    ) { invoice, walletAddress ->
        when {
            invoice is Lce.Loading || walletAddress is Lce.Loading -> Lce.Loading
            walletAddress is Lce.Error -> Lce.Error(walletAddress.exception)
            invoice is Lce.Error -> Lce.Error(invoice.exception)
            else -> {
                invoice as Lce.Content
                walletAddress as Lce.Content
                invoice.data.let {
                    Lce.Content(
                        RequestPaymentsUiState(
                            invoice.data.invoice_data.invoice_data_encoded_payment_request,
                            walletAddress.data,
                            invoiceAmount.value
                        )
                    )
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    fun createInvoice(amount: CurrencyAmount) {
        invoiceAmount.tryEmit(amount)
    }
}