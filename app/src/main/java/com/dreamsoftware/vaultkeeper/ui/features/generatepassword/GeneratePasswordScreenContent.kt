package com.dreamsoftware.vaultkeeper.ui.features.generatepassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.core.components.SheetSurface
import com.dreamsoftware.vaultkeeper.ui.features.generatepassword.components.CustomSlider
import com.dreamsoftware.vaultkeeper.ui.theme.BgBlack
import com.dreamsoftware.vaultkeeper.ui.theme.Blue
import com.dreamsoftware.vaultkeeper.ui.theme.LightBlue
import com.dreamsoftware.vaultkeeper.util.clickWithRipple

@Composable
fun GeneratePasswordScreenContent(
    uiState: GenerateUiState,
    clipboardManager: ClipboardManager,
    actionListener: GeneratePasswordScreenActionListener
) {
    with(uiState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BgBlack),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BrownieText(
                modifier = Modifier.padding(
                    top = 18.dp, bottom = 12.dp
                ),
                titleText = "Password Generator",
                type = BrownieTextTypeEnum.TITLE_LARGE,
                textColor = MaterialTheme.colorScheme.onPrimary
            )

            Surface(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .height(140.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                contentColor = MaterialTheme.colorScheme.surfaceContainer

            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    BrownieText(
                        type = BrownieTextTypeEnum.HEADLINE_MEDIUM,
                        titleText = password
                    )
                }
            }
            SheetSurface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    BrownieText(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        type = BrownieTextTypeEnum.TITLE_SMALL,
                        textAlign = TextAlign.Center,
                        titleText = "Options",
                    )
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BrownieText(
                            type = BrownieTextTypeEnum.LABEL_LARGE,
                            titleText = "Length",
                        )
                        CustomSlider(
                            value = passwordLength.toFloat(),
                            onValueChange = { actionListener.onPasswordLength(newLength = it.toInt())},
                            valueRange = 6f..15f
                        )
                    }

                    SelectionRow(
                        text = "Lower case",
                        checked = lowerCase,
                        onCheckedChange = actionListener::onLowerCaseChanged
                    )

                    SelectionRow(
                        text = "Upper case",
                        checked = upperCase,
                        onCheckedChange = actionListener::onUpperCaseChanged
                    )

                    SelectionRow(
                        text = "Digits",
                        checked = digits,
                        onCheckedChange = actionListener::onDigitsChanged
                    )

                    SelectionRow(
                        text = "Special characters",
                        checked = specialCharacters,
                        onCheckedChange = actionListener::onSpecialCharactersChanged
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 22.dp,
                                end = 22.dp,
                                bottom = 32.dp
                            )
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .shadow(10.dp)
                                .background(color = BgBlack)
                                .clickWithRipple {
                                    actionListener.onValidateAndSave()
                                },
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(all = 8.dp)
                                    .size(40.dp),
                                painter = painterResource(R.drawable.icon_regenerate),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        BrownieButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .padding(
                                    start = 16.dp
                                ),
                            text = "Copy Password",
                            onClick = {
                                clipboardManager.setText(
                                    AnnotatedString((password))
                                )
                                actionListener.onPasswordCopied()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectionRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BrownieText(
            type = BrownieTextTypeEnum.LABEL_LARGE,
            titleText = text
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            modifier = Modifier.size(42.dp),
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = Blue,
                uncheckedTrackColor = LightBlue,
                uncheckedBorderColor = LightBlue,
                uncheckedThumbColor = Color.White
            )
        )
    }
}