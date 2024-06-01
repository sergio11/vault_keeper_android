package com.dreamsoftware.vaultkeeper.ui.features.home.components

import androidx.compose.runtime.Composable
import com.dreamsoftware.vaultkeeper.ui.features.home.FilterOptionsEnum

@Composable
fun EmptyListPlaceholder(
    isSearch: Boolean,
    selectedOption: FilterOptionsEnum
) {
    if (isSearch) {
        CommonColumnPlaceHolder(text = "Sorry, Nothing found!")
    } else {
        when (selectedOption) {
            FilterOptionsEnum.ALL -> CommonColumnPlaceHolder(text = "Vault is empty")
            FilterOptionsEnum.PASSWORDS -> CommonColumnPlaceHolder(text = "No Passwords found!")
            FilterOptionsEnum.CARDS -> CommonColumnPlaceHolder(text = "No Cards found!")
        }
    }
}