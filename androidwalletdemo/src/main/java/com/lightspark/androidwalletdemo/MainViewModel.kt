package com.lightspark.androidwalletdemo

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lightspark.androidwalletdemo.auth.AuthRepository
import com.lightspark.androidwalletdemo.auth.AuthState
import com.lightspark.androidwalletdemo.auth.CredentialsStore
import com.lightspark.androidwalletdemo.auth.SavedCredentials
import com.lightspark.androidwalletdemo.model.WalletLockStatus
import com.lightspark.androidwalletdemo.settings.DefaultPrefsStore
import com.lightspark.androidwalletdemo.settings.SavedPrefs
import com.lightspark.androidwalletdemo.wallet.WalletRepository
import com.lightspark.sdk.core.Lce
import com.lightspark.sdk.core.requester.ServerEnvironment
import com.lightspark.sdk.wallet.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// TODO: This class should probably be broken up into 2 ViewModels.
@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel @Inject constructor(
    private val credentialsStore: CredentialsStore,
    private val prefsStore: DefaultPrefsStore,
    private val walletRepository: WalletRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    val jwtInfo = credentialsStore.getJwtInfoFlow()
        .runningFold(null to null) { prevInfo: Pair<SavedCredentials?, SavedCredentials?>, tokenInfo ->
            prevInfo.second to tokenInfo
        }
        .flatMapLatest { tokenInfoWithPrev ->
            val prevTokenInfo = tokenInfoWithPrev.first
            val tokenInfo = tokenInfoWithPrev.second
            if (tokenInfo != null) {
                if (prevTokenInfo?.accountId != tokenInfo.accountId || prevTokenInfo.jwt != tokenInfo.jwt) {
                    return@flatMapLatest walletRepository.loginWithJwt(tokenInfo.accountId, tokenInfo.jwt).map { jwt ->
                        when (jwt) {
                            is Lce.Loading -> Lce.Loading
                            is Lce.Error -> Lce.Error(jwt.exception)
                            is Lce.Content -> Lce.Content(tokenInfo)
                        }
                    }
                }
            }
            flowOf(Lce.Content(null))
        }.stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    val preferences = prefsStore.getPrefsFlow()
        .runningFold(null to null) { prevPrefs: Pair<SavedPrefs?, SavedPrefs?>, newPrefs ->
            prevPrefs.second to newPrefs
        }
        .onEach { prefsWithPrev ->
            val prevPrefs = prefsWithPrev.first
            val prefs = prefsWithPrev.second ?: SavedPrefs.DEFAULT
            if (prefs.environment != (prevPrefs?.environment ?: prefs.environment)) {
                val hadToken = credentialsStore.clear()
                if (hadToken) {
                    authStatusChange.emit(
                        AuthEvent(false, "Logged out due to environment change"),
                    )
                }
                walletRepository.setServerEnvironment(
                    prefs.environment,
                    invalidateAuth = hadToken,
                )
            }
        }
        .map { prefsWithPrev -> prefsWithPrev.second ?: SavedPrefs.DEFAULT }
        .stateIn(viewModelScope, SharingStarted.Eagerly, SavedPrefs.DEFAULT)

    data class AuthEvent(val isError: Boolean, val message: String)

    val authStatusChange = MutableSharedFlow<AuthEvent>()

    val tokenState = jwtInfo.map { tokenInfo ->
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
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    private val refreshWallet = MutableSharedFlow<Unit>(replay = 1)

    private val isUnlocking = MutableStateFlow(false)

    val walletUnlockStatus = combine(
        walletRepository.isWalletUnlocked,
        isUnlocking,
    ) { isUnlocked, isUnlocking ->
        if (isUnlocking) {
            WalletLockStatus.UNLOCKING
        } else if (isUnlocked) {
            WalletLockStatus.UNLOCKED
        } else {
            WalletLockStatus.LOCKED
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, WalletLockStatus.LOCKED)

    private val lastReceivedWalletData = MutableStateFlow<Wallet?>(null)
    val walletDashboardData = combine(
        refreshWallet.flatMapLatest { walletRepository.getWalletDashboard() },
        lastReceivedWalletData,
    ) { dashboard, wallet ->
        if (dashboard is Lce.Content && wallet != null) {
            Lce.Content(dashboard.data.copy(status = wallet.status))
        } else {
            dashboard
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Lce.Loading)

    fun refreshWalletData() {
        refreshWallet.tryEmit(Unit)
    }

    fun onApiTokenInfoSubmitted(
        tokenId: String,
        tokenSecret: String,
    ) = viewModelScope.launch {
        credentialsStore.setAccountData(tokenId, tokenSecret)
    }

    fun onDemoLogin(
        userName: String,
        password: String,
    ) = viewModelScope.launch {
        credentialsStore.setUserName(userName)
        authRepository.getJwt(userName, password).collect {
            when (it) {
                is Lce.Loading -> {
                    // TODO: Show loading indicator
                }

                is Lce.Error -> authStatusChange.emit(
                    AuthEvent(true, "Error logging in: ${it.exception?.message ?: "Unknown error"}"),
                )

                is Lce.Content -> {
                    credentialsStore.setAccountData(it.data.accountId, it.data.token)
                }
            }
        }
    }

    fun onServerEnvironmentSelected(environment: ServerEnvironment) = viewModelScope.launch {
        prefsStore.setServerEnvironment(environment)
    }

    fun unlockWallet(signingPrivateKeyPEM: String) = viewModelScope.launch(context = Dispatchers.IO) {
        walletRepository.unlockWallet(Base64.decode(signingPrivateKeyPEM, Base64.DEFAULT)).collect {
            when (it) {
                is Lce.Loading -> isUnlocking.value = true
                is Lce.Error -> isUnlocking.value = false
                is Lce.Content -> isUnlocking.value = false
            }
        }
    }

    fun deployWallet() = viewModelScope.launch(context = Dispatchers.IO) {
        walletRepository.deployWallet().collect {
            when (it) {
                is Lce.Loading -> {}
                is Lce.Error -> {}
                is Lce.Content -> lastReceivedWalletData.emit(it.data)
            }
        }
    }

    fun initializeWallet() = viewModelScope.launch(context = Dispatchers.IO) {
        walletRepository.initializeWallet().collect {
            when (it) {
                is Lce.Loading -> {}
                is Lce.Error -> {}
                is Lce.Content -> lastReceivedWalletData.emit(it.data)
            }
        }
    }

    fun attemptKeyStoreUnlock() = walletRepository.attemptKeyStoreUnlock()
}
