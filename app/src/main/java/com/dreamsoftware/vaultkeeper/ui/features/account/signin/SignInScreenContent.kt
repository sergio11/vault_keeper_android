package com.dreamsoftware.vaultkeeper.ui.features.account.signin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieButtonTypeEnum
import com.dreamsoftware.brownie.component.BrownieDefaultTextField
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextFieldPassword
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.features.account.core.AccountScreen

@Composable
fun SignInScreenContent(
    uiState: SignInUiState,
    actionsListener: SignInScreenActionListener
) {
    with(uiState){
        AccountScreen(
            mainTitleRes = R.string.signin_main_title_text,
            isLoading = isLoading,
            onInfoMessageCleared = actionsListener::onInfoMessageCleared,
            onErrorMessageCleared = actionsListener::onErrorMessageCleared,
            errorMessage = errorMessage,
            screenBackgroundRes = R.drawable.main_background) {
            BrownieText(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 6.dp)
                    .fillMaxWidth(),
                type = BrownieTextTypeEnum.TITLE_LARGE,
                titleRes = R.string.onboarding_subtitle_text,
                textAlign = TextAlign.Center,
                textBold = true
            )
            BrownieText(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 6.dp)
                    .fillMaxWidth(),
                type = BrownieTextTypeEnum.TITLE_MEDIUM,
                titleRes = R.string.signin_secondary_title_text,
                textAlign = TextAlign.Center
            )
            BrownieDefaultTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                labelRes = R.string.signin_input_email_label,
                placeHolderRes = R.string.signin_input_email_placeholder,
                keyboardType = KeyboardType.Email,
                value = email,
                isSingleLine = true,
                enableTextFieldSeparator = true,
                errorMessage = emailError,
                leadingIconRes = R.drawable.icon_username,
                onValueChanged = actionsListener::onEmailChanged
            )
            BrownieTextFieldPassword(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                labelRes = R.string.signin_input_password_label,
                placeHolderRes = R.string.signin_input_password_placeholder,
                value = password,
                errorMessage = passwordError,
                enableTextFieldSeparator = true,
                leadingIconRes = R.drawable.icon_secret,
                onValueChanged = actionsListener::onPasswordChanged
            )
            BrownieButton(
                modifier  = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                textRes = R.string.signin_login_button_text,
                type = BrownieButtonTypeEnum.LARGE,
                onClick = actionsListener::onSignIn
            )
        }
    }
}