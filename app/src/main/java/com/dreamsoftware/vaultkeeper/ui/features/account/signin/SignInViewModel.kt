package com.dreamsoftware.vaultkeeper.ui.features.account.signin

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


    override fun onGetDefaultState(): SignInUiState = SignInUiState()
    override fun onEmailChanged(newEmail: String) {
        updateState { it.copy(email = newEmail) }
    }

    override fun onPasswordChanged(newPassword: String) {
        updateState { it.copy(password = newPassword) }
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
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class SignInUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val email: String = String.EMPTY,
    val emailError: String? = null,
    val password: String = String.EMPTY,
    val passwordError: String? = null,
): UiState<SignInUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SignInUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface SignInSideEffects: SideEffect {
    data object UserAuthenticatedSuccessfullySideEffect: SignInSideEffects
    data object RequireMasterKeySideEffect: SignInSideEffects
}