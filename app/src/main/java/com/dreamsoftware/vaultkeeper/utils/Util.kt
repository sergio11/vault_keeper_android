package com.dreamsoftware.vaultkeeper.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import java.util.UUID
import kotlin.random.Random

fun Modifier.clickWithRipple(bounded: Boolean = true, onClick: () -> Unit) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(bounded = bounded),
        onClick = { onClick() }
    )
}

fun getRandomNumber(): Int = Random.nextInt(6, 21)

fun generateUUID(): String = UUID.randomUUID().toString()

fun generateRandomBrush(): Brush {
    val gradientOptions = listOf(
        listOf(Color(0xFF6C72CB), Color(0xFF0078FF)), // Blue Gradient
        listOf(Color(0xFF8A2387), Color(0xFFE94057)), // Pink Gradient
        listOf(Color(0xFF56CCF2), Color(0xFF2F80ED)), // Sky Blue Gradient
        listOf(Color(0xFFFFD23F), Color(0xFFFF6B6B)), // Sunset Gradient
        listOf(Color(0xFF6A3093), Color(0xFFA044FF)), // Purple Gradient
        listOf(Color(0xFFF7B733), Color(0xFFFC4A1A)), // Orange Gradient
        listOf(Color(0xFF00C9FF), Color(0xFF92FE9D)), // Turquoise Gradient
        listOf(Color(0xFF00F260), Color(0xFF0575E6)), // Green Gradient
        listOf(Color(0xFF693B52), Color(0xFF1B1B1E)), // Dark Red Gradient
        listOf(Color(0xFF00B4DB), Color(0xFF0083B0)), // Ocean Blue Gradient
        listOf(Color(0xFFFEAC5E), Color(0xFFC779D0)), // Peach to Purple Gradient
        listOf(Color(0xFFB3FFAB), Color(0xFF12FFF7)), // Light Green to Cyan Gradient
        listOf(Color(0xFFF4E2D8), Color(0xFFFFB3B3)), // Cream to Light Pink Gradient
        listOf(Color(0xFF1FA2FF), Color(0xFF12D8FA), Color(0xFFA6FFCB)), // Blue to Green Gradient
        listOf(Color(0xFF5433FF), Color(0xFF20BDFF), Color(0xFFA5FECB)), // Purple to Blue to Green Gradient
        listOf(Color(0xFFFF9A8B), Color(0xFFFF6A88), Color(0xFFFF99AC)), // Light Pink to Red Gradient
        listOf(Color(0xFFC6FFDD), Color(0xFFFBD786), Color(0xFFF7797D)), // Green to Yellow to Red Gradient
        listOf(Color(0xFFFFDEE9), Color(0xFFB5FFFC)), // Light Pink to Light Blue Gradient
        listOf(Color(0xFF9D50BB), Color(0xFF6E48AA)), // Dark Purple to Dark Blue Gradient
        listOf(Color(0xFFFC00FF), Color(0xFF00DBDE)), // Magenta to Cyan Gradient
        listOf(Color(0xFFFF9966), Color(0xFFFF5E62)), // Orange to Red Gradient
        listOf(Color(0xFF36D1DC), Color(0xFF5B86E5)), // Cyan to Blue Gradient
        listOf(Color(0xFFEB3349), Color(0xFFF45C43)), // Red to Orange Gradient
        listOf(Color(0xFFDD5E89), Color(0xFFF7BB97)), // Pink to Peach Gradient
        listOf(Color(0xFF56AB2F), Color(0xFFA8E063)), // Green to Light Green Gradient
        listOf(Color(0xFF614385), Color(0xFF516395)), // Dark Purple to Light Purple Gradient
        listOf(Color(0xFF02AAB0), Color(0xFF00CDAC)), // Teal to Light Green Gradient
        listOf(Color(0xFFDA22FF), Color(0xFF9733EE)), // Bright Purple Gradient
        listOf(Color(0xFF1488CC), Color(0xFF2B32B2)), // Blue to Dark Blue Gradient
        listOf(Color(0xFFEC6F66), Color(0xFFF3A183))  // Coral to Peach Gradient
    )
    val randomIndex = Random.nextInt(gradientOptions.size)
    return Brush.verticalGradient(gradientOptions[randomIndex])
}


