package com.dreamsoftware.vaultkeeper.ui.features.account.createmasterkey

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieButtonTypeEnum
import com.dreamsoftware.brownie.component.BrownieLoadingDialog
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextFieldPassword
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.vaultkeeper.R

@Composable
fun CreateMasterKeyScreenContent(
    uiState: CreateMasterKeyUiState,
    actionListener: CreateMasterKeyScreenActionListener
) {
    with(uiState) {
        BrownieLoadingDialog(
            isShowingDialog = isLoading,
            mainLogoRes = R.drawable.icon_home,
            titleRes = R.string.loading_dialog_title_text,
            descriptionRes = R.string.loading_dialog_description_text
        )
        BrownieScreenContent(
            enableVerticalScroll = false,
            hasTopBar = false,
            errorMessage = error,
            backgroundRes = R.drawable.main_background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
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
                    modifier = Modifier
                        .padding(start = 16.dp, end = 32.dp),
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    type = BrownieTextTypeEnum.HEADLINE_MEDIUM,
                    titleRes = R.string.setup_key_tagline_1
                )
                BrownieText(
                    modifier = Modifier
                        .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    type = BrownieTextTypeEnum.BODY_LARGE,
                    titleRes = R.string.setup_key_tagline_2,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                CreateMasterKeySheetContent(
                    uiState = uiState,
                    actionListener = actionListener
                )
            }
        }
    }
}

@Composable
private fun CreateMasterKeySheetContent(
    uiState: CreateMasterKeyUiState,
    actionListener: CreateMasterKeyScreenActionListener
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

                BrownieSheetSurface {
                    val keyboard = LocalSoftwareKeyboardController.current

                    BrownieText(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 6.dp)
                            .fillMaxWidth(),
                        type = BrownieTextTypeEnum.TITLE_MEDIUM,
                        titleRes = R.string.setup_master_key,
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
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BrownieTextFieldPassword(
                        modifier = Modifier
                            .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        labelRes = R.string.create_master_confirm_key_label,
                        placeHolderRes = R.string.create_master_confirm_key_placeholder,
                        value = confirmMasterKey,
                        onValueChanged = {
                            if (it.length <= 8) {
                                actionListener.onRepeatMasterKeyUpdated(newRepeatMasterKey = it)
                            }
                        },
                        leadingIconRes = R.drawable.icon_lock,
                        supportingText = {
                            "${confirmMasterKey.length}/8"
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
                        text = "Save Master Key"
                    ) {
                        actionListener.onSave()
                    }
                }
            }
        }
    }
}