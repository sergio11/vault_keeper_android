package com.dreamsoftware.lockbuddy.ui.features.splash

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): BrownieViewModel<SplashUiState, SplashSideEffects>() {
    override fun onGetDefaultState(): SplashUiState = SplashUiState()

    fun verifySession() {
        viewModelScope.launch {
            delay(4000)
            onVerifyUserSessionFailed()
        }
    }

    private fun onVerifyUserSessionCompleted(hasActiveSession: Boolean) {
        launchSideEffect(SplashSideEffects.UserNotAuthenticated)
    }

    private fun onVerifyUserSessionFailed() {
        launchSideEffect(SplashSideEffects.UserNotAuthenticated)
    }
}

data class SplashUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val isAuth: Boolean = false
): UiState<SplashUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): SplashUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface SplashSideEffects: SideEffect {
    data object UserAlreadyAuthenticated: SplashSideEffects
    data object UserNotAuthenticated: SplashSideEffects
}