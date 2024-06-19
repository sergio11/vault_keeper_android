package com.dreamsoftware.vaultkeeper.ui.features.home.components

import androidx.compose.runtime.Composable
import com.dreamsoftware.brownie.component.BrownieColumnPlaceHolder
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.features.home.FilterOptionsEnum

@Composable
fun EmptyListPlaceholder(
    isSearch: Boolean,
    selectedOption: FilterOptionsEnum
) {
    if (isSearch) {
        BrownieColumnPlaceHolder(
            titleRes = R.string.nothing_found,
            iconRes = R.drawable.img_empty_box
        )
    } else {
        when (selectedOption) {
            FilterOptionsEnum.ALL -> BrownieColumnPlaceHolder(
                titleRes = R.string.vault_empty,
                iconRes = R.drawable.img_empty_box
            )
            FilterOptionsEnum.PASSWORDS -> BrownieColumnPlaceHolder(
                titleRes = R.string.no_passwords_found,
                iconRes = R.drawable.img_empty_box
            )
            FilterOptionsEnum.CARDS -> BrownieColumnPlaceHolder(
                titleRes = R.string.no_cards_found,
                iconRes = R.drawable.img_empty_box
            )
        }
    }
}