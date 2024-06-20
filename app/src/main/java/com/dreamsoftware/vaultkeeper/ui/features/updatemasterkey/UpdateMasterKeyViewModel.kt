package com.dreamsoftware.vaultkeeper.ui.features.updatemasterkey

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.di.UpdateMasterKeyErrorMapper
import com.dreamsoftware.vaultkeeper.domain.usecase.UpdateMasterKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateMasterKeyViewModel @Inject constructor(
    private val updateMasterKeyUseCase: UpdateMasterKeyUseCase,
    @UpdateMasterKeyErrorMapper private val errorMapper: IBrownieErrorMapper
) : BrownieViewModel<UpdateMasterKeyUiState, UpdateMasterKeySideEffects>(),
    UpdateMasterKeyScreenActionListener {

    override fun onGetDefaultState(): UpdateMasterKeyUiState = UpdateMasterKeyUiState()
    override fun onMasterKeyUpdated(masterKey: String) {
        updateState { it.copy(currentMasterKey = masterKey) }
    }

    override fun onNewMasterKeyUpdated(newMasterKey: String) {
        updateState { it.copy(newMasterKey = newMasterKey) }
    }

    override fun onRepeatNewMasterKeyUpdated(newRepeatMasterKey: String) {
        updateState { it.copy(confirmNewMasterKey = newRepeatMasterKey) }
    }

    override fun onSave() {
        with(uiState.value) {
            executeUseCaseWithParams(
                useCase = updateMasterKeyUseCase,
                params = UpdateMasterKeyUseCase.Params(
                    key = currentMasterKey,
                    newKey = newMasterKey,
                    confirmKey = confirmNewMasterKey
                ),
                onSuccess = { onMasterKeyUpdated() },
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
    }

    override fun onUpdateMasterKeyConfirmed() {
        updateState { it.copy(masterKeyUpdatedDialogVisible = false) }
        launchSideEffect(UpdateMasterKeySideEffects.MasterKeyUpdatedSuccessfully)
    }

    private fun onMasterKeyUpdated() {
        updateState { it.copy(masterKeyUpdatedDialogVisible = true) }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: UpdateMasterKeyUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class UpdateMasterKeyUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val masterKeyUpdatedDialogVisible: Boolean = false,
    val currentMasterKey: String = String.EMPTY,
    val newMasterKey: String = String.EMPTY,
    val confirmNewMasterKey: String = String.EMPTY
) : UiState<UpdateMasterKeyUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): UpdateMasterKeyUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface UpdateMasterKeySideEffects : SideEffect {
    data object MasterKeyUpdatedSuccessfully: UpdateMasterKeySideEffects
}