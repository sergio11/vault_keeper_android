package com.dreamsoftware.vaultkeeper.ui.features.account.signup

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.di.SignUpScreenErrorMapper
import com.dreamsoftware.vaultkeeper.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    @SignUpScreenErrorMapper private val errorMapper: IBrownieErrorMapper,
): BrownieViewModel<SignUpUiState, SignUpSideEffects>(), SignUpScreenActionListener {


    override fun onGetDefaultState(): SignUpUiState = SignUpUiState()

    override fun onEmailChanged(newEmail: String) {
        updateState { it.copy(email = newEmail) }
    }

    override fun onPasswordChanged(newPassword: String) {
        updateState { it.copy(password = newPassword) }
    }

    override fun onConfirmPasswordChanged(newConfirmPassword: String) {
        updateState { it.copy(confirmPassword = newConfirmPassword) }
    }

    override fun onSignUp() {
        with(uiState.value) {
            executeUseCaseWithParams(
                useCase = signUpUseCase,
                params = SignUpUseCase.Params(email, password, confirmPassword),
                onSuccess = {
                    onSigUpSuccessfully()
                },
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
    }

    override fun onNavigateToSignIn() {
        launchSideEffect(SignUpSideEffects.NavigateToSignIn)
    }

    private fun onSigUpSuccessfully() {
        launchSideEffect(SignUpSideEffects.RegisteredSuccessfully)
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SignUpUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class SignUpUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val email: String = String.EMPTY,
    val emailError: String? = null,
    val password: String = String.EMPTY,
    val passwordError: String? = null,
    val confirmPassword: String = String.EMPTY
): UiState<SignUpUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SignUpUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface SignUpSideEffects: SideEffect {
    data object NavigateToSignIn : SignUpSideEffects
    data object RegisteredSuccessfully: SignUpSideEffects
}