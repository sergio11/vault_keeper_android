package com.dreamsoftware.vaultkeeper.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dreamsoftware.vaultkeeper.ui.features.account.createmasterkey.CreateMasterKeyScreen
import com.dreamsoftware.vaultkeeper.ui.features.account.onboarding.OnboardingScreen
import com.dreamsoftware.vaultkeeper.ui.features.account.signin.SignInScreen
import com.dreamsoftware.vaultkeeper.ui.features.account.signup.SignUpScreen
import com.dreamsoftware.vaultkeeper.ui.features.account.splash.SplashScreen
import com.dreamsoftware.vaultkeeper.ui.features.main.MainScreen
import com.dreamsoftware.vaultkeeper.ui.navigation.Screens
import com.dreamsoftware.vaultkeeper.ui.navigation.utils.navigateSingleTopTo

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
                    onGoToHome = {
                        navigate(Screens.Main.route)
                    }
                )
            }
        }

        composable(
            route = Screens.Onboarding.route
        ) {
            with(navController) {
                OnboardingScreen(
                    onGoToSignIn = {
                        navigate(Screens.SignIn.route)
                    },
                    onGoToSignUp = {
                        navigate(Screens.SignUp.route)
                    }
                )
            }
        }

        composable(
            route = Screens.SignIn.route
        ) {
            with(navController) {
                SignInScreen(
                    onAuthenticated = {
                        navigate(Screens.Main.route)
                    },
                    onMasterKeyNeeded = {
                        navigate(Screens.CreateMasterKey.route)
                    },
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }

        composable(
            route = Screens.SignUp.route
        ) {
            with(navController) {
                SignUpScreen(
                    onGoToSignIn = {
                        navigate(Screens.SignIn.route)
                    },
                    onGoToCreateMasterKey = {
                        navigate(Screens.CreateMasterKey.route)
                    },
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }

        composable(
            route = Screens.CreateMasterKey.route
        ) {
            with(navController) {
                CreateMasterKeyScreen(
                    onMasterKeyCreated = {
                        navigate(Screens.Main.route)
                    }
                )
            }
        }

        composable(
            route = Screens.Main.route
        ) {
            MainScreen(
                onGoToSignIn = {
                    navController.navigateSingleTopTo(Screens.Onboarding.route)
                }
            )
        }
    }
}