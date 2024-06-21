package com.dreamsoftware.vaultkeeper.ui.features.account.unlock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieButtonTypeEnum
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextFieldPassword
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.core.components.CommonMasterKeyScreenContent
import com.dreamsoftware.vaultkeeper.ui.utils.showBiometricPrompt

@Composable
fun UnlockScreenContent(
    uiState: UnlockScreenUiState,
    actionListener: UnlockScreenActionListener
) {
    with(uiState) {
        CommonMasterKeyScreenContent(
            mainTitleRes = R.string.unlock_screen_title,
            isLoading = isLoading,
            errorMessage = errorMessage,
            onErrorMessageCleared = actionListener::onErrorMessageCleared,
            onInfoMessageCleared = actionListener::onInfoMessageCleared,
        ) {
            UnlockScreenSheetContent(
                uiState = uiState,
                actionListener = actionListener
            )
        }
    }
}

@Composable
private fun UnlockScreenSheetContent(
    uiState: UnlockScreenUiState,
    actionListener: UnlockScreenActionListener
) {
    with(uiState) {
        Box(modifier = Modifier.fillMaxHeight(0.8f)) {
            BrownieSheetSurface(
                enableVerticalScroll = false
            ) {
                val keyboard = LocalSoftwareKeyboardController.current

                BrownieText(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 6.dp)
                        .fillMaxWidth(),
                    type = BrownieTextTypeEnum.TITLE_MEDIUM,
                    titleRes = R.string.unlock_screen_secondary_title,
                    textAlign = TextAlign.Center
                )

                BrownieTextFieldPassword(
                    modifier = Modifier
                        .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    labelRes = R.string.create_master_key_label,
                    placeHolderRes = R.string.create_master_key_placeholder,
                    value = masterKey,
                    onValueChanged = {
                        if (it.length <= 8) {
                            actionListener.onMaterKeyUpdated(newMasterKey = it)
                        }
                    },
                    leadingIconRes = R.drawable.icon_lock,
                    supportingText = {
                        "${masterKey.length}/8"
                    },
                    onDone = {
                        keyboard?.hide()
                        actionListener.onValidate()
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                BrownieButton(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth(),
                    type = BrownieButtonTypeEnum.LARGE,
                    textRes = R.string.unlock_screen_button
                ) {
                    actionListener.onValidate()
                }

                Spacer(modifier = Modifier.height(12.dp))

                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(0.5f)
                        .background(color = Color.Gray)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))

                val context = LocalContext.current
                IconButton(
                    onClick = {
                        context.showBiometricPrompt(
                            titleRes = R.string.biometric_prompt_title,
                            subtitleRes = R.string.biometric_prompt_subtitle,
                            negativeButtonTextRes = R.string.biometric_prompt_negative_button,
                            onSuccess = {
                                actionListener.onBiometricAuthSuccessfully()
                            },
                            onError = { _, errString ->

                            },
                            onFailure = {

                            }
                        )
                    },
                    modifier = Modifier
                        .size(82.dp)
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(78.dp)
                            .padding(end = 6.dp),
                        painter = painterResource(R.drawable.icon_fingerprint),
                        contentDescription = "Finger Print"
                    )
                }

            }
        }
    }
}