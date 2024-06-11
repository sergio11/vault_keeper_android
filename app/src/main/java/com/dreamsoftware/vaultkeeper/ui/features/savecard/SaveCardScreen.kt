package com.dreamsoftware.vaultkeeper.ui.features.savecard

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

data class SaveCardScreenArgs(
    val cardUid: String
)

@Composable
fun SaveCardScreen(
    viewModel: SaveCardViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    args: SaveCardScreenArgs? = null
) {
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { SaveCardUiState() },
        onSideEffect = {
            when(it) {
                SaveCardUiSideEffects.SaveSecureCardCancelled -> onBackPressed()
            }
        },
        onInit = {
            args?.cardUid?.let(::getCardById)
        }
    ) { uiState ->
        SaveCardScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}