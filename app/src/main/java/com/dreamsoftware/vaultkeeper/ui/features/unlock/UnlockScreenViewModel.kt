package com.dreamsoftware.vaultkeeper.ui.features.unlock

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnlockScreenViewModel @Inject constructor() : BrownieViewModel<UnlockScreenUiState, UnlockScreenSideEffects>(), UnlockScreenActionListener {

    override fun onGetDefaultState(): UnlockScreenUiState = UnlockScreenUiState()
    override fun onMaterKeyUpdated(newMasterKey: String) {
        updateState { it.copy(masterKey = newMasterKey) }
    }

    override fun onValidate() {

    }
}

data class UnlockScreenUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val masterKey: String = String.EMPTY
) : UiState<UnlockScreenUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): UnlockScreenUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface UnlockScreenSideEffects : SideEffect {
}