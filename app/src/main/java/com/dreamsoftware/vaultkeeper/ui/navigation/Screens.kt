package com.dreamsoftware.vaultkeeper.ui.navigation

import androidx.navigation.NamedNavArgument

sealed class Screens(val route: String, arguments: List<NamedNavArgument> = emptyList()) {

    data object Splash: Screens("splash")
    data object Onboarding: Screens("onboarding")
    data object SignIn: Screens("sign_in")
    data object SignUp: Screens("sign_up")
    data object CreateMasterKey: Screens("create_master_key")
    sealed class Main private constructor(route: String) : Screens(route) {
        companion object {
            const val route = "main"
            const val startDestination = Home.route
        }

        sealed class Home private constructor(route: String) : Screens(route) {
            companion object {
                const val route = "home"
                val startDestination = Info.route
            }
            data object Info : Screens("info")
            data object Generate : Screens("generate")
            data object Settings: Screens("settings")
        }

        data object CreateCard : Screens("create_card")
    }
}