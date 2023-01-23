package com.lightspark.androiddemo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightspark.androiddemo.accountdashboard.AccountDashboardRepository
import com.lightspark.androiddemo.accountdashboard.DashboardData
import com.lightspark.androiddemo.model.NodeDisplayData
import com.lightspark.androiddemo.model.NodeStatistics
import com.lightspark.androiddemo.wallet.WalletRepository
import com.lightspark.api.DashboardOverviewQuery
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.LightsparkNodePurpose
import com.lightspark.api.type.LightsparkNodeStatus
import com.lightspark.sdk.Lce
import com.lightspark.sdk.model.CurrencyAmount
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val dashboardRepository: AccountDashboardRepository = AccountDashboardRepository(),
    private val walletRepository: WalletRepository = WalletRepository()
) : ViewModel() {
    private val refreshDashboard = MutableSharedFlow<Unit>(replay = 1)
    private val refreshWallet = MutableSharedFlow<Unit>(replay = 1)

    val unlockedNodeIds = dashboardRepository.unlockedNodeIds

    val advancedDashboardData = refreshDashboard.flatMapLatest {
        dashboardRepository.getDashboardData().map { result ->
            when (result) {
                is Lce.Content -> Lce.Content(result.data.toDashboardData())
                is Lce.Error -> Lce.Error(result.exception)
                is Lce.Loading -> Lce.Loading
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    val walletDashboardData = refreshWallet.flatMapLatest {
        walletRepository.getWalletDashboard()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    fun refreshAdvancedDashboardData() {
        refreshDashboard.tryEmit(Unit)
    }

    fun refreshWalletData() {
        refreshWallet.tryEmit(Unit)
    }

    fun setActiveWallet(nodeId: String, nodePassword: String) = walletRepository
        .setActiveWalletAndUnlock(nodeId, nodePassword)
        .map {
            when (it) {
                is Lce.Content -> {
                    refreshWallet.tryEmit(Unit)
                    Lce.Content(it.data)
                }
                is Lce.Error -> Lce.Error(it.exception)
                is Lce.Loading -> Lce.Loading
            }
        }

    fun requestKeyRecovery(node: NodeDisplayData) = viewModelScope.launch {
        setActiveWallet(
            node.id,
            // TODO: Replace with actual password. This is just my super secret password scheme for dev :-p.
            "${node.name.replace(" ", "")}!"
        ).collect { result ->
            when (result) {
                is Lce.Content -> {
                    // TODO(Jeremy): Acually do something real with the flow result here in the UI.
                    Log.d("MainViewModel", "Unlocked that node!")
                }
                is Lce.Error -> Log.e(
                    "MainViewModel",
                    "Error setting active wallet",
                    result.exception
                )
                else -> {
                    /* Do nothing when loading */
                }
            }
        }
    }

    private fun DashboardOverviewQuery.Current_account.toDashboardData() = DashboardData(
        accountName = name ?: "Unknown account",
        overviewNodes = dashboard_overview_nodes.edges.map { edge ->
            val node = edge.entity
            NodeDisplayData(
                id = node.id,
                name = node.display_name,
                purpose = node.purpose ?: LightsparkNodePurpose.UNKNOWN__,
                color = node.color ?: "#FFFFFF",
                status = node.status ?: LightsparkNodeStatus.UNKNOWN__,
                publicKey = node.public_key ?: "",
                totalBalance = CurrencyAmount(
                    amount = node.blockchain_balance?.total_balance?.value
                        ?: 0L,
                    unit = node.blockchain_balance?.total_balance?.unit
                        ?: CurrencyUnit.UNKNOWN__
                ),
                availableBalance = CurrencyAmount(
                    amount = node.blockchain_balance?.available_balance?.value
                        ?: 0L,
                    unit = node.blockchain_balance?.available_balance?.unit
                        ?: CurrencyUnit.UNKNOWN__
                ),
                // TODO(Jeremy): Add real stats when the query is fixed
                stats = NodeStatistics(
                    uptime = 99.0f,
                    numChannels = 2,
                    numPaymentsSent = 10,
                    numPaymentsReceived = 1,
                    numTransactionsRouted = 0,
                    amountRouted = CurrencyAmount(10_000_000L, CurrencyUnit.SATOSHI)
                )
            )
        },
        blockchainBalance = blockchain_balance?.let { balance ->
            val availableBalance = balance.available_balance ?: return@let null
            CurrencyAmount(
                availableBalance.value,
                availableBalance.unit
            )
        } ?: CurrencyAmount(0, CurrencyUnit.SATOSHI)
    )
}
