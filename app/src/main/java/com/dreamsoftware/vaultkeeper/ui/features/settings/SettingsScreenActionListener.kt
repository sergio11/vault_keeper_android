package com.dreamsoftware.vaultkeeper.ui.features.settings

interface SettingsScreenActionListener {
    fun onUpdateSheetVisibility(isVisible: Boolean)
    fun onUpdateCloseSessionDialogVisibility(isVisible: Boolean)
    fun onSettingItemClicked(item: SettingsItem)
    fun onCloseSession()
}