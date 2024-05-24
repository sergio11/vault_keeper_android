package com.dreamsoftware.lockbuddy.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import com.dreamsoftware.lockbuddy.ui.theme.LockBuddyTheme
import com.dreamsoftware.lockbuddy.util.LocalSnackbar
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Complying with Android 12 Splash Screen guidelines
        super.onCreate(savedInstanceState)

        setContent {
            LockBuddyTheme {
                ScaffoldDefaults.contentWindowInsets
                /*val navController = engine.rememberNavController()
                val destination = navController.appCurrentDestinationAsState().value
                    ?: NavGraphs.root.startRoute.startAppDestination*/

                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                val onSnackbarMessageReceived = fun(message: String) {
                    scope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        /*if (destination.shouldShowBottomBar()) {
                            BottomBar(navController)
                        }*/
                    },
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) { contentPadding ->
                    CompositionLocalProvider(
                        LocalSnackbar provides onSnackbarMessageReceived
                    ) {
                        /*DestinationsNavHost(
                            modifier = Modifier
                                .padding(contentPadding),
                            navGraph = NavGraphs.root,
                            navController = navController,
                            engine = engine
                        )*/
                    }
                }
            }
        }
    }
}

