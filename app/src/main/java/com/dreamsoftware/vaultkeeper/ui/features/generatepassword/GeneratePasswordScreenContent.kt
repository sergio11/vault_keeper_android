package com.dreamsoftware.vaultkeeper.ui.features.generatepassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieSelectionRow
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieSliderRow
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.features.home.components.BrownieIconButton

@Composable
fun GeneratePasswordScreenContent(
    uiState: GenerateUiState,
    actionListener: GeneratePasswordScreenActionListener
) {
    with(uiState) {
        with(MaterialTheme.colorScheme) {
            BrownieScreenContent(
                hasTopBar = false,
                enableVerticalScroll = true,
                screenContainerColor = primary,
                infoMessage = infoMessage,
                errorMessage = errorMessage,
                onInfoMessageCleared = actionListener::onInfoMessageCleared,
                onErrorMessageCleared = actionListener::onErrorMessageCleared,
            ) {
                BrownieText(
                    modifier = Modifier
                        .padding(top = 18.dp, bottom = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    titleRes = R.string.generator_password_screen_title,
                    type = BrownieTextTypeEnum.TITLE_LARGE,
                    textColor = onPrimary
                )

                Surface(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .height(140.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    contentColor = surfaceContainer
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

                BrownieSheetSurface(
                    enableVerticalScroll = false,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    BrownieText(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        type = BrownieTextTypeEnum.TITLE_SMALL,
                        textAlign = TextAlign.Center,
                        titleRes = R.string.generator_password_screen_options,
                    )

                    BrownieSliderRow(
                        titleRes = R.string.generator_password_screen_length,
                        value = passwordLength.toFloat(),
                        onValueChange = { actionListener.onPasswordLength(newLength = it.toInt()) },
                        valueRange = 6f..15f
                    )

                    BrownieSelectionRow(
                        titleRes = R.string.generator_password_screen_lower_case,
                        checked = lowerCase,
                        onCheckedChange = actionListener::onLowerCaseChanged
                    )

                    BrownieSelectionRow(
                        titleRes = R.string.generator_password_screen_upper_case,
                        checked = upperCase,
                        onCheckedChange = actionListener::onUpperCaseChanged
                    )

                    BrownieSelectionRow(
                        titleRes = R.string.generator_password_screen_digits,
                        checked = digits,
                        onCheckedChange = actionListener::onDigitsChanged
                    )

                    BrownieSelectionRow(
                        titleRes = R.string.generator_password_screen_special_characters,
                        checked = specialCharacters,
                        onCheckedChange = actionListener::onSpecialCharactersChanged
                    )
                    Spacer(modifier = Modifier.height(34.dp))
                    GeneratePasswordScreenActions(
                        actionListener = actionListener
                    )
                }
            }
        }
    }
}

@Composable
private fun GeneratePasswordScreenActions(
    actionListener: GeneratePasswordScreenActionListener
) {
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

        BrownieIconButton(
            iconRes = R.drawable.icon_regenerate
        ) {
            actionListener.onValidateAndSave()
        }

        BrownieButton(
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
                .padding(start = 16.dp),
            textRes = R.string.generator_password_screen_copy_password,
            onClick = {
                actionListener.onPasswordCopied()
            }
        )
    }
}