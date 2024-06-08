package com.dreamsoftware.vaultkeeper.ui.features.account.onboarding

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieButtonStyleTypeEnum
import com.dreamsoftware.brownie.component.BrownieButtonTypeEnum
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.features.account.core.AccountScreen

@Composable
fun OnboardingScreenContent(
    uiState: OnboardingUiState,
    actionListener: OnboardingScreenActionListener
) {
    AccountScreen(
        mainTitleRes = R.string.onboarding_main_title_text,
        videoResourceId = R.raw.onboarding_video,
        isLoading = uiState.isLoading
    ) {
        BrownieText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            type = BrownieTextTypeEnum.TITLE_LARGE,
            titleRes = R.string.onboarding_subtitle_text,
            textAlign = TextAlign.Center,
            textBold = true
        )
        BrownieText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            type = BrownieTextTypeEnum.TITLE_MEDIUM,
            titleRes = R.string.onboarding_description_text,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        BrownieButton(
            modifier = Modifier.fillMaxWidth(0.8f),
            textRes = R.string.onboarding_login_button_text,
            type = BrownieButtonTypeEnum.LARGE,
            onClick = actionListener::onNavigateToSignIn
        )
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        BrownieButton(
            modifier = Modifier.fillMaxWidth(0.8f),
            textRes = R.string.onboarding_signup_button_text,
            type = BrownieButtonTypeEnum.LARGE,
            style = BrownieButtonStyleTypeEnum.INVERSE,
            onClick = actionListener::onNavigateToSignUp
        )
    }
}