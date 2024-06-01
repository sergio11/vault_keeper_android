package com.dreamsoftware.vaultkeeper.ui.features.home

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.data.database.dao.AccountDao
import com.dreamsoftware.vaultkeeper.data.database.dao.CardDao
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.model.ICredentialBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.ui.core.components.fab.FabButtonItem
import com.dreamsoftware.vaultkeeper.ui.core.components.fab.FabButtonMain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dbAccount: AccountDao,
    private val dbCard: CardDao
) : BrownieViewModel<HomeUiState, HomeSideEffects>(), HomeScreenActionListener {

    private companion object {
        const val ADD_CARD_BUTTON_ID = 1
        const val ADD_PASSWORD_BUTTON_ID = 2
    }

    override fun onGetDefaultState(): HomeUiState = HomeUiState(
        fabButtonItemList = listOf(
            FabButtonItem(id = ADD_CARD_BUTTON_ID, iconRes = R.drawable.icon_card, label = "Add Card"),
            FabButtonItem(id = ADD_PASSWORD_BUTTON_ID, iconRes = R.drawable.icon_pass, label = "Add Password")
        )
    )

    /*val combinedData: Flow<List<AccountOrCard>> = combine(
        dbAccount.getAllAccounts(),
        dbCard.getAllCards(),
        searchQuery
    ) { accounts, cards, query ->

        val itemsWithTimestamp = mutableStateListOf<Pair<AccountOrCard, Long>>()

        accounts.forEach { item -> itemsWithTimestamp.add(AccountOrCard.AccountItem(item) to item.createdAt) }
        cards.forEach { item -> itemsWithTimestamp.add(AccountOrCard.CardItem(item) to item.createdAt) }

        val sortedItems = itemsWithTimestamp.sortedByDescending { it.second }

        val filteredItems = if (query.isNotBlank()) {
            sortedItems.filter { (item, _) ->
                when (selectedOption) {
                    "All" -> true
                    "Passwords" -> item is AccountOrCard.AccountItem
                    "Cards" -> item is AccountOrCard.CardItem
                    else -> true
                } &&
                when (item) {
                    is AccountOrCard.AccountItem -> {
                        val account = item.account
                        account.accountName.contains(query, ignoreCase = true) ||
                                decryptInput(account.userName).contains(query, ignoreCase = true) ||
                                decryptInput(account.email).contains(query, ignoreCase = true) ||
                                decryptInput(account.mobileNumber).contains(query, ignoreCase = true)
                    }

                    is AccountOrCard.CardItem -> {
                        val card = item.card
                        decryptInput(card.cardHolderName).contains(query, ignoreCase = true) ||
                                decryptInput(card.cardNumber).contains(query, ignoreCase = true) ||
                                card.cardProvider.contains(query, ignoreCase = true)
                    }
                }
            }
        } else {
            sortedItems
        }
        filteredItems.map { it.first }
    }*/

    override fun onDeleteAccount(account: AccountBO) {
       updateState {
           it.copy(
               showAccountDeleteDialog = true,
               accountToDelete = account
           )
       }
    }

    override fun onDeleteAccountConfirmed() {
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
    }

    override fun onFilterOptionUpdated(newFilterOption: FilterOptionsEnum) {
        updateState { it.copy(
            selectedOption = newFilterOption,
            searchQuery = String.EMPTY,
            showSheet = false
        ) }
    }

    override fun onFabItemClicked(fabButtonItem: FabButtonItem) {
        when (fabButtonItem.id) {
            ADD_CARD_BUTTON_ID -> launchSideEffect(HomeSideEffects.AddNewSecureCard)
            ADD_PASSWORD_BUTTON_ID -> launchSideEffect(HomeSideEffects.AddNewAccountPassword)
            else -> {
                // To handle other cases if needed
            }
        }
    }
}

data class HomeUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val selectedOption: FilterOptionsEnum = FilterOptionsEnum.ALL,
    val filterOptions: List<FilterOptionsEnum> = FilterOptionsEnum.entries,
    val accountToDelete: AccountBO? = null,
    val showAccountDeleteDialog: Boolean = false,
    val secureCardToDelete: SecureCardBO? = null,
    val showCardDeleteDialog: Boolean = false,
    val showSheet: Boolean = false,
    val searchQuery: String = String.EMPTY,
    val fabButtonItemList: List<FabButtonItem> = emptyList(),
    val fabButtonMain: FabButtonMain = FabButtonMain(R.drawable.icon_add),
    val credentials: List<ICredentialBO> = emptyList()
): UiState<HomeUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): HomeUiState =
        copy(isLoading = isLoading, error = error)
}

enum class FilterOptionsEnum {
    ALL, PASSWORDS, CARDS
}

sealed interface HomeSideEffects: SideEffect {
    data object AddNewSecureCard: HomeSideEffects
    data object AddNewAccountPassword: HomeSideEffects
}