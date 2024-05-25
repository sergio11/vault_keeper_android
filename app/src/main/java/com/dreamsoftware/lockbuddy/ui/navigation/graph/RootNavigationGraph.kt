package com.dreamsoftware.lockbuddy.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dreamsoftware.lockbuddy.ui.features.account.onboarding.OnboardingScreen
import com.dreamsoftware.lockbuddy.ui.features.account.signin.SignInScreen
import com.dreamsoftware.lockbuddy.ui.features.account.signup.SignUpScreen
import com.dreamsoftware.lockbuddy.ui.features.account.splash.SplashScreen
import com.dreamsoftware.lockbuddy.ui.features.main.MainScreen
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
                    onBackPressed = {
                        popBackStack()
                    }
                )
            }
        }

        composable(
            route = Screens.Main.route
        ) {
            MainScreen()
        }
    }
}