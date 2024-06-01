package com.dreamsoftware.vaultkeeper.ui.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen
import com.dreamsoftware.vaultkeeper.ui.utils.shareApp

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    onGoToCreateMasterKey: () -> Unit,
    onGoToSignIn: () -> Unit
) {
    val context = LocalContext.current
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { SettingsUiState() },
        onSideEffect = {
            when (it) {
                SettingsUiSideEffects.ResetMasterKey -> onGoToCreateMasterKey()
                SettingsUiSideEffects.ShareApp -> context.shareApp()
                SettingsUiSideEffects.SessionDeleted -> onGoToSignIn()
            }
        },
        onInit = {
            onInit()
        }
    ) { uiState ->
        SettingsScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}

