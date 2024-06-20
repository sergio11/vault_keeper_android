package com.dreamsoftware.vaultkeeper.ui.features.updatemasterkey

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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

@Composable
fun UpdateMasterKeyScreenContent(
    uiState: UpdateMasterKeyUiState,
    actionListener: UpdateMasterKeyScreenActionListener
) {
    with(uiState) {
        CommonMasterKeyScreenContent(
            mainTitleRes = R.string.update_master_key_headline,
            isLoading = isLoading,
            errorMessage = errorMessage,
            onErrorMessageCleared = actionListener::onErrorMessageCleared,
            onInfoMessageCleared = actionListener::onInfoMessageCleared,
        ) {
            UpdateMasterKeySheetContent(
                uiState = uiState,
                actionListener = actionListener
            )
        }
    }
}

@Composable
private fun UpdateMasterKeySheetContent(
    uiState: UpdateMasterKeyUiState,
    actionListener: UpdateMasterKeyScreenActionListener
) {
    with(uiState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                BrownieSheetSurface(
                    enableVerticalScroll = false
                ) {
                    val keyboard = LocalSoftwareKeyboardController.current

                    BrownieText(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 6.dp)
                            .fillMaxWidth(),
                        type = BrownieTextTypeEnum.TITLE_MEDIUM,
                        titleRes = R.string.update_master_key_title,
                        textAlign = TextAlign.Center
                    )

                    BrownieTextFieldPassword(
                        modifier = Modifier
                            .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        labelRes = R.string.update_master_key_current_key_label,
                        placeHolderRes = R.string.update_master_key_current_key_placeholder,
                        value = currentMasterKey,
                        onValueChanged = {
                            if (it.length <= 8) {
                                actionListener.onMasterKeyUpdated(masterKey = it)
                            }
                        },
                        leadingIconRes = R.drawable.icon_lock,
                        supportingText = {
                            "${currentMasterKey.length}/8"
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BrownieTextFieldPassword(
                        modifier = Modifier
                            .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        labelRes = R.string.update_master_key_new_key_label,
                        placeHolderRes = R.string.update_master_key_new_key_placeholder,
                        value = newMasterKey,
                        onValueChanged = {
                            if (it.length <= 8) {
                                actionListener.onNewMasterKeyUpdated(newMasterKey = it)
                            }
                        },
                        leadingIconRes = R.drawable.icon_lock,
                        supportingText = {
                            "${newMasterKey.length}/8"
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BrownieTextFieldPassword(
                        modifier = Modifier
                            .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        labelRes = R.string.update_master_key_confirm_new_key_label,
                        placeHolderRes = R.string.update_master_key_confirm_new_key_placeholder,
                        value = confirmNewMasterKey,
                        onValueChanged = {
                            if (it.length <= 8) {
                                actionListener.onRepeatNewMasterKeyUpdated(newRepeatMasterKey = it)
                            }
                        },
                        leadingIconRes = R.drawable.icon_lock,
                        supportingText = {
                            "${confirmNewMasterKey.length}/8"
                        },
                        onDone = {
                            keyboard?.hide()
                            actionListener.onSave()
                        }
                    )

                    BrownieButton(
                        modifier = Modifier
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .fillMaxWidth(),
                        type = BrownieButtonTypeEnum.LARGE,
                        textRes = R.string.update_master_key_confirm_button
                    ) {
                        actionListener.onSave()
                    }
                }
            }
        }
    }
}