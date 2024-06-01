package com.dreamsoftware.vaultkeeper.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dreamsoftware.vaultkeeper.ui.navigation.Screens

@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    onGoToSignIn: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Main.startDestination
    ) {
        HomeNavigationGraph(
            navController = navController,
            onGoToSignIn = onGoToSignIn
        )
    }
}