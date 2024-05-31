package com.dreamsoftware.vaultkeeper.ui.features.savepassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.vaultkeeper.ui.features.savecard.Params

data class Params(
    val accountId: Int
)

@Composable
fun SavePasswordScreen(
    viewModel: SavePasswordViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    params: Params? = null
) {


}



@Composable
fun TextFieldSeparator(
    height: Int
) {
    Box(
        modifier = Modifier
            .padding(end = 12.dp)
            .height(height.dp)
            .width(1.dp)
            .background(color = Color.LightGray),
        contentAlignment = Alignment.Center
    ) {}
}