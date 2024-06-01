package com.dreamsoftware.vaultkeeper.ui.features.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { HomeUiState() },
        onSideEffect = {

        }
    ) { uiState ->
        HomeScreenContent(
            uiState = uiState,
            clipboardManager = clipboardManager,
            actionListener = viewModel
        )
    }
}