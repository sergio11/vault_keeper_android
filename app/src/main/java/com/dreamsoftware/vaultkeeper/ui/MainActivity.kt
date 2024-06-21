package com.dreamsoftware.vaultkeeper.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import com.dreamsoftware.vaultkeeper.ui.features.app.AppScreen
import com.dreamsoftware.vaultkeeper.ui.theme.VaultKeeperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Complying with Android 12 Splash Screen guidelines
        super.onCreate(savedInstanceState)
        setContent {
            VaultKeeperTheme {
                AppScreen()
            }
        }
    }
}

