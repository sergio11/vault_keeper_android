package com.dreamsoftware.vaultkeeper.ui.features.savepassword

import com.dreamsoftware.brownie.component.BrownieDropdownMenuItem
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.di.SavePasswordErrorMapper
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.domain.service.IPasswordGeneratorService
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAccountByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveAccountUseCase
import com.dreamsoftware.vaultkeeper.utils.IApplicationAware
import com.dreamsoftware.vaultkeeper.utils.getRandomNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavePasswordViewModel @Inject constructor(
    private val saveAccountUseCase: SaveAccountUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val passwordGeneratorService: IPasswordGeneratorService,
    @SavePasswordErrorMapper private val errorMapper: IBrownieErrorMapper,
    private val applicationAware: IApplicationAware
) : BrownieViewModel<SavePasswordUiState, SavePasswordUiSideEffects>(), SavePasswordScreenActionListener {

    fun getAccountById(accountUid: String) {
        executeUseCaseWithParams(
            useCase = getAccountByIdUseCase,
            params = GetAccountByIdUseCase.Params(uid = accountUid),
            onSuccess = ::onFetchAccountDetailsSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onGetDefaultState(): SavePasswordUiState = SavePasswordUiState(
        accountSuggestionsMenuItems = getAccountProviderSuggestions()
    )

    override fun onResetSuggestions() {
        updateState { it.copy(accountSuggestionsMenuItems = getAccountProviderSuggestions()) }
    }

    override fun onAccountNameUpdated(newName: String) {
        updateState { it.copy(
            accountName = newName,
            accountSuggestionsMenuItems = getAccountProviderSuggestions()
        ) }
    }

    override fun onFilterByAccountName(name: String) {
        updateState { it.copy(
            accountName = name,
            accountSuggestionsMenuItems = getAccountProviderSuggestions().filter { account ->
                account.textRes?.let(applicationAware::getString)?.lowercase()?.contains(name.lowercase()) ?: false
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
            it.copy(password = passwordGeneratorService.generatePassword(
                length = getRandomNumber(),
                isWithSpecial = true,
                isWithUppercase = true,
                isWithLetters = true,
                isWithNumbers = true
            ))
        }
    }

    override fun onSave() {
        with(uiState.value) {
            executeUseCaseWithParams(
                useCase = saveAccountUseCase,
                params = SaveAccountUseCase.Params(
                    uid = accountUid,
                    accountName = accountName,
                    userName = username,
                    email = email,
                    mobileNumber = mobileNumber,
                    password = password,
                    note = note
                ),
                onSuccess = ::onAccountSavedSuccessfully,
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
    }

    override fun onCancel() {
        launchSideEffect(SavePasswordUiSideEffects.SavePasswordCancelled)
    }

    private fun onFetchAccountDetailsSuccessfully(account: AccountPasswordBO) {
        updateState {
            it.copy(
                isEditScreen = true,
                accountUid = account.uid,
                accountName = account.accountName,
                username = account.userName,
                email = account.email,
                note = account.note,
                mobileNumber = account.mobileNumber,
                password = account.password
            )
        }
    }

    private fun onAccountSavedSuccessfully(account: AccountPasswordBO) {
        updateState {
            it.copy(
                isEditScreen = true,
                accountUid = account.uid,
                accountName = account.accountName,
                username = account.userName,
                email = account.email,
                note = account.note,
                mobileNumber = account.mobileNumber,
                password = account.password
            )
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SavePasswordUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    private fun getAccountProviderSuggestions() =
        listOf(
            BrownieDropdownMenuItem(textRes = R.string.amazon_prime),
            BrownieDropdownMenuItem(textRes = R.string.behance),
            BrownieDropdownMenuItem(textRes = R.string.discord),
            BrownieDropdownMenuItem(textRes = R.string.dribbble),
            BrownieDropdownMenuItem(textRes = R.string.facebook),
            BrownieDropdownMenuItem(textRes = R.string.gmail),
            BrownieDropdownMenuItem(textRes = R.string.github),
            BrownieDropdownMenuItem(textRes = R.string.instagram),
            BrownieDropdownMenuItem(textRes = R.string.medium),
            BrownieDropdownMenuItem(textRes = R.string.messenger),
            BrownieDropdownMenuItem(textRes = R.string.netflix),
            BrownieDropdownMenuItem(textRes = R.string.pinterest),
            BrownieDropdownMenuItem(textRes = R.string.quora),
            BrownieDropdownMenuItem(textRes = R.string.reddit),
            BrownieDropdownMenuItem(textRes = R.string.snapchat),
            BrownieDropdownMenuItem(textRes = R.string.spotify),
            BrownieDropdownMenuItem(textRes = R.string.stackoverflow),
            BrownieDropdownMenuItem(textRes = R.string.tumblr),
            BrownieDropdownMenuItem(textRes = R.string.twitter),
            BrownieDropdownMenuItem(textRes = R.string.whatsapp),
            BrownieDropdownMenuItem(textRes = R.string.wordpress),
            BrownieDropdownMenuItem(textRes = R.string.youtube),
        )
}

data class SavePasswordUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val isEditScreen: Boolean = false,
    val expanded: Boolean = false,
    val accountUid: String? = null,
    val accountName: String = String.EMPTY,
    val username: String = String.EMPTY,
    val email: String = String.EMPTY,
    val note: String = String.EMPTY,
    val mobileNumber: String = String.EMPTY,
    val password: String = String.EMPTY,
    val accountSuggestionsMenuItems: List<BrownieDropdownMenuItem> = emptyList()
): UiState<SavePasswordUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SavePasswordUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface SavePasswordUiSideEffects: SideEffect {
    data object SavePasswordCancelled: SavePasswordUiSideEffects
}