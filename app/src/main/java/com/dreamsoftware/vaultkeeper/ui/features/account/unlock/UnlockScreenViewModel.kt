package com.dreamsoftware.vaultkeeper.ui.features.account.unlock

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.di.UnlockScreenErrorMapper
import com.dreamsoftware.vaultkeeper.domain.usecase.UnLockAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.ValidateMasterKeyUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.VerifyBiometricAuthEnabledUseCase
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UnlockScreenViewModel @Inject constructor(
    private val validateMasterKeyUseCase: ValidateMasterKeyUseCase,
    private val unLockAccountUseCase: UnLockAccountUseCase,
    private val verifyBiometricAuthEnabledUseCase: VerifyBiometricAuthEnabledUseCase,
    private val applicationAware: IVaultKeeperApplicationAware,
    @UnlockScreenErrorMapper private val errorMapper: IBrownieErrorMapper
) : BrownieViewModel<UnlockScreenUiState, UnlockScreenSideEffects>(), UnlockScreenActionListener {

    override fun onGetDefaultState(): UnlockScreenUiState = UnlockScreenUiState()

    fun onInit() {
        executeUseCase(
            useCase = verifyBiometricAuthEnabledUseCase,
            onSuccess = ::onVerifyBiometricAuthenticationCompleted
        )
    }

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

    override fun onBiometricAuthSuccessfully() {
        executeUseCase(
            useCase = unLockAccountUseCase,
            onSuccess = { onMasterKeyValidatedSuccessfully() },
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onMasterKeyValidatedSuccessfully() {
        launchSideEffect(UnlockScreenSideEffects.AccountUnlockSuccessfully)
    }

    private fun onVerifyBiometricAuthenticationCompleted(isEnabled: Boolean) {
        updateState { it.copy(isBiometricAuthEnabled = applicationAware.isBiometricSupported() && isEnabled) }
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
    val masterKey: String = String.EMPTY,
    val isBiometricAuthEnabled: Boolean = false
) : UiState<UnlockScreenUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): UnlockScreenUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface UnlockScreenSideEffects : SideEffect {
    data object AccountUnlockSuccessfully: UnlockScreenSideEffects
}