package com.dreamsoftware.lockbuddy.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dreamsoftware.lockbuddy.ui.features.app.AppScreen
import com.dreamsoftware.lockbuddy.ui.theme.VaultKeeperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

