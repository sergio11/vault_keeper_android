package com.dreamsoftware.vaultkeeper.ui.features.unlock

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun UnlockScreen(
    viewModel: UnlockScreenViewModel = hiltViewModel(),
    onUnlocked: () -> Unit
) {
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { UnlockScreenUiState() },
        onSideEffect = {

        }
    ) { uiState ->
        UnlockScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}