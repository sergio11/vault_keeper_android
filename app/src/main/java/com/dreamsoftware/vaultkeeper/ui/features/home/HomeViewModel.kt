package com.dreamsoftware.vaultkeeper.ui.features.home

import com.dreamsoftware.brownie.component.fab.BrownieFabButtonItem
import com.dreamsoftware.brownie.component.fab.BrownieFabButtonMain
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.di.HomeErrorMapper
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.domain.model.ICredentialBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllAccountsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllCardsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAllCredentialsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemovePasswordAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveSecureCardUseCase
import com.dreamsoftware.vaultkeeper.ui.utils.obfuscateSecret
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val getAllCredentialsUseCase: GetAllCredentialsUseCase,
    private val removeSecureCardUseCase: RemoveSecureCardUseCase,
    private val removePasswordAccountUseCase: RemovePasswordAccountUseCase,
    private val applicationAware: IVaultKeeperApplicationAware,
    @HomeErrorMapper private val errorMapper: IBrownieErrorMapper
) : BrownieViewModel<HomeUiState, HomeSideEffects>(), HomeScreenActionListener {

    private companion object {
        const val ADD_CARD_BUTTON_ID = 1
        const val ADD_PASSWORD_BUTTON_ID = 2
    }

    fun loadData() {
        onLoadData()
    }

    override fun onGetDefaultState(): HomeUiState = HomeUiState(
        fabButtonItemList = listOf(
            BrownieFabButtonItem(id = ADD_CARD_BUTTON_ID, iconRes = R.drawable.icon_card, label = "Add Card"),
            BrownieFabButtonItem(id = ADD_PASSWORD_BUTTON_ID, iconRes = R.drawable.icon_pass, label = "Add Password")
        )
    )

    override fun onDeleteAccount(account: AccountPasswordBO) {
       updateState {
           it.copy(
               showAccountDeleteDialog = true,
               accountToDelete = account
           )
       }
    }

    override fun onDeleteAccountConfirmed() {
        uiState.value.accountToDelete?.let { passwordAccount ->
            executeUseCaseWithParams(
                useCase = removePasswordAccountUseCase,
                params = RemovePasswordAccountUseCase.Params(uid = passwordAccount.uid),
                onSuccess = {
                    onCredentialsDeletedSuccessfully(passwordAccount.uid)
                },
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
        updateState {
            it.copy(
                showAccountDeleteDialog = false,
                accountToDelete = null
            )
        }
    }

    override fun onDeleteAccountCancelled() {
        updateState {
            it.copy(
                showAccountDeleteDialog = false,
                accountToDelete = null
            )
        }
    }

    override fun onDeleteSecureCard(secureCard: SecureCardBO) {
        updateState {
            it.copy(
                showCardDeleteDialog = true,
                secureCardToDelete = secureCard
            )
        }
    }

    override fun onDeleteSecureCardConfirmed() {
        uiState.value.secureCardToDelete?.let { secureCard ->
            executeUseCaseWithParams(
                useCase = removeSecureCardUseCase,
                params = RemoveSecureCardUseCase.Params(uid = secureCard.uid),
                onSuccess = {
                    onCredentialsDeletedSuccessfully(secureCard.uid)
                },
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
        updateState {
            it.copy(
                showCardDeleteDialog = false,
                secureCardToDelete = null
            )
        }
    }

    override fun onDeleteSecureCardCancelled() {
        updateState {
            it.copy(
                showCardDeleteDialog = false,
                secureCardToDelete = null
            )
        }
    }

    override fun onSearchQueryUpdated(newSearchQuery: String) {
        updateState { it.copy(searchQuery = newSearchQuery) }
        onLoadData()
    }

    override fun onFilterOptionUpdated(newFilterOption: FilterOptionsEnum) {
        updateState { it.copy(
            selectedOption = newFilterOption,
            searchQuery = String.EMPTY,
            showSheet = false
        ) }
        onLoadData()
    }

    override fun onFilterBottomSheetVisibilityUpdated(isVisible: Boolean) {
        updateState { it.copy(showSheet = isVisible) }
    }

    override fun onFabItemClicked(fabButtonItem: BrownieFabButtonItem) {
        when (fabButtonItem.id) {
            ADD_CARD_BUTTON_ID -> launchSideEffect(HomeSideEffects.AddNewSecureCard)
            ADD_PASSWORD_BUTTON_ID -> launchSideEffect(HomeSideEffects.AddNewAccountPassword)
            else -> {
                // To handle other cases if needed
            }
        }
    }

    override fun onEditSecureCard(cardUid: String) {
        launchSideEffect(HomeSideEffects.EditSecureCard(cardUid))
    }

    override fun onEditAccountPassword(accountUid: String) {
        launchSideEffect(HomeSideEffects.EditAccountPassword(accountUid))
    }

    override fun onCopyCardNumberToClipboard(cardNumber: String) {
        updateState {
            it.copy(infoMessage = applicationAware.getString(R.string.copy_secure_card_to_clipboard, cardNumber.obfuscateSecret(4)))
        }
        launchSideEffect(HomeSideEffects.CopyTextToClipboard(cardNumber))
    }

    override fun onCopyAccountPasswordToClipboard(accountPassword: String) {
        updateState {
            it.copy(infoMessage = applicationAware.getString(R.string.copy_account_password_to_clipboard))
        }
        launchSideEffect(HomeSideEffects.CopyTextToClipboard(accountPassword))
    }

    private fun onLoadData() {
        with(uiState.value) {
            when(selectedOption) {
                FilterOptionsEnum.ALL -> onLoadAllCredentials()
                FilterOptionsEnum.PASSWORDS -> onLoadAllAccounts()
                FilterOptionsEnum.CARDS -> onLoadAllSecureCards()
            }
        }
    }

    private fun onLoadAllAccounts() {
        executeUseCaseWithParams(
            useCase = getAllAccountsUseCase,
            params = GetAllAccountsUseCase.Params(
                term = uiState.value.searchQuery
            ),
            onSuccess = ::onLoadCredentialsSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onLoadAllSecureCards() {
        executeUseCaseWithParams(
            useCase = getAllCardsUseCase,
            params = GetAllCardsUseCase.Params(
                term = uiState.value.searchQuery
            ),
            onSuccess = ::onLoadCredentialsSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onLoadAllCredentials() {
        executeUseCaseWithParams(
            useCase = getAllCredentialsUseCase,
            params = GetAllCredentialsUseCase.Params(
                term = uiState.value.searchQuery
            ),
            onSuccess = ::onLoadCredentialsSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onCredentialsDeletedSuccessfully(credentialUid: String) {
        updateState { it.copy(credentials = it.credentials.filter { card -> card.uid != credentialUid }) }
    }

    private fun onLoadCredentialsSuccessfully(credentials: List<ICredentialBO>) {
        updateState { it.copy(credentials = credentials) }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: HomeUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    override fun onInfoMessageCleared() {
        updateState { it.copy(infoMessage = null) }
    }
}

data class HomeUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val infoMessage: String? = null,
    val selectedOption: FilterOptionsEnum = FilterOptionsEnum.ALL,
    val filterOptions: List<FilterOptionsEnum> = FilterOptionsEnum.entries,
    val accountToDelete: AccountPasswordBO? = null,
    val showAccountDeleteDialog: Boolean = false,
    val secureCardToDelete: SecureCardBO? = null,
    val showCardDeleteDialog: Boolean = false,
    val showSheet: Boolean = false,
    val searchQuery: String = String.EMPTY,
    val fabButtonItemList: List<BrownieFabButtonItem> = emptyList(),
    val fabButtonMain: BrownieFabButtonMain = BrownieFabButtonMain(R.drawable.icon_add),
    val credentials: List<ICredentialBO> = emptyList()
): UiState<HomeUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): HomeUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

enum class FilterOptionsEnum {
    ALL, PASSWORDS, CARDS
}

sealed interface HomeSideEffects: SideEffect {
    data object AddNewSecureCard: HomeSideEffects
    data object AddNewAccountPassword: HomeSideEffects
    data class EditSecureCard(val cardUid: String): HomeSideEffects
    data class EditAccountPassword(val accountUid: String): HomeSideEffects
    data class CopyTextToClipboard(val text: String): HomeSideEffects
}