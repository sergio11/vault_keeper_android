package com.dreamsoftware.vaultkeeper.ui.features.app

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(): BrownieViewModel<AppUiState, AppSideEffects>() {
    override fun onGetDefaultState(): AppUiState = AppUiState()
}

data class AppUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null
): UiState<AppUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): AppUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface AppSideEffects: SideEffect