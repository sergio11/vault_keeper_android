package com.dreamsoftware.vaultkeeper.ui.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieBottomSheet
import com.dreamsoftware.brownie.component.BrownieDialog
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.features.settings.components.SettingsItemRow
import com.dreamsoftware.vaultkeeper.ui.features.settings.components.UserProfileRow
import com.dreamsoftware.vaultkeeper.ui.theme.BgBlack

@Composable
fun SettingsScreenContent(
    uiState: SettingsUiState,
    actionListener: SettingsScreenActionListener
) {
    with(uiState) {
        with(MaterialTheme.colorScheme) {
            BrownieDialog(
                isVisible = showCloseSessionDialog,
                mainLogoRes = R.drawable.main_logo_inverse,
                titleRes = R.string.close_session_dialog_title,
                descriptionRes = R.string.close_session_dialog_description,
                cancelRes = R.string.close_session_dialog_cancel,
                acceptRes = R.string.close_session_dialog_accept,
                onCancelClicked = {
                    actionListener.onUpdateCloseSessionDialogVisibility(isVisible = false)
                },
                onAcceptClicked = {
                    actionListener.onCloseSession()
                }
            )

            BrownieScreenContent(
                hasTopBar = false,
                screenContainerColor = primary
            ) {
                BrownieText(
                    modifier = Modifier
                        .padding(top = 18.dp, bottom = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    titleText = "Settings",
                    textColor = onPrimary,
                    type = BrownieTextTypeEnum.TITLE_MEDIUM,
                    textBold = true
                )
                BrownieSheetSurface(
                    verticalArrangement = Arrangement.Top
                ) {
                    UserProfileRow(userData = null)
                    HorizontalDivider(
                        color = Color.LightGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 16.dp,
                                horizontal = 46.dp
                            )
                    )
                    items.forEach {
                        SettingsItemRow(
                            item = it,
                            onClicked = actionListener::onSettingItemClicked
                        )
                    }
                }
            }
            if (showSheet) {
                BrownieBottomSheet(
                    onDismiss = {
                        actionListener.onUpdateSheetVisibility(isVisible = false)
                    },
                    content = {
                        Column(
                            Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BrownieText(
                                modifier = Modifier
                                    .padding(16.dp),
                                type = BrownieTextTypeEnum.LABEL_MEDIUM,
                                titleRes = R.string.about_info,
                                textColor = Color.Black
                            )
                            Spacer(modifier = Modifier.navigationBarsPadding())
                            Spacer(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .background(color = BgBlack)
                            )
                        }
                    }
                )
            }
        }
    }
}