package com.dreamsoftware.vaultkeeper.ui.features.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dreamsoftware.brownie.component.screen.BrownieScreen
import com.dreamsoftware.vaultkeeper.ui.navigation.Screens

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onGoToSignIn: () -> Unit,
    onGoToUnlockScreen: () -> Unit
) {
    val navController = rememberNavController()
    with(navController) {
        val navBackStackEntry by currentBackStackEntryAsState()
        val hideBottomItems by remember {
            derivedStateOf {
                when (navBackStackEntry?.destination?.route) {
                    Screens.Main.Home.Info.route,
                    Screens.Main.Home.Generate.route,
                    Screens.Main.Home.Settings.route -> false
                    else -> true
                }
            }
        }
        LaunchedEffect(hideBottomItems) {
            viewModel.onBottomItemsVisibilityChanged(hideBottomItems)
        }
        BrownieScreen(
            viewModel = viewModel,
            onInitialUiState = { MainUiState() },
            onSideEffect = {
                when (it) {
                    MainSideEffects.AccountIsLockedSideEffect -> onGoToUnlockScreen()
                }
            },
            onResume = {
                onVerifyUserAccountStatus()
            },
            onPause = {
                onLockAccount()
            }
        ) { uiState ->
            MainScreenContent(
                uiState = uiState,
                onGoToSignIn = onGoToSignIn,
                currentDestination = navBackStackEntry?.destination,
                navHostController = navController
            )
        }
    }
}