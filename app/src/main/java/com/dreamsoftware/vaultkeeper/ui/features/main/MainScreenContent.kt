package com.dreamsoftware.vaultkeeper.ui.features.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.dreamsoftware.brownie.component.BrownieBottomBar
import com.dreamsoftware.brownie.component.BrownieSlideDownAnimatedVisibility
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.vaultkeeper.ui.navigation.graph.MainNavigationGraph

@Composable
fun MainScreenContent(
    uiState: MainUiState,
    currentDestination: NavDestination?,
    onGoToSignIn: () -> Unit,
    navHostController: NavHostController
) {
    with(uiState) {
        BrownieScreenContent(
            hasTopBar = false,
            onBuildBottomBar = {
                BrownieSlideDownAnimatedVisibility(
                    visible = shouldShowBottomNav && hasSession,
                ) {
                    BrownieBottomBar(
                        currentItemRouteSelected = currentDestination?.route,
                        items = mainDestinationList,
                        containerColor = MaterialTheme.colorScheme.primary,
                        iconColorSelected = MaterialTheme.colorScheme.primary
                    ) {
                        navHostController.navigate(it.route)
                    }
                }
            }
        ) {
           MainNavigationGraph(
               navController = navHostController,
               onGoToSignIn = onGoToSignIn
           )
        }
    }
}