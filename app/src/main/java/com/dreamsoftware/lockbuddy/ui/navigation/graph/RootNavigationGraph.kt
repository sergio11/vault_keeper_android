package com.dreamsoftware.lockbuddy.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dreamsoftware.lockbuddy.ui.features.main.MainScreen
import com.dreamsoftware.lockbuddy.ui.navigation.Screens

@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        startDestination = Screens.Main.route,
        navController = navController
    ) {
        composable(
            route = Screens.Splash.route
        ) {
            with(navController) {

            }
        }
        composable(
            route = Screens.Onboarding.route
        ) {
            with(navController) {

            }
        }
        composable(
            route = Screens.Main.route
        ) {
            MainScreen()
        }
    }
}