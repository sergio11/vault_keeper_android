package com.dreamsoftware.vaultkeeper.ui.features.account.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onGoToOnboarding: () -> Unit,
    onGoToCreateMasterKey: () -> Unit,
    onGoToHome: () -> Unit
) {
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { SplashUiState() },
        onSideEffect = {
            when(it) {
                SplashSideEffects.UserAlreadyAuthenticatedSideEffect -> onGoToHome()
                SplashSideEffects.UserNotAuthenticatedSideEffect -> onGoToOnboarding()
                SplashSideEffects.RequireMasterKeySideEffect -> onGoToCreateMasterKey()
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