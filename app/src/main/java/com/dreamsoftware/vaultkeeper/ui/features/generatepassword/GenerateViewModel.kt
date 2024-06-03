package com.dreamsoftware.vaultkeeper.ui.features.generatepassword

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.domain.service.IPasswordGeneratorService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenerateViewModel @Inject constructor(
    private val passwordGenerator: IPasswordGeneratorService
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
                it.copy(message = "Please toggle at least one option.")
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
        updateState { it.copy(message = "Password copied to clipboard.") }
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
    override val error: String? = null,
    val message: String? = null,
    val password: String = String.EMPTY,
    val passwordLength: Int = 12,
    val lowerCase: Boolean = true,
    val upperCase: Boolean = true,
    val digits: Boolean = true,
    val specialCharacters: Boolean = true
): UiState<GenerateUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): GenerateUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface GenerateSideEffects: SideEffect