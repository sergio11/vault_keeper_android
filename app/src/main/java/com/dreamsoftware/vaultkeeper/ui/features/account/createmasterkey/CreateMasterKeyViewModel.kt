package com.dreamsoftware.vaultkeeper.ui.features.account.createmasterkey

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.di.CreateMasterKeyErrorMapper
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveMasterKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateMasterKeyViewModel @Inject constructor(
    private val saveMasterKeyUseCase: SaveMasterKeyUseCase,
    @CreateMasterKeyErrorMapper private val errorMapper: IBrownieErrorMapper
) : BrownieViewModel<CreateMasterKeyUiState, CreateMasterKeySideEffects>(),
    CreateMasterKeyScreenActionListener {

    override fun onGetDefaultState(): CreateMasterKeyUiState = CreateMasterKeyUiState()

    override fun onMaterKeyUpdated(newMasterKey: String) {
        updateState { it.copy(masterKey = newMasterKey) }
    }

    override fun onRepeatMasterKeyUpdated(newRepeatMasterKey: String) {
        updateState { it.copy(confirmMasterKey = newRepeatMasterKey) }
    }

    override fun onSave() {
        with(uiState.value) {
            executeUseCaseWithParams(
                useCase = saveMasterKeyUseCase,
                params = SaveMasterKeyUseCase.Params(
                    key = masterKey,
                    confirmKey = confirmMasterKey
                ),
                onSuccess = { onMasterKeyCreated() },
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
    }

    private fun onMasterKeyCreated() {
        launchSideEffect(CreateMasterKeySideEffects.MasterKeyCreatedSideEffect)
    }

    private fun onMapExceptionToState(ex: Exception, uiState: CreateMasterKeyUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class CreateMasterKeyUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val masterKey: String = String.EMPTY,
    val confirmMasterKey: String = String.EMPTY
) : UiState<CreateMasterKeyUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): CreateMasterKeyUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface CreateMasterKeySideEffects : SideEffect {
    data object MasterKeyCreatedSideEffect : CreateMasterKeySideEffects
}