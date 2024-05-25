package com.dreamsoftware.lockbuddy.ui.features.onboarding

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onGoToHome: () -> Unit
) {
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { OnboardingUiState() },
        onSideEffect = {
            when(it) {
                OnboardingSideEffects.OnSignInSuccessfully -> onGoToHome()
            }
        }
    ) { uiState ->
        OnboardingScreenContent()
    }
}
