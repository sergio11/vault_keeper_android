package com.dreamsoftware.vaultkeeper.ui.features.generatepassword

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.service.IPasswordGeneratorService
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenerateViewModel @Inject constructor(
    private val passwordGenerator: IPasswordGeneratorService,
    private val applicationAware: IVaultKeeperApplicationAware
) : BrownieViewModel<GenerateUiState, GenerateSideEffects>(), GeneratePasswordScreenActionListener {

    override fun onGetDefaultState(): GenerateUiState = GenerateUiState()
    override fun onPasswordLength(newLength: Int) {
        updateState { it.copy(passwordLength = newLength) }
    }

    override fun onLowerCaseChanged(hasLowerCase: Boolean) {
        updateState { it.copy(lowerCase = hasLowerCase) }
    }

    override fun onUpperCaseChanged(hasUpperCase: Boolean) {
        updateState { it.copy(upperCase = hasUpperCase) }
    }

    override fun onDigitsChanged(hasDigits: Boolean) {
        updateState { it.copy(digits = hasDigits) }
    }

    override fun onSpecialCharactersChanged(hasSpecialCharacters: Boolean) {
        updateState { it.copy(specialCharacters = hasSpecialCharacters) }
    }

    override fun onValidateAndSave() {
        updateState {
            if (!(it.lowerCase || it.upperCase || it.digits || it.specialCharacters)) {
                it.copy(errorMessage = applicationAware.getString(R.string.generator_password_screen_invalid_options))
            } else {
                it.copy(password = passwordGenerator.generatePassword(
                    length = it.passwordLength,
                    isWithSpecial = it.specialCharacters,
                    isWithUppercase = it.upperCase,
                    isWithLetters = it.lowerCase,
                    isWithNumbers = it.digits
                ))
            }
        }
    }

    override fun onPasswordCopied() {
        updateState { it.copy(infoMessage = applicationAware.getString(R.string.generator_password_screen_copy_password_clipboard)) }
    }

    fun generateInitialPassword() {
        updateState {
            it.copy(password = passwordGenerator.generatePassword(
                length = it.passwordLength,
                isWithSpecial = it.specialCharacters,
                isWithUppercase = it.upperCase,
                isWithLetters = it.lowerCase,
                isWithNumbers = it.digits
            ))
        }
    }
}

data class GenerateUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val infoMessage: String? = null,
    val password: String = String.EMPTY,
    val passwordLength: Int = 12,
    val lowerCase: Boolean = true,
    val upperCase: Boolean = true,
    val digits: Boolean = true,
    val specialCharacters: Boolean = true
): UiState<GenerateUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): GenerateUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface GenerateSideEffects: SideEffect {
    data class CopyPasswordToClipboard(val password: String): GenerateSideEffects
}