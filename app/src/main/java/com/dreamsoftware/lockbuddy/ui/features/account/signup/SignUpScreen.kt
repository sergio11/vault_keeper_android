package com.dreamsoftware.lockbuddy.ui.features.account.signup

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onGoToSignIn: () -> Unit,
    onBackPressed: () -> Unit,
) {
    BrownieScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { SignUpUiState() },
        onSideEffect = {
            when(it) {
                SignUpSideEffects.NavigateToSignIn -> onGoToSignIn()
            }
        }
    ) { uiState ->
        SignUpScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}