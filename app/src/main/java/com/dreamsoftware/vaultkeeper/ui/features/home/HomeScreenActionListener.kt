package com.dreamsoftware.vaultkeeper.ui.features.home

import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.ui.core.components.fab.FabButtonItem

interface HomeScreenActionListener {
    fun onDeleteAccount(account: AccountBO)
    fun onDeleteAccountConfirmed()
    fun onDeleteAccountCancelled()
    fun onDeleteSecureCard(secureCard: SecureCardBO)
    fun onDeleteSecureCardConfirmed()
    fun onDeleteSecureCardCancelled()
    fun onSearchQueryUpdated(newSearchQuery: String)
    fun onFilterOptionUpdated(newFilterOption: FilterOptionsEnum)
    fun onFabItemClicked(fabButtonItem: FabButtonItem)
}