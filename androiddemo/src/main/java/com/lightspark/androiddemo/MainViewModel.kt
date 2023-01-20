package com.lightspark.androiddemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightspark.androiddemo.dashboard.DashboardData
import com.lightspark.androiddemo.dashboard.DashboardRepository
import com.lightspark.androiddemo.model.NodeDisplayData
import com.lightspark.androiddemo.model.NodeStatistics
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.LightsparkNodePurpose
import com.lightspark.api.type.LightsparkNodeStatus
import com.lightspark.sdk.Result
import com.lightspark.sdk.model.CurrencyAmount
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val dashboardRepository: DashboardRepository = DashboardRepository()
) : ViewModel() {
    private val refreshDashboard = MutableSharedFlow<Unit>(replay = 1)
    private val refreshWallet = MutableSharedFlow<String>(replay = 1)

    val unlockedNodeIds = dashboardRepository.unlockedNodeIds

    val advancedDashboardData = refreshDashboard.flatMapLatest {
        flow {
            val data = dashboardRepository.getDashboardData() ?: return@flow
            emit(
                DashboardData(
                    accountName = data.name ?: "Unknown account",
                    overviewNodes = data.dashboard_overview_nodes.edges.map { edge ->
                        val node = edge.entity
                        NodeDisplayData(
                            id = node.id,
                            name = node.display_name,
                            purpose = node.purpose ?: LightsparkNodePurpose.UNKNOWN__,
                            color = node.color ?: "#FFFFFF",
                            status = node.status ?: LightsparkNodeStatus.UNKNOWN__,
                            publicKey = node.public_key ?: "",
                            totalBalance = CurrencyAmount(
                                balance = node.blockchain_balance?.total_balance?.value
                                    ?: 0L,
                                unit = node.blockchain_balance?.total_balance?.unit
                                    ?: CurrencyUnit.UNKNOWN__
                            ),
                            availableBalance = CurrencyAmount(
                                balance = node.blockchain_balance?.available_balance?.value
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
                    blockchainBalance = data.blockchain_balance?.let { balance ->
                        val availableBalance = balance.available_balance ?: return@let null
                        CurrencyAmount(
                            availableBalance.value,
                            availableBalance.unit
                        )
                    } ?: CurrencyAmount(0, CurrencyUnit.SATOSHI)
                )
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val walletDashboardData = refreshWallet.flatMapLatest { nodeId ->
        dashboardRepository.getWalletDashboard(nodeId)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Result.Loading)

    fun refreshAdvancedDashboardData() {
        refreshDashboard.tryEmit(Unit)
    }

    fun refreshWalletData(nodeId: String) {
        refreshWallet.tryEmit(nodeId)
    }

    fun requestKeyRecovery(node: NodeDisplayData) = viewModelScope.launch {
        dashboardRepository.recoverNodeKey(
            node.id,
            // TODO: Replace with actual password. This is just my super secret password scheme for dev :-p.
            "${node.name.replace(" ", "")}!"
        )
    }
}
