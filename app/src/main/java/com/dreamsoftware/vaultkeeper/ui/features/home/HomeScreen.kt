package com.dreamsoftware.vaultkeeper.ui.features.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onGoToAddNewAccount: () -> Unit,
    onGoToAddNewSecureCard: () -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { HomeUiState() },
        onSideEffect = {
            when(it) {
                HomeSideEffects.AddNewAccountPassword -> onGoToAddNewAccount()
                HomeSideEffects.AddNewSecureCard -> onGoToAddNewSecureCard()
            }
        },
        onInit = { loadData() }
    ) { uiState ->
        HomeScreenContent(
            uiState = uiState,
            clipboardManager = clipboardManager,
            actionListener = viewModel
        )
    }
}