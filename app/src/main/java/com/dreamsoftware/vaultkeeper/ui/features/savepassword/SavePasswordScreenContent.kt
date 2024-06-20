package com.dreamsoftware.vaultkeeper.ui.features.savepassword

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieButtonTypeEnum
import com.dreamsoftware.brownie.component.BrownieDefaultTextField
import com.dreamsoftware.brownie.component.BrownieFieldDropdown
import com.dreamsoftware.brownie.component.BrownieImageIcon
import com.dreamsoftware.brownie.component.BrownieImageSize
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextFieldPassword
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.BrownieType
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.core.components.LoadingDialog
import com.dreamsoftware.vaultkeeper.utils.clickWithRipple

@Composable
fun SavePasswordScreenContent(
    uiState: SavePasswordUiState,
    actionListener: SavePasswordScreenActionListener
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
                        titleRes = if (isEditScreen) {
                            R.string.edit_account_password_title
                        } else {
                            R.string.add_new_account_password_title
                        },
                        textColor = MaterialTheme.colorScheme.onPrimary
                    )
                }

                BrownieSheetSurface(enableVerticalScroll = false) {

                    val context =  LocalContext.current

                    BrownieFieldDropdown(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        labelRes = R.string.account_password,
                        placeHolderRes = R.string.account_password_placeholder,
                        value = accountName,
                        onValueChanged = actionListener::onFilterByAccountName,
                        menuItems = accountSuggestionsMenuItems,
                        leadingIconRes = R.drawable.icon_card_number,
                        enableKeyboardController = false,
                        onMenuItemClicked = {
                            val accountName = it.text ?: it.textRes?.let(context::getString)
                            if(!accountName.isNullOrBlank()) {
                                actionListener.onAccountNameUpdated(accountName)
                            }
                        }
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
                        onValueChanged = {
                            if (it.length <= 35) {
                                actionListener.onUsernameUpdated(username = it)
                            }
                        }
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
                        onValueChanged = {
                            if (it.length <= 35) {
                                actionListener.onEmailUpdated(email = it)
                            }
                        }
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
                        onValueChanged = {
                            if (it.length <= 10) {
                                actionListener.onMobileNumberUpdated(mobileNumber = it)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        BrownieTextFieldPassword(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(end = 6.dp),
                            labelRes = R.string.password_label,
                            placeHolderRes = R.string.password_placeholder,
                            value = password,
                            onValueChanged = {
                                if (it.length <= 25) {
                                    actionListener.onPasswordUpdated(password = it)
                                }
                            },
                            leadingIconRes = R.drawable.icon_lock,
                            supportingText = {
                                "${password.length}/25"
                            },
                            enableTextFieldSeparator = true
                        )

                        Icon(
                            modifier = Modifier
                                .padding(top = 18.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                                .size(34.dp)
                                .clickWithRipple {
                                    actionListener.onGenerateRandomPassword()
                                },
                            painter = painterResource(R.drawable.icon_regenerate),
                            tint = Color.Black,
                            contentDescription = null
                        )
                    }

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
                        onValueChanged = {
                            if (it.length <= 140) {
                                actionListener.onNoteUpdated(note = it)
                            }
                        },
                        enableTextFieldSeparator = true,
                        onDone = {
                            actionListener.onSave()
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    BrownieButton(
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth(),
                        type = BrownieButtonTypeEnum.LARGE,
                        onClick = actionListener::onSave,
                        textRes =  if (isEditScreen) {
                            R.string.update_account_password_button
                        } else {
                            R.string.create_account_password_button
                        }
                    )
                }
            }
        }
    }
}