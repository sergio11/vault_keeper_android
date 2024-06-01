package com.dreamsoftware.vaultkeeper.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dreamsoftware.vaultkeeper.ui.features.generatepassword.GeneratePasswordScreen
import com.dreamsoftware.vaultkeeper.ui.features.home.HomeScreen
import com.dreamsoftware.vaultkeeper.ui.features.savecard.SaveCardScreen
import com.dreamsoftware.vaultkeeper.ui.features.savepassword.SavePasswordScreen
import com.dreamsoftware.vaultkeeper.ui.features.settings.SettingsScreen
import com.dreamsoftware.vaultkeeper.ui.navigation.Screens

fun NavGraphBuilder.HomeNavigationGraph(
    navController: NavHostController,
    onGoToSignIn: () -> Unit
) {
    navigation(
        startDestination = Screens.Main.Home.startDestination,
        route = Screens.Main.Home.route
    ) {
        composable(
            route = Screens.Main.Home.Info.route
        ) {
            with(navController) {
                HomeScreen(
                    onGoToAddNewAccount = {
                        navigate(Screens.Main.Home.CreateAccountPassword.route)
                    },
                    onGoToAddNewSecureCard = {
                        navigate(Screens.Main.Home.CreateSecureCard.route)
                    }
                )
            }
        }

        composable(
            route = Screens.Main.Home.Generate.route
        ) {
            with(navController) {
                GeneratePasswordScreen()
            }
        }

        composable(
            route = Screens.Main.Home.Settings.route
        ) {
            with(navController) {
                SettingsScreen(
                    onGoToCreateMasterKey = {},
                    onGoToSignIn = onGoToSignIn
                )
            }
        }

        composable(
            route = Screens.Main.Home.CreateAccountPassword.route
        ) {
            with(navController) {
                SavePasswordScreen(
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }

        composable(
            route = Screens.Main.Home.CreateSecureCard.route
        ) {
            with(navController) {
                SaveCardScreen(
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }
    }
}