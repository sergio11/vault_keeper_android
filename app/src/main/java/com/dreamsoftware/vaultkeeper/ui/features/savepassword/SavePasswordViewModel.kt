package com.dreamsoftware.vaultkeeper.ui.features.savepassword

import android.util.Patterns
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.data.database.dao.AccountDao
import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.utils.accountSuggestions
import com.dreamsoftware.vaultkeeper.utils.generatePassword
import com.dreamsoftware.vaultkeeper.utils.getRandomNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavePasswordViewModel @Inject constructor(
    private val db: AccountDao
) : BrownieViewModel<SavePasswordUiState, SavePasswordUiSideEffects>(), SavePasswordScreenActionListener {

    override fun onGetDefaultState(): SavePasswordUiState = SavePasswordUiState()

    fun getAccountById(accountId: Int) {
        viewModelScope.launch {
            db.getAccountById(accountId).collect {
                accountName = it.accountName
                //username = encryptionManager.decrypt(it.userName)
                //email = encryptionManager.decrypt(it.email)
                //mobileNumber = encryptionManager.decrypt(it.mobileNumber)
                //password = encryptionManager.decrypt(it.password)
            }
        }
    }

    private fun validateFields(): Boolean {
        if (accountName.isBlank()) {
            messages.tryEmit("Please provide an account name")
            return false
        }
        if (username.isEmpty() && email.isBlank() && mobileNumber.isBlank()) {
            messages.tryEmit("Please provide a username, email, or mobile number")
            return false
        }
        if (password.isBlank()) {
            messages.tryEmit("Password cannot be empty")
            return false
        }
        if (password.trim().isEmpty() || password.contains("\\s+".toRegex())) {
            messages.tryEmit("Password cannot contain whitespace")
            return false
        }
        if (email.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            messages.tryEmit("Invalid email address")
            return false
        }
        if (!mobileNumber.isDigitsOnly()) {
            messages.tryEmit("Invalid mobile number")
            return false
        }
        return true
    }

    fun validateAndInsert() {

        if (validateFields()) {
            val currentTimeInMillis = System.currentTimeMillis()
            val account = AccountEntity(
                id = 0,
                accountName = accountName.trim(),
                userName = username,
                email = email,
                mobileNumber = mobileNumber,
                password = password,
                note = note.trim(),
                createdAt = currentTimeInMillis
            )

            viewModelScope.launch {
                db.insertAccount(account)
                messages.tryEmit("Credentials Added!")
                success.value = true
            }
        }

    }

    fun validationAndUpdate(id: Int) {
        if (validateFields()) {
            viewModelScope.launch {
                val currentTimeInMillis = System.currentTimeMillis()
                val accountEntity = AccountEntity(
                    id = id,
                    accountName = accountName.trim(),
                    userName = username.trim(),
                    email = email.trim(),
                    mobileNumber = mobileNumber,
                    password = password.trim(),
                    note = note.trim(),
                    createdAt = currentTimeInMillis
                )
                db.updateAccount(accountEntity)
                messages.tryEmit("Successfully Updated!")
                success.value = true
            }
        }
    }
    fun filter(accountName: String) {
        suggestions.clear()
        if (accountName.isNotEmpty()) {
            suggestions.addAll(accountSuggestions.filter { it.contains(accountName, true) })
        }
    }

    fun resetSuggestions() {
        suggestions.clear()
    }

    fun generateRandomPassword() {
        password = generatePassword(
            length = getRandomNumber(),
            lowerCase = true,
            upperCase = true,
            digits = true,
            specialCharacters = true
        )
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
        onAccountNameUpdated(name)
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

    }

    override fun onSave() {

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

sealed interface SavePasswordUiSideEffects: SideEffect