package com.dreamsoftware.vaultkeeper.ui.features.home

import com.dreamsoftware.brownie.component.fab.BrownieFabButtonItem
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO

interface HomeScreenActionListener {
    fun onDeleteAccount(account: AccountBO)
    fun onDeleteAccountConfirmed()
    fun onDeleteAccountCancelled()
    fun onDeleteSecureCard(secureCard: SecureCardBO)
    fun onDeleteSecureCardConfirmed()
    fun onDeleteSecureCardCancelled()
    fun onSearchQueryUpdated(newSearchQuery: String)
    fun onFilterOptionUpdated(newFilterOption: FilterOptionsEnum)
    fun onFilterBottomSheetVisibilityUpdated(isVisible: Boolean)
    fun onFabItemClicked(fabButtonItem: BrownieFabButtonItem)
}