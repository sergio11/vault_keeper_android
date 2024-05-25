package com.dreamsoftware.lockbuddy.ui.features.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dreamsoftware.brownie.component.screen.BrownieScreen
import com.dreamsoftware.lockbuddy.ui.navigation.Screens

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    with(navController) {
        val navBackStackEntry by currentBackStackEntryAsState()
        val hideBottomItems by remember { derivedStateOf {
            when (navBackStackEntry?.destination?.route) {
                Screens.Main.Home.Info.route,
                Screens.Main.Home.Generate.route,
                Screens.Main.Home.Settings.route -> false
                else -> true
            }
        } }
        LaunchedEffect(hideBottomItems) {
            viewModel.onBottomItemsVisibilityChanged(hideBottomItems)
        }
        BrownieScreen(
            viewModel = viewModel,
            onInitialUiState = { MainUiState() }
        ) { uiState ->
            MainScreenContent(
                uiState = uiState,
                currentDestination = navBackStackEntry?.destination,
                navHostController = navController
            )
        }
    }
}