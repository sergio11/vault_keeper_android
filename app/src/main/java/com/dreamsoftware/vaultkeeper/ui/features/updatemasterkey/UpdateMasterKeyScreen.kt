package com.dreamsoftware.vaultkeeper.ui.features.updatemasterkey

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun UpdateMasterKeyScreen(
    viewModel: UpdateMasterKeyViewModel = hiltViewModel(),
    onMasterKeyUpdated: () -> Unit,
    onBackPressed: () -> Unit
) {
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { UpdateMasterKeyUiState() },
        onBackPressed = onBackPressed,
        onSideEffect = {
            when(it) {
                is UpdateMasterKeySideEffects.MasterKeyUpdatedSuccessfully -> onMasterKeyUpdated()
            }
        }
    ) { uiState ->
        UpdateMasterKeyScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}