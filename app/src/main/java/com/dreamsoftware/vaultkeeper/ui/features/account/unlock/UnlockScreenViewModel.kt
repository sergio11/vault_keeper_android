package com.dreamsoftware.vaultkeeper.ui.features.account.unlock

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.di.UnlockScreenErrorMapper
import com.dreamsoftware.vaultkeeper.domain.usecase.ValidateMasterKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnlockScreenViewModel @Inject constructor(
    private val validateMasterKeyUseCase: ValidateMasterKeyUseCase,
    @UnlockScreenErrorMapper private val errorMapper: IBrownieErrorMapper,
) : BrownieViewModel<UnlockScreenUiState, UnlockScreenSideEffects>(), UnlockScreenActionListener {

    override fun onGetDefaultState(): UnlockScreenUiState = UnlockScreenUiState()
    override fun onMaterKeyUpdated(newMasterKey: String) {
        updateState { it.copy(masterKey = newMasterKey) }
    }

    override fun onValidate() {
        executeUseCaseWithParams(
            useCase = validateMasterKeyUseCase,
            params = ValidateMasterKeyUseCase.Params(
                key = uiState.value.masterKey
            ),
            onSuccess = { onMasterKeyValidatedSuccessfully() },
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onMasterKeyValidatedSuccessfully() {
        launchSideEffect(UnlockScreenSideEffects.AccountUnlockSuccessfully)
    }

    private fun onMapExceptionToState(ex: Exception, uiState: UnlockScreenUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
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
    data object AccountUnlockSuccessfully: UnlockScreenSideEffects
}