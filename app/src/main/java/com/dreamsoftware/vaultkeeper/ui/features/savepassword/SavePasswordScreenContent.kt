package com.dreamsoftware.vaultkeeper.ui.features.savepassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieButtonTypeEnum
import com.dreamsoftware.brownie.component.BrownieDefaultTextField
import com.dreamsoftware.brownie.component.BrownieImageIcon
import com.dreamsoftware.brownie.component.BrownieImageSize
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextFieldPassword
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.BrownieType
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.core.components.SheetSurface
import com.dreamsoftware.vaultkeeper.ui.features.savepassword.component.SearchOutlinedTextFieldWithDropdown
import com.dreamsoftware.vaultkeeper.ui.theme.BgBlack
import com.dreamsoftware.vaultkeeper.utils.clickWithRipple

@Composable
fun SavePasswordScreenContent(
    uiState: SavePasswordUiState,
    actionListener: SavePasswordScreenActionListener
) {
    with(uiState) {
        Column(
            modifier = Modifier
                .background(color = BgBlack)
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
                        top = 18.dp, bottom = 12.dp,
                        start = 16.dp, end = 16.dp
                    ),
                    type = BrownieTextTypeEnum.TITLE_LARGE,
                    titleText = if (isEditScreen) "Edit Password" else "Add New Password",
                    textColor = MaterialTheme.colorScheme.onPrimary
                )
            }

            SheetSurface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {

                    Spacer(modifier = Modifier.height(12.dp))

                    SearchOutlinedTextFieldWithDropdown(
                        uiState = uiState,
                        actionListener = actionListener
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
                                .weight(1f)
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
                            prefix = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextFieldSeparator(24)
                                }
                            }
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
                        labelRes = R.string.note_label,
                        placeHolderRes = R.string.note_placeholder,
                        supportingText = { "${note.length}/140" },
                        leadingIconRes = R.drawable.icon_note,
                        keyboardType = KeyboardType.Number,
                        onValueChanged = {
                            if (it.length <= 140) {
                                actionListener.onNoteUpdated(note = it)
                            }
                        },
                        prefix = {
                            Row(
                                modifier = Modifier.padding(top = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextFieldSeparator(134)
                            }
                        },
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
                        text =  if (isEditScreen) "Update Password" else "Save Password"
                    )
                }
            }
        }
    }
}

@Composable
private fun TextFieldSeparator(
    height: Int
) {
    Box(
        modifier = Modifier
            .padding(end = 12.dp)
            .height(height.dp)
            .width(1.dp)
            .background(color = Color.LightGray),
        contentAlignment = Alignment.Center
    ) {}
}