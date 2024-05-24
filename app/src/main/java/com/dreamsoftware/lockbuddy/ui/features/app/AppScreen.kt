package com.dreamsoftware.lockbuddy.ui.features.app

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun AppScreen(
    viewModel: AppViewModel = hiltViewModel(),
) {
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { AppUiState() }
    ) { uiState ->
        AppScreenContent(
            uiState = uiState
        )
    }
}