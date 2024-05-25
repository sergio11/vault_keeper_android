package com.dreamsoftware.lockbuddy.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dreamsoftware.lockbuddy.ui.features.main.MainScreen
import com.dreamsoftware.lockbuddy.ui.features.onboarding.OnboardingScreen
import com.dreamsoftware.lockbuddy.ui.features.splash.SplashScreen
import com.dreamsoftware.lockbuddy.ui.navigation.Screens

@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        startDestination = Screens.Splash.route,
        navController = navController
    ) {
        composable(
            route = Screens.Splash.route
        ) {
            with(navController) {
                SplashScreen(
                    onGoToOnboarding = {
                        navigate(Screens.Onboarding.route)
                    },
                    onGoToHome = {}
                )
            }
        }
        composable(
            route = Screens.Onboarding.route
        ) {
            with(navController) {
                OnboardingScreen()
            }
        }
        composable(
            route = Screens.Main.route
        ) {
            MainScreen()
        }
    }
}