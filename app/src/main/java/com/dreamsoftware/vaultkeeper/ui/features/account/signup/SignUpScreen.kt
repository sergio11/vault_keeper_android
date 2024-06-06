package com.dreamsoftware.vaultkeeper.ui.features.account.signup

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onGoToSignIn: () -> Unit,
    onGoToCreateMasterKey: () -> Unit,
    onBackPressed: () -> Unit,
) {
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { SignUpUiState() },
        onSideEffect = {
            when(it) {
                SignUpSideEffects.NavigateToSignIn -> onGoToSignIn()
                SignUpSideEffects.RegisteredSuccessfully -> onGoToCreateMasterKey()
            }
        }
    ) { uiState ->
        SignUpScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}