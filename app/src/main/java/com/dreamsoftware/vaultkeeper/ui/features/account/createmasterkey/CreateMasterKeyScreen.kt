package com.dreamsoftware.vaultkeeper.ui.features.account.createmasterkey

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun CreateMasterKeyScreen(
    viewModel: CreateMasterKeyViewModel = hiltViewModel(),
    onMasterKeyCreated: () -> Unit,
    onBackPressed: () -> Unit
) {
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { CreateMasterKeyUiState() },
        onSideEffect = {
            when(it) {
                is CreateMasterKeySideEffects.MasterKeyCreatedSideEffect -> onMasterKeyCreated()
            }
        }
    ) { uiState ->
        CreateMasterKeyScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}