package com.dreamsoftware.vaultkeeper.ui.features.home

import com.dreamsoftware.brownie.component.fab.BrownieFabButtonItem
import com.dreamsoftware.brownie.core.IBrownieScreenActionListener
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO

interface HomeScreenActionListener: IBrownieScreenActionListener {
    fun onDeleteAccount(account: AccountPasswordBO)
    fun onDeleteAccountConfirmed()
    fun onDeleteAccountCancelled()
    fun onDeleteSecureCard(secureCard: SecureCardBO)
    fun onDeleteSecureCardConfirmed()
    fun onDeleteSecureCardCancelled()
    fun onSearchQueryUpdated(newSearchQuery: String)
    fun onFilterOptionUpdated(newFilterOption: FilterOptionsEnum)
    fun onFilterBottomSheetVisibilityUpdated(isVisible: Boolean)
    fun onFabItemClicked(fabButtonItem: BrownieFabButtonItem)
    fun onSecureCardClicked(cardUid: String)
    fun onEditSecureCard(cardUid: String)
    fun onEditAccountPassword(accountUid: String)
    fun onCopyCardNumberToClipboard(cardNumber: String)

    fun onCopyAccountPasswordToClipboard(accountPassword: String)
}