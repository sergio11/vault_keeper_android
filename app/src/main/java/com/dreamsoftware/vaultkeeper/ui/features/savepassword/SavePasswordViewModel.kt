package com.dreamsoftware.vaultkeeper.ui.features.savepassword

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAccountByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveAccountUseCase
import com.dreamsoftware.vaultkeeper.utils.PasswordUtils
import com.dreamsoftware.vaultkeeper.utils.accountSuggestions
import com.dreamsoftware.vaultkeeper.utils.getRandomNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavePasswordViewModel @Inject constructor(
    private val saveAccountUseCase: SaveAccountUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase
) : BrownieViewModel<SavePasswordUiState, SavePasswordUiSideEffects>(), SavePasswordScreenActionListener {

    override fun onGetDefaultState(): SavePasswordUiState = SavePasswordUiState()

    fun getAccountById(accountId: Int) {

    }

    override fun onResetSuggestions() {
        updateState { it.copy(suggestions = SnapshotStateList()) }
    }

    override fun onAccountNameUpdated(newName: String) {
        updateState { it.copy(
            accountName = newName,
            suggestions = SnapshotStateList()
        ) }
    }

    override fun onFilterByAccountName(name: String) {
        updateState { it.copy(
            accountName = name,
            suggestions = SnapshotStateList<String>().apply {
                if(name.isNotEmpty()) {
                    addAll(accountSuggestions.filter { result -> result.contains(name, true) })
                }
            }
        ) }
    }

    override fun onUsernameUpdated(username: String) {
        updateState { it.copy(username = username) }
    }

    override fun onEmailUpdated(email: String) {
        updateState { it.copy(email = email) }
    }

    override fun onMobileNumberUpdated(mobileNumber: String) {
        updateState { it.copy(mobileNumber = mobileNumber) }
    }

    override fun onPasswordUpdated(password: String) {
        updateState { it.copy(password = password) }
    }

    override fun onNoteUpdated(note: String) {
        updateState { it.copy(note = note) }
    }

    override fun onGenerateRandomPassword() {
        updateState {
            it.copy(password = PasswordUtils.generatePassword(
                length = getRandomNumber(),
                isWithSpecial = true,
                isWithUppercase = true,
                isWithLetters = true,
                isWithNumbers = true
            ))
        }
    }

    override fun onSave() {

    }

    override fun onCancel() {
        launchSideEffect(SavePasswordUiSideEffects.SavePasswordCancelled)
    }
}

data class SavePasswordUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val isEditScreen: Boolean = false,
    val expanded: Boolean = false,
    val accountName: String = String.EMPTY,
    val suggestions: SnapshotStateList<String> = SnapshotStateList(),
    val username: String = String.EMPTY,
    val email: String = String.EMPTY,
    val note: String = String.EMPTY,
    val mobileNumber: String = String.EMPTY,
    val password: String = String.EMPTY,
): UiState<SavePasswordUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): SavePasswordUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface SavePasswordUiSideEffects: SideEffect {
    data object SavePasswordCancelled: SavePasswordUiSideEffects
}