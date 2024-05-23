package com.dreamsoftware.lockbuddy.ui.bottomnav

import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.dreamsoftware.lockbuddy.R
import com.dreamsoftware.lockbuddy.ui.destinations.GenerateScreenDestination
import com.dreamsoftware.lockbuddy.ui.destinations.HomeScreenDestination
import com.dreamsoftware.lockbuddy.ui.destinations.SettingsScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, R.drawable.icon_home, R.string.home),
    Generate(
        GenerateScreenDestination,
        R.drawable.icon_key,
        R.string.generate
    ),
    Settings(SettingsScreenDestination, R.drawable.icon_settings, R.string.settings)
}