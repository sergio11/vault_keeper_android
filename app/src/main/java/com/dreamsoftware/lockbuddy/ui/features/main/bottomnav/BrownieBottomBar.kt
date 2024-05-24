package com.dreamsoftware.lockbuddy.ui.features.main.bottomnav

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.dreamsoftware.lockbuddy.ui.features.main.model.BottomNavBarItem
import com.dreamsoftware.lockbuddy.ui.theme.Blue

@SuppressLint("RestrictedApi")
@Composable
fun BrownieBottomBar(
    navController: NavController,
    items: List<BottomNavBarItem>
) {
    val currentDestination = navController.currentDestination?.route
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.route,
                onClick = {

                },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = "",
                        tint = if (currentDestination == destination.route) Color.White else Color.Black // Icon color when selected
                    )
                },
                label = { Text("Test") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue,
                    indicatorColor = Blue
                )
            )
        }
    }
}