package com.dreamsoftware.vaultkeeper.ui.features.account.signin

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(): BrownieViewModel<SignInUiState, SignInSideEffects>(), SignInScreenActionListener {

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    override fun onGetDefaultState(): SignInUiState = SignInUiState()
    override fun onEmailChanged(newEmail: String) {
        updateState {
            it.copy(
                email = newEmail,
                isLoginButtonEnabled = loginButtonShouldBeEnabled(newEmail, it.password.orEmpty())
            )
        }
    }

    override fun onPasswordChanged(newPassword: String) {
        updateState {
            it.copy(
                password = newPassword,
                isLoginButtonEnabled = loginButtonShouldBeEnabled(it.email.orEmpty(), newPassword)
            )
        }
    }

    override fun onSignIn() {
        viewModelScope.launch {
            updateState {
                it.copy(isLoading = true)
            }
            delay(4500)
            launchSideEffect(SignInSideEffects.UserAuthenticatedSuccessfully)
            updateState {
                it.copy(isLoading = false)
            }
        }

    }

    private fun loginButtonShouldBeEnabled(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && password.length > MIN_PASSWORD_LENGTH
}

data class SignInUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val email: String? = null,
    val password: String? = null,
    val isLoginButtonEnabled: Boolean = false
): UiState<SignInUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): SignInUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface SignInSideEffects: SideEffect {
    data object UserAuthenticatedSuccessfully: SignInSideEffects
}