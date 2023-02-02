package com.lightspark.androiddemo

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightspark.androiddemo.accountdashboard.AccountDashboardRepository
import com.lightspark.androiddemo.accountdashboard.DashboardData
import com.lightspark.androiddemo.auth.AuthState
import com.lightspark.androiddemo.auth.CredentialsStore
import com.lightspark.androiddemo.auth.SavedCredentials
import com.lightspark.androiddemo.model.NodeDisplayData
import com.lightspark.androiddemo.model.NodeLockStatus
import com.lightspark.androiddemo.model.NodeStatistics
import com.lightspark.androiddemo.settings.DefaultPrefsStore
import com.lightspark.androiddemo.settings.SavedPrefs
import com.lightspark.androiddemo.wallet.WalletRepository
import com.lightspark.api.DashboardOverviewQuery
import com.lightspark.api.type.BitcoinNetwork
import com.lightspark.api.type.CurrencyUnit
import com.lightspark.api.type.LightsparkNodePurpose
import com.lightspark.api.type.LightsparkNodeStatus
import com.lightspark.sdk.Lce
import com.lightspark.sdk.auth.DataStoreAuthStateStorage
import com.lightspark.sdk.auth.OAuthHelper
import com.lightspark.sdk.auth.OAuthProvider
import com.lightspark.sdk.model.CurrencyAmount
import com.lightspark.sdk.model.ServerEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val DEV_OAUTH_CLIENT_ID = "01860f40-e211-7777-0000-da8b0e7566a5"
private const val OAUTH_REDIRECT_URL = "com.lightspark.androiddemo:/auth-redirect"

// NOTE: This is a public client secret, it is safe to include in the app.
private const val OAUTH_CLIENT_SECRET = "EcvwdPJW8102Rv8TR8OUpw573huPoi2s7RMW19pFOT8"

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val dashboardRepository: AccountDashboardRepository = AccountDashboardRepository(),
    private val walletRepository: WalletRepository = WalletRepository(),
    private val credentialsStore: CredentialsStore = CredentialsStore.instance,
    private val prefsStore: DefaultPrefsStore = DefaultPrefsStore.instance,
) : ViewModel() {
    private val oAuthStorage = DataStoreAuthStateStorage(LightsparkDemoApplication.instance)
    private val oAuthHelper = OAuthHelper(LightsparkDemoApplication.instance, oAuthStorage)

    private val accountTokenInfo = credentialsStore.getAccountTokenFlow()
        .runningFold(null to null) { prevInfo: Pair<SavedCredentials?, SavedCredentials?>, tokenInfo ->
            prevInfo.second to tokenInfo
        }
        .onEach { tokenInfoWithPrev ->
            val prevTokenInfo = tokenInfoWithPrev.first
            val tokenInfo = tokenInfoWithPrev.second
            if (tokenInfo != null) {
                if (prevTokenInfo?.tokenId != tokenInfo.tokenId || prevTokenInfo.tokenSecret != tokenInfo.tokenSecret) {
                    dashboardRepository.setAccountToken(tokenInfo.tokenId, tokenInfo.tokenSecret)
                }
            }
        }
        .map { tokenInfo ->
            Lce.Content(tokenInfo.second)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    val preferences = prefsStore.getPrefsFlow()
        .runningFold(null to null) { prevPrefs: Pair<SavedPrefs?, SavedPrefs?>, newPrefs ->
            prevPrefs.second to newPrefs
        }
        .onEach { prefsWithPrev ->
            val prevPrefs = prefsWithPrev.first
            val prefs = prefsWithPrev.second ?: SavedPrefs.DEFAULT
            if (prefsWithPrev.second != null) {
                walletRepository.setActiveWalletWithoutUnlocking(prefs.defaultWalletNodeId)
            }
            if (prefs.bitcoinNetwork != (prevPrefs?.bitcoinNetwork
                    ?: SavedPrefs.DEFAULT.bitcoinNetwork)
            ) {
                dashboardRepository.setBitcoinNetwork(prefs.bitcoinNetwork)
            }
            if (prefs.environment != (prevPrefs?.environment ?: SavedPrefs.DEFAULT.environment)) {
                dashboardRepository.setServerEnvironment(prefs.environment)
                credentialsStore.clear()
                oAuthHelper.setServerEnvironment(prefs.environment)
                oAuthStatusChange.emit(OAuthEvent(false, "Logged out due to environment change"))
            }
        }
        .map { prefsWithPrev -> prefsWithPrev.second ?: SavedPrefs.DEFAULT }
        .stateIn(viewModelScope, SharingStarted.Eagerly, SavedPrefs.DEFAULT)

    val oAuthIsAuthorized = oAuthStorage.observeIsAuthorized()

    data class OAuthEvent(val isError: Boolean, val message: String)

    val oAuthStatusChange = MutableSharedFlow<OAuthEvent>()

    val tokenState = combine(
        oAuthIsAuthorized,
        accountTokenInfo
    ) { isOAuthAuthorize, tokenInfo ->
        if (isOAuthAuthorize) {
            return@combine Lce.Content(AuthState.HAS_TOKEN)
        }
        when (tokenInfo) {
            is Lce.Loading -> Lce.Loading
            is Lce.Error -> Lce.Error(tokenInfo.exception)
            is Lce.Content -> {
                if (tokenInfo.data == null) {
                    Lce.Content(AuthState.NO_TOKEN)
                } else {
                    Lce.Content(AuthState.HAS_TOKEN)
                }
            }
        }
    }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    private val refreshDashboard = MutableSharedFlow<Unit>(replay = 1)
    private val refreshWallet = MutableSharedFlow<Unit>(replay = 1)

    private val unlockingNodeIds = MutableStateFlow(emptySet<String>())

    private val nodeLockStatus = combine(
        dashboardRepository.unlockedNodeIds,
        unlockingNodeIds
    ) { unlockedNodeIds, unlockingNodeIds ->
        unlockedNodeIds.associateWith { NodeLockStatus.UNLOCKED } +
                unlockingNodeIds.associateWith { NodeLockStatus.UNLOCKING }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyMap())

    val advancedDashboardData =
        combine(
            nodeLockStatus,
            refreshDashboard.flatMapLatest { dashboardRepository.getDashboardData() }
        ) { lockStatuses, dashboardResult ->
            when (dashboardResult) {
                is Lce.Content -> Lce.Content(dashboardResult.data.toDashboardData(lockStatuses))
                is Lce.Error -> Lce.Error(dashboardResult.exception)
                is Lce.Loading -> Lce.Loading
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    val walletDashboardData = refreshWallet.flatMapLatest {
        walletRepository.getWalletDashboard()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    val walletUnlockStatus = nodeLockStatus.map { statuses ->
        statuses[walletRepository.activeWalletId] ?: NodeLockStatus.LOCKED
    }.stateIn(viewModelScope, SharingStarted.Eagerly, NodeLockStatus.LOCKED)

    init {
        if (oAuthHelper.isAuthorized()) {
            dashboardRepository.setAuthProvider(OAuthProvider(oAuthHelper))
        }
    }

    fun refreshAdvancedDashboardData() {
        refreshDashboard.tryEmit(Unit)
    }

    fun refreshWalletData() {
        refreshWallet.tryEmit(Unit)
    }

    fun onApiTokenInfoSubmitted(
        tokenId: String,
        tokenSecret: String,
    ) = viewModelScope.launch {
        credentialsStore.setAccountData(tokenId, tokenSecret)
    }

    fun onBitcoinNetworkSelected(bitcoinNetwork: BitcoinNetwork) = viewModelScope.launch {
        prefsStore.setDefaultWalletNode(null)
        prefsStore.setBitcoinNetwork(bitcoinNetwork)
    }

    fun onServerEnvironmentSelected(environment: ServerEnvironment) = viewModelScope.launch {
        prefsStore.setDefaultWalletNode(null)
        prefsStore.setServerEnvironment(environment)
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

    fun unlockWallet(nodePassword: String) = requestKeyRecovery(
        requireNotNull(walletRepository.activeWalletId) { "No active wallet" },
        nodePassword
    )

    fun setActiveWalletWithoutUnlocking(nodeId: String) =
        viewModelScope.launch(context = Dispatchers.IO) {
            walletRepository.setActiveWalletWithoutUnlocking(nodeId)
        }

    fun requestKeyRecovery(nodeId: String, nodePassword: String) =
        viewModelScope.launch(context = Dispatchers.IO) {
            setActiveWallet(nodeId, nodePassword).collect { result ->
                when (result) {
                    is Lce.Content -> {
                        unlockingNodeIds.value =
                            unlockingNodeIds.value.toMutableSet().apply { remove(nodeId) }
                        Log.d("MainViewModel", "Unlocked that node!")
                    }
                    is Lce.Error -> {
                        unlockingNodeIds.value =
                            unlockingNodeIds.value.toMutableSet().apply { remove(nodeId) }
                        Log.e(
                            "MainViewModel",
                            "Error setting active wallet",
                            result.exception
                        )
                    }
                    else -> {
                        unlockingNodeIds.value =
                            unlockingNodeIds.value.toMutableSet().apply { add(nodeId) }
                    }
                }
            }
        }

    private fun DashboardOverviewQuery.Current_account.toDashboardData(
        nodeLockStatuses: Map<String, NodeLockStatus>
    ) = DashboardData(
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
                lockStatus = nodeLockStatuses[node.id] ?: NodeLockStatus.LOCKED,
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

    fun handleAuthResponse(intent: Intent) {
        try {
            oAuthHelper.handleAuthResponse(intent)
        } catch (e: Exception) {
            oAuthStatusChange.emitAsync(
                OAuthEvent(
                    isError = true,
                    message = "Error handling auth response."
                )
            )
            Log.e("MainActivity", "Error handling auth response", e)
            return
        }
        oAuthHelper.fetchAndPersistRefreshToken(oauthClientSecret()) { _, error ->
            if (error != null) {
                oAuthStatusChange.emitAsync(
                    OAuthEvent(
                        isError = true,
                        message = "Error fetching auth refresh token."
                    )
                )
                Log.e("MainActivity", "Error fetching refresh token", error)
                return@fetchAndPersistRefreshToken
            }
            oAuthStatusChange.emitAsync(
                OAuthEvent(
                    isError = false,
                    message = "Successfully authenticated!"
                )
            )
            Log.i("MainActivity", "Auth flow completed")
            dashboardRepository.setAuthProvider(OAuthProvider(oAuthHelper))
        }
    }

    private fun MutableSharedFlow<OAuthEvent>.emitAsync(event: OAuthEvent) = viewModelScope.launch {
        emit(event)
    }

    private fun oauthClientId() = when (preferences.value.environment) {
        ServerEnvironment.DEV -> DEV_OAUTH_CLIENT_ID
        ServerEnvironment.PROD -> "2cacb0a9-23ae-4e57-b0c4-2fe4f7a8da29"
    }

    private fun oauthClientSecret() = when (preferences.value.environment) {
        ServerEnvironment.DEV -> OAUTH_CLIENT_SECRET
        ServerEnvironment.PROD -> ""
    }

    fun launchOAuthFlow(
        completedIntent: PendingIntent,
        cancelIntent: PendingIntent
    ) {
        oAuthHelper.launchAuthFlow(
            oauthClientId(),
            OAUTH_REDIRECT_URL,
            completedIntent,
            cancelIntent
        )
    }
}
