package com.dreamsoftware.vaultkeeper.ui.features.account.splash

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.usecase.VerifyUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val verifyUserSessionUseCase: VerifyUserSessionUseCase
): BrownieViewModel<SplashUiState, SplashSideEffects>() {
    override fun onGetDefaultState(): SplashUiState = SplashUiState()

    fun verifySession() {
        viewModelScope.launch {
            delay(4000)
            executeUseCase(
                useCase = verifyUserSessionUseCase,
                onSuccess = ::onVerifyUserSessionCompleted,
                onFailed = ::onVerifyUserSessionFailed
            )
        }
    }

    private fun onVerifyUserSessionCompleted(authUserBO: AuthUserBO) {
        launchSideEffect(if(authUserBO.hasMasterKey) {
            SplashSideEffects.UserAlreadyAuthenticatedSideEffect
        } else {
            SplashSideEffects.RequireMasterKeySideEffect
        })
    }

    private fun onVerifyUserSessionFailed() {
        launchSideEffect(SplashSideEffects.UserNotAuthenticatedSideEffect)
    }
}

data class SplashUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val isAuth: Boolean = false
): UiState<SplashUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SplashUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface SplashSideEffects: SideEffect {
    data object UserAlreadyAuthenticatedSideEffect: SplashSideEffects
    data object UserNotAuthenticatedSideEffect: SplashSideEffects
    data object RequireMasterKeySideEffect: SplashSideEffects
}