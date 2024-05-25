package com.dreamsoftware.vaultkeeper.ui.features.account.onboarding

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onGoToSignIn: ()  -> Unit,
    onGoToSignUp: ()  -> Unit
) {
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { OnboardingUiState() },
        onSideEffect = {
            when(it) {
                OnboardingSideEffects.NavigateToSignIn -> onGoToSignIn()
                OnboardingSideEffects.NavigateToSignUp -> onGoToSignUp()
                OnboardingSideEffects.NoAuthenticated -> {}
                OnboardingSideEffects.UserAlreadyAuthenticated -> {}
            }
        }
    ) { uiState ->
        OnboardingScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}
