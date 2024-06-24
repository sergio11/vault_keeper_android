package com.dreamsoftware.vaultkeeper.ui.features.accountpassworddetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

data class AccountPasswordDetailScreenArgs(
    val accountPasswordUid: String
)

@Composable
fun AccountPasswordDetailScreen(
    viewModel: AccountPasswordDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    args: AccountPasswordDetailScreenArgs? = null
) {
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { AccountPasswordDetailUiState() },
        onSideEffect = {
            when (it) {
                AccountPasswordDetailSideEffects.Cancelled -> onBackPressed()
                AccountPasswordDetailSideEffects.AccountPasswordDeleted -> onBackPressed()
            }
        },
        onInit = {
            args?.accountPasswordUid?.let(::getAccountPasswordById)
        }
    ) { uiState ->
        AccountPasswordDetailScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}