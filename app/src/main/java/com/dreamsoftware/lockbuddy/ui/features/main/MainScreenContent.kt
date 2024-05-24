package com.dreamsoftware.lockbuddy.ui.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dreamsoftware.brownie.component.BrownieSlideDownAnimatedVisibility
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.lockbuddy.R
import com.dreamsoftware.lockbuddy.ui.features.main.bottomnav.BrownieBottomBar
import com.dreamsoftware.lockbuddy.ui.features.main.model.BottomNavBarItem
import com.dreamsoftware.lockbuddy.ui.navigation.Screens
import com.dreamsoftware.lockbuddy.ui.navigation.graph.MainNavigationGraph

@Composable
fun MainScreenContent(
    uiState: MainUiState,
    navHostController: NavHostController
) {
    with(uiState) {
        BrownieScreenContent(
            hasTopBar = false,
            onBuildBottomBar = {
                BrownieSlideDownAnimatedVisibility(
                    visible = shouldShowBottomNav && hasSession,
                ) {
                    BottomBar(navController = navHostController)
                }
            }
        ) {
           MainNavigationGraph(navHostController)
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary,
                clip = true
            )
    ) {
        BrownieBottomBar(
            navController = navController,
            items = listOf(
                BottomNavBarItem(Screens.Main.Home.Info.route, R.drawable.icon_call),
                BottomNavBarItem(Screens.Main.Home.Generate.route, R.drawable.icon_call),
                BottomNavBarItem(Screens.Main.Home.Settings.route, R.drawable.icon_call)
            )
        )
    }
}