package com.dreamsoftware.vaultkeeper.ui.features.savepassword

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

data class SaveAccountPasswordScreenArgs(
    val accountUid: String
)

@Composable
fun SavePasswordScreen(
    viewModel: SavePasswordViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    args: SaveAccountPasswordScreenArgs? = null
) {
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { SavePasswordUiState() },
        onSideEffect = {
            when(it) {
                SavePasswordUiSideEffects.SavePasswordCancelled -> onBackPressed()
            }
        },
        onInit = {
            args?.accountUid?.let(::getAccountById)
        }
    ) { uiState ->
        SavePasswordScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}