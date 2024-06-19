package com.dreamsoftware.vaultkeeper.ui.features.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onGoToAddNewAccount: () -> Unit,
    onGoToAddNewSecureCard: () -> Unit,
    onGoToEditSecureCard: (cardUid: String) -> Unit,
    onGoToEditAccountPassword: (accountUid: String) -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { HomeUiState() },
        onSideEffect = {
            when(it) {
                HomeSideEffects.AddNewAccountPassword -> onGoToAddNewAccount()
                HomeSideEffects.AddNewSecureCard -> onGoToAddNewSecureCard()
                is HomeSideEffects.EditSecureCard -> onGoToEditSecureCard(it.cardUid)
                is HomeSideEffects.EditAccountPassword -> onGoToEditAccountPassword(it.accountUid)
                is HomeSideEffects.CopyTextToClipboard -> {
                    clipboardManager.setText(AnnotatedString(it.text))
                }
            }
        },
        onInit = { loadData() }
    ) { uiState ->
        HomeScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}