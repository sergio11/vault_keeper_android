package com.dreamsoftware.vaultkeeper.ui.features.carddetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

data class SecureCardDetailScreenArgs(
    val cardUid: String
)

@Composable
fun SecureCardDetailScreen(
    viewModel: SecureCardDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    args: SecureCardDetailScreenArgs? = null
) {
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { SecureCardDetailUiState() },
        onSideEffect = {
            when (it) {
                SecureCardDetailSideEffects.Cancelled -> onBackPressed()
            }
        },
        onInit = {
            args?.cardUid?.let(::getCardById)
        }
    ) { uiState ->
        SecureCardDetailScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}