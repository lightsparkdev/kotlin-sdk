package com.lightspark.androidwalletdemo.requestpayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightspark.androidwalletdemo.util.CurrencyAmountArg
import com.lightspark.androidwalletdemo.wallet.PaymentRepository
import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.wallet.model.CurrencyUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class RequestPaymentViewModel @Inject constructor(
    private val repository: PaymentRepository,
) : ViewModel() {
    private val invoiceAmount = MutableStateFlow<CurrencyAmountArg?>(null)
    private val invoice = invoiceAmount.flatMapLatest { amount ->
        if (amount == null) {
            repository.createInvoice(CurrencyAmountArg(0, CurrencyUnit.SATOSHI))
        } else {
            repository.createInvoice(amount)
        }
    }

    val uiState: StateFlow<Lce<RequestPaymentUiState>> get() = _uiState
    private val _uiState = invoice.map { invoice ->
        when (invoice) {
            is Lce.Loading -> Lce.Loading
            is Lce.Error -> Lce.Error(invoice.exception)
            is Lce.Content ->
                invoice.data.let {
                    Lce.Content(
                        RequestPaymentUiState(
                            invoice.data.data.encodedPaymentRequest,
                            invoiceAmount.value,
                        ),
                    )
                }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    fun createInvoice(amount: CurrencyAmountArg) {
        invoiceAmount.tryEmit(amount)
    }
}
