package com.dreamsoftware.vaultkeeper.ui.features.account.unlock

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.core.components.LoadingDialog

@Composable
fun UnlockScreenContent(
    uiState: UnlockScreenUiState,
    actionListener: UnlockScreenActionListener
) {
    with(uiState) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        LoadingDialog(isShowingDialog = isLoading)
        BrownieScreenContent(
            enableVerticalScroll = true,
            hasTopBar = false,
            errorMessage = errorMessage,
            onInfoMessageCleared = actionListener::onInfoMessageCleared,
            onErrorMessageCleared = actionListener::onErrorMessageCleared,
            backgroundRes = R.drawable.main_background
        ) {
            Column(
                modifier = Modifier.fillMaxSize().heightIn(min = screenHeight),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Image(
                    painter = painterResource(id = R.drawable.main_logo),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(horizontal = 32.dp, vertical = 10.dp)
                )
                BrownieText(
                    modifier  = Modifier
                        .padding(start = 16.dp, end = 32.dp)
                        .fillMaxWidth(),
                    type = BrownieTextTypeEnum.HEADLINE_SMALL,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    titleRes = R.string.unlock_screen_title,
                )
                Spacer(modifier = Modifier.weight(1f))
                UnlockScreenSheetContent(
                    uiState = uiState,
                    actionListener = actionListener
                )
            }
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
            }
        }
    }
}