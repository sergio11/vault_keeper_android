package com.dreamsoftware.lockbuddy.ui.features.account.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.lockbuddy.R

@Composable
fun SplashScreenContent(
    uiState: SplashUiState
) {
    BrownieScreenContent(
        enableVerticalScroll = false,
        hasTopBar = false,
        backgroundRes = R.drawable.main_background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BrownieText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp),
                type = BrownieTextTypeEnum.HEADLINE_SMALL,
                textColor = Color.White,
                textAlign = TextAlign.Center,
                titleRes = R.string.splash_loading_title,
                textBold = true
            )
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                color = Color.White
            )
        }
    }
}