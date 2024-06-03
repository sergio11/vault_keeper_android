package com.dreamsoftware.vaultkeeper.ui.features.account.createmasterkey

import android.content.Context
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.vaultkeeper.data.preferences.SharedPrefHelper
import com.dreamsoftware.vaultkeeper.utils.oneShotFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMasterKeyViewModel @Inject constructor(
    private val prefs: SharedPrefHelper,
    @ApplicationContext private val context: Context
) : BrownieViewModel<CreateMasterKeyUiState, CreateMasterKeySideEffects>() {

    // For SharedPrefs
    private val loginKey = prefs.masterKey

    val isUserLoggedIn = prefs.masterKey.isNotEmpty()

    private fun saveUserLoginInfo(value: String) {
        prefs.masterKey = value
    }

    // For Setup Key Screen
    var key by mutableStateOf("")
    var keyVisible by mutableStateOf(false)

    var confirmKey by mutableStateOf("")
    var confirmKeyVisible by mutableStateOf(false)

    var isLoading by mutableStateOf(false)

    val messages = oneShotFlow<String>()

    val navigateToHome = oneShotFlow<Unit>()

    fun validateAndSaveMasterKey() {

        if (key.isEmpty() and confirmKey.isEmpty()) {
            messages.tryEmit("Please enter a Master Key")
            return
        }
        if (key != confirmKey) {
            messages.tryEmit("Please enter correct Master Key")
            return
        }
        if (key.length < 6) {
            messages.tryEmit("A Master Key should at least have 6 characters")
            return
        }

        val isNotEmpty = key.isNotEmpty() and confirmKey.isNotEmpty()
        val isKeySame = key == confirmKey

        if (isNotEmpty and isKeySame) {
            saveUserLoginInfo(confirmKey)
            viewModelScope.launch {
                isLoading = true
                delay(2000)
                isLoading = false
                navigateToHome.tryEmit(Unit)
            }
        }
    }

    //For Unlock Screen
    var unlockKey by mutableStateOf("")
    var unlockKeyVisible by mutableStateOf(false)
    var isLoadingForUnlock by (mutableStateOf(false))

    fun validateAndOpen() {
        if (unlockKey.isEmpty()) {
            messages.tryEmit("Please enter a Master Key")
        } else if (unlockKey == loginKey) {
            viewModelScope.launch {
                isLoadingForUnlock = true
                delay(2000)
                isLoadingForUnlock = false
                navigateToHome.tryEmit(Unit)
            }
        } else {
            messages.tryEmit(
                "Incorrect Master Key," +
                        " Please check again"
            )
        }
    }


    //For Update key
    var oldKey by mutableStateOf("")
    var oldKeyVisible by mutableStateOf(false)

    var newKey by mutableStateOf("")
    var newKeyVisible by mutableStateOf(false)

    var confirmNewKey by mutableStateOf("")
    var confirmNewKeyVisible by mutableStateOf(false)


    fun validateAndUpdateMasterKey() {

        if (oldKey.isEmpty()) {
            messages.tryEmit("Please enter Old Key")
            return
        }
        if (newKey.isEmpty()) {
            messages.tryEmit("Please enter New Master Key")
            return
        }
        if (confirmNewKey.isEmpty()) {
            messages.tryEmit("Please enter Confirm Master Key")
            return
        }
        if (newKey != confirmNewKey) {
            messages.tryEmit("Please enter correct Master Key")
            return
        }
        if (oldKey != loginKey) {
            messages.tryEmit("Please enter correct old key")
            return
        }
        if (oldKey == newKey) {
            messages.tryEmit("New Key and Old Key cannot be the same")
            return
        }

        val isNotEmpty = oldKey.isNotEmpty() and newKey.isNotEmpty() and confirmNewKey.isNotEmpty()
        val isOldKeySame = oldKey == loginKey
        val isKeyNotSame = oldKey != newKey

        if (isNotEmpty and isOldKeySame and isKeyNotSame) {
            saveUserLoginInfo(newKey)
            viewModelScope.launch {
                isLoading = true
                delay(2000)
                isLoading = false
                navigateToHome.tryEmit(Unit)
            }
        }
    }

    // For Biometric

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setAllowedAuthenticators(BIOMETRIC_STRONG)
        .setTitle("Fingerprint Unlock")
        .setSubtitle("Use your fingerprint to unlock Securify")
        .setNegativeButtonText("Cancel")
        .setDeviceCredentialAllowed(true)
        .build()

    fun showSnackBarMsg(msg: String) {
        messages.tryEmit(msg)
    }

    val getState: Boolean
        get() = prefs.getSwitchState()

    override fun onGetDefaultState(): CreateMasterKeyUiState = CreateMasterKeyUiState()
}

data class CreateMasterKeyUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null
): UiState<CreateMasterKeyUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): CreateMasterKeyUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface CreateMasterKeySideEffects: SideEffect {
    data object MasterKeyCreatedSideEffect: CreateMasterKeySideEffects
}