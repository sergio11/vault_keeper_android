package com.dreamsoftware.vaultkeeper.ui.features.account.signin

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onAuthenticated: () -> Unit,
    onBackPressed: () -> Unit,
) {
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { SignInUiState() },
        onSideEffect = {
            when(it) {
                SignInSideEffects.UserAuthenticatedSuccessfully -> onAuthenticated()
            }
        }
    ) { uiState ->
        SignInScreenContent(
            uiState = uiState,
            actionsListener = viewModel
        )
    }
}