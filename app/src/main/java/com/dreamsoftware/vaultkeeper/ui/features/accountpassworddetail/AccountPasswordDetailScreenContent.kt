package com.dreamsoftware.vaultkeeper.ui.features.accountpassworddetail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieDefaultTextField
import com.dreamsoftware.brownie.component.BrownieImageIcon
import com.dreamsoftware.brownie.component.BrownieImageSize
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextFieldPassword
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.BrownieType
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.brownie.utils.clickWithRipple
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.core.components.LoadingDialog

@Composable
fun AccountPasswordDetailScreenContent(
    uiState: AccountPasswordDetailUiState,
    actionListener: AccountPasswordDetailScreenActionListener
) {
    with(uiState) {
        with(MaterialTheme.colorScheme) {
            LoadingDialog(isShowingDialog = isLoading)
            BrownieScreenContent(
                hasTopBar = false,
                errorMessage = errorMessage,
                infoMessage = infoMessage,
                enableVerticalScroll = true,
                screenContainerColor = primary,
                onInfoMessageCleared = actionListener::onInfoMessageCleared,
                onErrorMessageCleared = actionListener::onErrorMessageCleared,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(16.dp))
                    BrownieImageIcon(
                        type = BrownieType.ICON,
                        size = BrownieImageSize.LARGE,
                        iconRes = R.drawable.icon_arrow_left,
                        tintColor = Color.White,
                        modifier = Modifier.clickWithRipple {
                            actionListener.onCancel()
                        }
                    )

                    BrownieText(
                        modifier = Modifier.padding(
                            top = 30.dp, bottom = 30.dp,
                            start = 16.dp, end = 16.dp
                        ),
                        type = BrownieTextTypeEnum.TITLE_LARGE,
                        titleRes = R.string.account_password_detail_screen_title,
                        textColor = MaterialTheme.colorScheme.onPrimary
                    )
                }

                BrownieSheetSurface(enableVerticalScroll = false) {

                    BrownieDefaultTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        value = accountName,
                        labelRes = R.string.account_password,
                        placeHolderRes = R.string.account_password_placeholder,
                        supportingText = {
                            "${username.length}/35"
                        },
                        isSingleLine = true,
                        leadingIconRes = R.drawable.icon_card_number,
                        isReadOnly = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BrownieDefaultTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        value = username,
                        labelRes = R.string.username_label,
                        placeHolderRes = R.string.username_placeholder,
                        supportingText = {
                            "${username.length}/35"
                        },
                        isSingleLine = true,
                        leadingIconRes = R.drawable.icon_username,
                        isReadOnly = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BrownieDefaultTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        value = email,
                        labelRes = R.string.email_label,
                        placeHolderRes = R.string.email_placeholder,
                        supportingText = { "${email.length}/35" },
                        isSingleLine = true,
                        leadingIconRes = R.drawable.icon_email,
                        keyboardType = KeyboardType.Email,
                        isReadOnly = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BrownieDefaultTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        value = mobileNumber,
                        labelRes = R.string.mobile_number_label,
                        placeHolderRes = R.string.mobile_number_placeholder,
                        supportingText = { "${mobileNumber.length}/10" },
                        isSingleLine = true,
                        leadingIconRes = R.drawable.icon_call,
                        keyboardType = KeyboardType.Number,
                        isReadOnly = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BrownieTextFieldPassword(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        labelRes = R.string.password_label,
                        placeHolderRes = R.string.password_placeholder,
                        value = password,
                        isReadOnly = true,
                        leadingIconRes = R.drawable.icon_lock,
                        supportingText = {
                            "${password.length}/25"
                        },
                        enableTextFieldSeparator = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))


                    BrownieDefaultTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(200.dp),
                        value = note,
                        maxLines = 6,
                        isSingleLine = false,
                        labelRes = R.string.note_label,
                        placeHolderRes = R.string.note_placeholder,
                        supportingText = { "${note.length}/140" },
                        leadingIconRes = R.drawable.icon_note,
                        isReadOnly = true,
                        enableTextFieldSeparator = true,
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}