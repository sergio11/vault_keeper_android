package com.dreamsoftware.vaultkeeper.ui.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.dreamsoftware.vaultkeeper.ui.features.savecard.SaveCardScreenArgs

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
            data object CreateAccountPassword : Screens("create_account_password")
            data object CreateSecureCard : Screens("create_secured_card")

            data object EditSecureCard : Screens("edit_secure_card/{card_uid}", arguments = listOf(
                navArgument("card_uid") {
                    type = NavType.StringType
                }
            )) {
                fun buildRoute(cardUid: String): String =
                    route.replace(
                        oldValue = "{card_uid}",
                        newValue = cardUid
                    )

                fun parseArgs(args: Bundle): SaveCardScreenArgs? = with(args) {
                    getString("card_uid")?.let {
                        SaveCardScreenArgs(cardUid = it)
                    }
                }
            }
        }

        data object CreateCard : Screens("create_card")
    }
}