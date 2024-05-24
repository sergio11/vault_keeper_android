package com.dreamsoftware.lockbuddy.ui.bottomnav

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.dreamsoftware.lockbuddy.ui.theme.Blue

@SuppressLint("RestrictedApi")
@Composable
fun BottomBar(
    navController: NavController
) {
    /*val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination*/

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        BottomBarDestination.entries.forEach { destination ->
            NavigationBarItem(
                //selected = currentDestination == destination.direction,
                onClick = {
                    // remove all navigation items from the stack
                    // so only the currently selected screen remains in the stack
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = stringResource(destination.label),
                        //tint = if (currentDestination == destination.direction) Color.White else Color.Black // Icon color when selected
                    )
                },
                selected = true,
                label = { Text(stringResource(destination.label)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue,
                    indicatorColor = Blue
                )
            )
        }
    }
}

fun checkForDestinations(
    navigationRoutes: Array<BottomBarDestination>,
    navBackStackEntry: NavBackStackEntry
): Boolean {
    navigationRoutes.forEach {
        /*if (it.direction.route == navBackStackEntry.destination.route) {
            return true
        }*/
    }
    return false
}