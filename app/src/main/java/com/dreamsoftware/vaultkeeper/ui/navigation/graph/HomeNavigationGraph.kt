package com.dreamsoftware.vaultkeeper.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dreamsoftware.vaultkeeper.ui.features.accountpassworddetail.AccountPasswordDetailScreen
import com.dreamsoftware.vaultkeeper.ui.features.carddetail.SecureCardDetailScreen
import com.dreamsoftware.vaultkeeper.ui.features.generatepassword.GeneratePasswordScreen
import com.dreamsoftware.vaultkeeper.ui.features.home.HomeScreen
import com.dreamsoftware.vaultkeeper.ui.features.savecard.SaveCardScreen
import com.dreamsoftware.vaultkeeper.ui.features.savepassword.SavePasswordScreen
import com.dreamsoftware.vaultkeeper.ui.features.settings.SettingsScreen
import com.dreamsoftware.vaultkeeper.ui.features.updatemasterkey.UpdateMasterKeyScreen
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
                    },
                    onGoToEditSecureCard = {
                        navigate(Screens.Main.Home.EditSecureCard.buildRoute(it))
                    },
                    onGoToSecureCardDetail = {
                        navigate(Screens.Main.Home.SecureCardDetail.buildRoute(it))
                    },
                    onGoToAccountPasswordDetail = {
                        navigate(Screens.Main.Home.AccountPasswordDetail.buildRoute(it))
                    },
                    onGoToEditAccountPassword = {
                        navigate(Screens.Main.Home.EditAccountPassword.buildRoute(it))
                    }
                )
            }
        }

        composable(
            route = Screens.Main.Home.Generate.route
        ) {
            GeneratePasswordScreen()
        }

        composable(
            route = Screens.Main.Home.Settings.route
        ) {
            with(navController) {
                SettingsScreen(
                    onGoToUpdateMasterKey = {
                        navigate(Screens.Main.Home.UpdateMasterKey.route)
                    },
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

        composable(
            route = Screens.Main.Home.EditSecureCard.route
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.let { args ->
                Screens.Main.Home.EditSecureCard.parseArgs(args)?.let {
                    with(navController) {
                        SaveCardScreen(
                            args = it,
                            onBackPressed = {
                                popBackStack()
                            }
                        )
                    }
                }
            }
        }

        composable(
            route = Screens.Main.Home.SecureCardDetail.route
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.let { args ->
                Screens.Main.Home.SecureCardDetail.parseArgs(args)?.let {
                    with(navController) {
                        SecureCardDetailScreen(
                            args = it,
                            onBackPressed = {
                                popBackStack()
                            }
                        )
                    }
                }
            }
        }

        composable(
            route = Screens.Main.Home.EditAccountPassword.route
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.let { args ->
                Screens.Main.Home.EditAccountPassword.parseArgs(args)?.let {
                    with(navController) {
                        SavePasswordScreen(
                            args = it,
                            onBackPressed = {
                                popBackStack()
                            }
                        )
                    }
                }
            }
        }

        composable(
            route = Screens.Main.Home.AccountPasswordDetail.route
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.let { args ->
                Screens.Main.Home.AccountPasswordDetail.parseArgs(args)?.let {
                    with(navController) {
                        AccountPasswordDetailScreen(
                            args = it,
                            onBackPressed = {
                                popBackStack()
                            }
                        )
                    }
                }
            }
        }

        composable(
            route = Screens.Main.Home.UpdateMasterKey.route
        ) {
            with(navController) {
                UpdateMasterKeyScreen(
                    onMasterKeyUpdated = {
                        popBackStack()
                    },
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }
    }
}