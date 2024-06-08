package com.dreamsoftware.vaultkeeper.ui.features.account.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.features.account.core.AccountScreen

@Composable
fun SignUpScreenContent(
    uiState: SignUpUiState,
    actionListener: SignUpScreenActionListener
) {
    with(uiState) {
        AccountScreen(
            mainTitleRes = R.string.signup_main_title_text,
            isLoading = isLoading,
            errorMessage = error,
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
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                BrownieDefaultTextField(
                    labelRes = R.string.signup_input_email_label,
                    placeHolderRes = R.string.signup_input_email_placeholder,
                    keyboardType = KeyboardType.Email,
                    value = email,
                    errorMessage = emailError,
                    enableTextFieldSeparator = true,
                    leadingIconRes = R.drawable.icon_username,
                    onValueChanged = actionListener::onEmailChanged
                )
                BrownieTextFieldPassword(
                    labelRes = R.string.signup_input_password_label,
                    placeHolderRes = R.string.signup_input_password_placeholder,
                    value = password,
                    errorMessage = passwordError,
                    enableTextFieldSeparator = true,
                    leadingIconRes = R.drawable.icon_secret,
                    onValueChanged = actionListener::onPasswordChanged
                )
                BrownieTextFieldPassword(
                    labelRes = R.string.signup_input_confirm_password_label,
                    placeHolderRes = R.string.signup_input_confirm_password_placeholder,
                    value = confirmPassword,
                    enableTextFieldSeparator = true,
                    leadingIconRes = R.drawable.icon_secret,
                    onValueChanged = actionListener::onConfirmPasswordChanged
                )
            }
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            BrownieButton(
                modifier = Modifier.fillMaxWidth(0.8f),
                textRes = R.string.signup_signup_button_text,
                type = BrownieButtonTypeEnum.LARGE,
                onClick = actionListener::onSignUp
            )
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            BrownieButton(
                modifier = Modifier.fillMaxWidth(0.8f),
                textRes = R.string.signup_already_has_account_text,
                type = BrownieButtonTypeEnum.LARGE,
                style = BrownieButtonStyleTypeEnum.TRANSPARENT,
                onClick = actionListener::onNavigateToSignIn
            )
        }
    }
}