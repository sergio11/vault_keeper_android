package com.dreamsoftware.lockbuddy.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dreamsoftware.lockbuddy.ui.features.generate.GenerateScreen
import com.dreamsoftware.lockbuddy.ui.features.home.HomeScreen
import com.dreamsoftware.lockbuddy.ui.features.settings.SettingsScreen
import com.dreamsoftware.lockbuddy.ui.navigation.Screens

fun NavGraphBuilder.HomeNavigationGraph(navController: NavHostController) {
    navigation(
        startDestination = Screens.Main.Home.startDestination,
        route = Screens.Main.Home.route
    ) {
        composable(
            route = Screens.Main.Home.Info.route
        ) {
            with(navController) {
                HomeScreen()
            }
        }

        composable(
            route = Screens.Main.Home.Generate.route
        ) {
            with(navController) {
                GenerateScreen()
            }
        }

        composable(
            route = Screens.Main.Home.Settings.route
        ) {
            with(navController) {
                SettingsScreen()
            }
        }
    }
}