package com.dreamsoftware.lockbuddy.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dreamsoftware.lockbuddy.data.database.dao.AccountDao
import com.dreamsoftware.lockbuddy.data.database.entity.AccountEntity
import com.dreamsoftware.lockbuddy.data.database.dao.CardDao
import com.dreamsoftware.lockbuddy.data.database.entity.CardEntity
import com.dreamsoftware.lockbuddy.service.encryption.EncryptionManager
import com.dreamsoftware.lockbuddy.util.AccountOrCard
import com.dreamsoftware.lockbuddy.util.oneShotFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dbAccount: AccountDao,
    private val dbCard: CardDao,
    private val encryptionManager: EncryptionManager
) : ViewModel() {

    val messages = oneShotFlow<String>()

    var selectedOption by mutableStateOf("All")

    val filterOptions = listOf(
        "All",
        "Passwords",
        "Cards"
    )

    var showAccountDeleteDialog by mutableStateOf(false)

    var showCardDeleteDialog by mutableStateOf(false)

    var accountToDelete by mutableStateOf(
        AccountEntity(
            -1,
            "",
            "",
            "",
            "",
            "",
            "",
            0L
        )
    )

    var cardToDelete by mutableStateOf(
        CardEntity(
            -1,
            "",
            "",
            "",
            "",
            "",
            0L
        )
    )

    private val _searchQuery = MutableStateFlow("")
    private val searchQuery: StateFlow<String> get() = _searchQuery

    val combinedData: Flow<List<AccountOrCard>> = combine(
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
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun onUserAccountDeleteClick(accountEntity: AccountEntity) {
        accountToDelete = accountEntity
        showAccountDeleteDialog = true
    }

    fun deleteAccount() {
        viewModelScope.launch {
            dbAccount.deleteAccount(accountToDelete)
            showAccountDeleteDialog = false
        }
    }

    fun onUserCardDeleteClick(cardEntity: CardEntity) {
        cardToDelete = cardEntity
        showCardDeleteDialog = true
    }

    fun deleteCard() {
        viewModelScope.launch {
            dbCard.deleteCard(cardToDelete)
            showCardDeleteDialog = false
        }
    }

    fun decryptInput(input: String): String {
        return encryptionManager.decrypt(input)
    }

    fun showCopyMsg(stringType: String) {
        messages.tryEmit("$stringType copied to clipboard.")
    }

}