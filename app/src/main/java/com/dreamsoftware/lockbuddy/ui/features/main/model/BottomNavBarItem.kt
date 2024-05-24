package com.dreamsoftware.lockbuddy.ui.features.main.model

import androidx.annotation.DrawableRes

data class BottomNavBarItem(
    val route: String,
    @DrawableRes val icon: Int
)