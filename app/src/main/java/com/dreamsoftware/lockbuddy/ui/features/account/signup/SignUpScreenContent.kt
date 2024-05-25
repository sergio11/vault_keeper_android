package com.dreamsoftware.lockbuddy.ui.features.account.signup

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieButtonStyleTypeEnum
import com.dreamsoftware.brownie.component.BrownieButtonTypeEnum
import com.dreamsoftware.brownie.component.BrownieDefaultTextField
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextFieldPassword
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.lockbuddy.R
import com.dreamsoftware.lockbuddy.ui.features.account.core.AccountScreen

@Composable
fun SignUpScreenContent(
    uiState: SignUpUiState,
    actionListener: SignUpScreenActionListener
) {
    with(uiState) {
        AccountScreen(
            mainTitleRes = R.string.signup_main_title_text,
            isLoading = isLoading,
            screenBackgroundRes = R.drawable.main_background
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
                titleRes = R.string.signup_secondary_title_text,
                textAlign = TextAlign.Center
            )
            BrownieDefaultTextField(
                labelRes = R.string.signup_input_email_label,
                placeHolderRes = R.string.signup_input_email_placeholder,
                keyboardType = KeyboardType.Email,
                value = email,
                onValueChanged = actionListener::onEmailChanged
            )
            BrownieTextFieldPassword(
                labelRes = R.string.signup_input_password_label,
                placeHolderRes = R.string.signup_input_password_placeholder,
                value = password,
                onValueChanged = actionListener::onPasswordChanged
            )
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            BrownieButton(
                enabled = isSignUpButtonEnabled,
                textRes = R.string.signup_signup_button_text,
                type = BrownieButtonTypeEnum.LARGE,
                onClick = actionListener::onSignUp
            )
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            BrownieButton(
                textRes = R.string.signup_already_has_account_text,
                type = BrownieButtonTypeEnum.LARGE,
                style = BrownieButtonStyleTypeEnum.TRANSPARENT,
                onClick = actionListener::onNavigateToSignIn
            )
        }
    }
}