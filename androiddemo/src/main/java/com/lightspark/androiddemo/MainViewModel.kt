package com.lightspark.androiddemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightspark.androiddemo.dashboard.DashboardData
import com.lightspark.androiddemo.dashboard.DashboardRepository
import com.lightspark.androiddemo.model.Balance
import com.lightspark.androiddemo.model.NodeDisplayData
import com.lightspark.androiddemo.model.NodeStatistics
import com.lightspark.androiddemo.model.parseAsBalanceLong
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.LightsparkNodePurpose
import com.lightspark.api.type.LightsparkNodeStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val dashboardRepository: DashboardRepository = DashboardRepository()
) : ViewModel() {
    private val refreshDashboard = MutableSharedFlow<Unit>(replay = 1)

    val unlockedNodeIds = dashboardRepository.unlockedNodeIds

    val dashboardData = refreshDashboard.flatMapLatest {
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
                            totalBalance = Balance(
                                balance = node.blockchain_balance?.total_balance?.value?.parseAsBalanceLong() ?: 0L,
                                unit = node.blockchain_balance?.total_balance?.unit ?: CurrencyUnit.UNKNOWN__
                            ),
                            availableBalance = Balance(
                                balance = node.blockchain_balance?.available_balance?.value?.parseAsBalanceLong() ?: 0L,
                                unit = node.blockchain_balance?.available_balance?.unit ?: CurrencyUnit.UNKNOWN__
                            ),
                            // TODO(Jeremy): Add real stats when the query is fixed
                            stats = NodeStatistics(
                                uptime = 99.0f,
                                numChannels = 2,
                                numPaymentsSent = 10,
                                numPaymentsReceived = 1,
                                numTransactionsRouted = 0,
                                amountRouted = Balance(10_000_000L, CurrencyUnit.SATOSHI)
                            )
                        )
                    },
                    blockchainBalance = data.blockchain_balance?.let { balance ->
                        val availableBalance = balance.available_balance ?: return@let null
                        Balance(availableBalance.value.parseAsBalanceLong() ?: 0L, availableBalance.unit)
                    } ?: Balance(0, CurrencyUnit.SATOSHI)
                )
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun refreshDashboard() {
        refreshDashboard.tryEmit(Unit)
    }

    fun requestKeyRecovery(node: NodeDisplayData) = viewModelScope.launch {
        dashboardRepository.recoverNodeKey(
            node.id,
            // TODO: Replace with actual password. This is just my super secret password scheme for dev :-p.
            "${node.name.replace(" ", "")}!"
        )
    }
}
