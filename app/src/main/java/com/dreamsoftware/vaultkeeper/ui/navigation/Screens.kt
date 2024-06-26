package com.dreamsoftware.vaultkeeper.ui.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.dreamsoftware.vaultkeeper.ui.features.accountpassworddetail.AccountPasswordDetailScreenArgs
import com.dreamsoftware.vaultkeeper.ui.features.carddetail.SecureCardDetailScreenArgs
import com.dreamsoftware.vaultkeeper.ui.features.savecard.SaveCardScreenArgs
import com.dreamsoftware.vaultkeeper.ui.features.savepassword.SaveAccountPasswordScreenArgs

sealed class Screens(val route: String, arguments: List<NamedNavArgument> = emptyList()) {

    data object Splash: Screens("splash")
    data object Onboarding: Screens("onboarding")
    data object SignIn: Screens("sign_in")
    data object SignUp: Screens("sign_up")
    data object CreateMasterKey: Screens("create_master_key")
    data object UnlockScreen: Screens("unlock_screen")
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

            data object SecureCardDetail : Screens("secure_card_detail/{card_uid}", arguments = listOf(
                navArgument("card_uid") {
                    type = NavType.StringType
                }
            )) {
                fun buildRoute(cardUid: String): String =
                    route.replace(
                        oldValue = "{card_uid}",
                        newValue = cardUid
                    )

                fun parseArgs(args: Bundle): SecureCardDetailScreenArgs? = with(args) {
                    getString("card_uid")?.let {
                        SecureCardDetailScreenArgs(cardUid = it)
                    }
                }
            }

            data object EditAccountPassword : Screens("edit_account_password/{account_uid}", arguments = listOf(
                navArgument("account_uid") {
                    type = NavType.StringType
                }
            )) {
                fun buildRoute(accountUid: String): String =
                    route.replace(
                        oldValue = "{account_uid}",
                        newValue = accountUid
                    )

                fun parseArgs(args: Bundle): SaveAccountPasswordScreenArgs? = with(args) {
                    getString("account_uid")?.let {
                        SaveAccountPasswordScreenArgs(accountUid = it)
                    }
                }
            }

            data object AccountPasswordDetail : Screens("account_password_detail/{account_uid}", arguments = listOf(
                navArgument("account_uid") {
                    type = NavType.StringType
                }
            )) {
                fun buildRoute(accountUid: String): String =
                    route.replace(
                        oldValue = "{account_uid}",
                        newValue = accountUid
                    )

                fun parseArgs(args: Bundle): AccountPasswordDetailScreenArgs? = with(args) {
                    getString("account_uid")?.let {
                        AccountPasswordDetailScreenArgs(accountPasswordUid = it)
                    }
                }
            }

            data object UpdateMasterKey : Screens("update_master_key")
        }
    }
}