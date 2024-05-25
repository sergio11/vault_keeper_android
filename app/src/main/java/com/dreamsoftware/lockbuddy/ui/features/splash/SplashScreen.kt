package com.dreamsoftware.lockbuddy.ui.features.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onGoToOnboarding: () -> Unit,
    onGoToHome: () -> Unit
) {
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { SplashUiState() },
        onSideEffect = {
            when(it) {
                SplashSideEffects.UserAlreadyAuthenticated -> onGoToHome()
                SplashSideEffects.UserNotAuthenticated -> onGoToOnboarding()
            }
        },
        onInit = {
            verifySession()
        }
    ) { uiState ->
        SplashScreenContent(
            uiState = uiState
        )
    }
}