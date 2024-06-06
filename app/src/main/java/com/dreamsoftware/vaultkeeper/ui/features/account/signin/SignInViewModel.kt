package com.dreamsoftware.vaultkeeper.ui.features.account.signin

import android.util.Patterns
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.di.SignInScreenErrorMapper
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    @SignInScreenErrorMapper private val errorMapper: IBrownieErrorMapper
): BrownieViewModel<SignInUiState, SignInSideEffects>(), SignInScreenActionListener {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    override fun onGetDefaultState(): SignInUiState = SignInUiState()
    override fun onEmailChanged(newEmail: String) {
        updateState {
            it.copy(
                email = newEmail,
                isLoginButtonEnabled = loginButtonShouldBeEnabled(newEmail, it.password)
            )
        }
    }

    override fun onPasswordChanged(newPassword: String) {
        updateState {
            it.copy(
                password = newPassword,
                isLoginButtonEnabled = loginButtonShouldBeEnabled(it.email, newPassword)
            )
        }
    }

    override fun onSignIn() {
        with(uiState.value) {
            executeUseCaseWithParams(
                useCase = signInUseCase,
                params = SignInUseCase.Params(email, password),
                onSuccess = ::onSignInSuccessfully,
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
    }

    private fun loginButtonShouldBeEnabled(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.length > MIN_PASSWORD_LENGTH

    private fun onSignInSuccessfully(authUserBO: AuthUserBO) {
        launchSideEffect(if(authUserBO.hasMasterKey) {
            SignInSideEffects.UserAuthenticatedSuccessfullySideEffect
        } else {
            SignInSideEffects.RequireMasterKeySideEffect
        })
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SignInUiState) =
        uiState.copy(
            isLoading = false,
            error = errorMapper.mapToMessage(ex)
        )
}

data class SignInUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val email: String = String.EMPTY,
    val password: String = String.EMPTY,
    val isLoginButtonEnabled: Boolean = false
): UiState<SignInUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): SignInUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface SignInSideEffects: SideEffect {
    data object UserAuthenticatedSuccessfullySideEffect: SignInSideEffects
    data object RequireMasterKeySideEffect: SignInSideEffects
}