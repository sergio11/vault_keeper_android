package com.dreamsoftware.vaultkeeper.ui.features.account.signup

import android.util.Patterns
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.vaultkeeper.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val signUpScreenSimpleErrorMapper: SignUpScreenSimpleErrorMapper,
): BrownieViewModel<SignUpUiState, SignUpSideEffects>(), SignUpScreenActionListener {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    override fun onGetDefaultState(): SignUpUiState = SignUpUiState()

    override fun onEmailChanged(newEmail: String) {
        updateState {
            it.copy(
                email = newEmail,
                isSignUpButtonEnabled = signButtonUpShouldBeEnabled(newEmail, it.password.orEmpty())
            )
        }
    }

    override fun onPasswordChanged(newPassword: String) {
        updateState {
            it.copy(
                password = newPassword,
                isSignUpButtonEnabled = signButtonUpShouldBeEnabled(it.email.orEmpty(), newPassword)
            )
        }
    }

    override fun onSignUp() {
        with(uiState.value) {
            executeUseCaseWithParams(
                useCase = signUpUseCase,
                params = SignUpUseCase.Params(email.orEmpty(), password.orEmpty()),
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

    private fun signButtonUpShouldBeEnabled(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.length > MIN_PASSWORD_LENGTH

    private fun onSigUpSuccessfully() {
        launchSideEffect(SignUpSideEffects.RegisteredSuccessfully)
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SignUpUiState) =
        uiState.copy(
            isLoading = false,
            error = signUpScreenSimpleErrorMapper.mapToMessage(ex)
        )
}

data class SignUpUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val email: String? = null,
    val password: String? = null,
    val isSignUpButtonEnabled: Boolean = false
): UiState<SignUpUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): SignUpUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface SignUpSideEffects: SideEffect {
    data object NavigateToSignIn : SignUpSideEffects
    data object RegisteredSuccessfully: SignUpSideEffects
}