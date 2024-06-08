package com.dreamsoftware.vaultkeeper.ui.features.savecard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieButtonTypeEnum
import com.dreamsoftware.brownie.component.BrownieDefaultTextField
import com.dreamsoftware.brownie.component.BrownieImageIcon
import com.dreamsoftware.brownie.component.BrownieImageSize
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.BrownieType
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.theme.Gray
import com.dreamsoftware.vaultkeeper.ui.theme.poppinsFamily
import com.dreamsoftware.vaultkeeper.utils.MaskVisualTransformation
import com.dreamsoftware.vaultkeeper.utils.cardSuggestions
import com.dreamsoftware.vaultkeeper.utils.clickWithRipple
import kotlinx.coroutines.delay

@Composable
fun SaveCardScreenContent(
    uiState: SaveCardUiState,
    actionListener: SaveCardScreenActionListener
) {
    with(uiState) {
        with(MaterialTheme.colorScheme) {
            BrownieScreenContent(
                hasTopBar = false,
                enableVerticalScroll = true,
                screenContainerColor = primary
            ) {
                val focusManager = LocalFocusManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(16.dp))
                    BrownieImageIcon(
                        modifier = Modifier.clickWithRipple {
                            actionListener.onCancel()
                        },
                        type = BrownieType.ICON,
                        size = BrownieImageSize.LARGE,
                        iconRes = R.drawable.icon_arrow_left,
                        tintColor = Color.White
                    )
                    BrownieText(
                        modifier = Modifier.padding(
                            top = 30.dp, bottom = 30.dp,
                            start = 16.dp, end = 16.dp
                        ),
                        type = BrownieTextTypeEnum.TITLE_LARGE,
                        titleText = if (isEditScreen) "Edit Card" else "Add New Card",
                        textColor = onPrimary
                    )
                }

                BrownieSheetSurface(enableVerticalScroll = false) {

                    Spacer(modifier = Modifier.height(16.dp))

                    val matchingImage =
                        cardSuggestions.firstOrNull { it.first == cardProviderName }?.second

                    val painter = matchingImage ?: R.drawable.icon_card

                    CardUi(
                        cardHolderName = cardHolderName,
                        cardNumber = cardNumber,
                        cardExpiryDate = cardExpiryDate,
                        cardCVV = cardCVV,
                        cardIcon = painter
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    TextFieldDropDown(
                        uiState = uiState,
                        actionListener = actionListener
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BrownieDefaultTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        value = cardNumber,
                        onValueChanged = {
                            actionListener.onCardNumberUpdated(newCardNumber = it)
                        },
                        labelRes = R.string.card_number,
                        placeHolderRes = R.string.card_number_placeholder,
                        supportingText = {
                            "${cardNumber.length}/16"
                        },
                        keyboardType = KeyboardType.Number,
                        visualTransformation = visualTransformation,
                        isSingleLine = true,
                        leadingIconRes = R.drawable.icon_card_number
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BrownieDefaultTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        value = cardHolderName,
                        onValueChanged = {
                            actionListener.onCardHolderNameUpdated(newCardHolderName = it)
                        },
                        isSingleLine = true,
                        labelRes = R.string.card_holder_name,
                        placeHolderRes = R.string.card_holder_name_placeholder,
                        leadingIconRes = R.drawable.icon_username
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        BrownieDefaultTextField(
                            modifier = Modifier
                                .weight(1.4f)
                                .padding(end = 6.dp),
                            labelRes = R.string.card_expiry_date,
                            placeHolderRes = R.string.card_expiry_date_placeholder,
                            value = cardExpiryDate,
                            onValueChanged = {
                                if (it.length <= 4) {
                                    actionListener.onCardExpiryDateUpdated(it)
                                }
                            },
                            supportingText = {
                                "mm/yy"
                            },
                            visualTransformation = MaskVisualTransformation("##/##"),
                            isSingleLine = true,
                            leadingIconRes = R.drawable.icon_date,
                            keyboardType = KeyboardType.Number,
                            onDone = {
                                focusManager.moveFocus(FocusDirection.Right)
                            }
                        )
                        BrownieDefaultTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 6.dp),
                            value = cardCVV,
                            labelRes = R.string.card_cvv,
                            placeHolderRes = R.string.card_cvv_placeholder,
                            supportingText = {
                                "${cardCVV.length}/3"
                            },
                            isSingleLine = true,
                            leadingIconRes = R.drawable.icon_secret,
                            keyboardType = KeyboardType.Number,
                            onValueChanged = {
                                if (it.length <= 3) {
                                    actionListener.onCardCvvUpdated(it)
                                }
                            },
                            onDone = {
                                keyboardController?.hide()
                                actionListener.onSaveSecureCard()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    BrownieButton(
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .fillMaxWidth(),
                        type = BrownieButtonTypeEnum.LARGE,
                        onClick = actionListener::onSaveSecureCard,
                        text = if (isEditScreen) "Update Card" else "Save Card"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDropDown(
    uiState: SaveCardUiState,
    actionListener: SaveCardScreenActionListener
) {
    with(uiState) {
        val keyboardController = LocalSoftwareKeyboardController.current
        ExposedDropdownMenuBox(
            modifier = Modifier.padding(horizontal = 16.dp),
            expanded = expandedProviderField,
            onExpandedChange = {
                actionListener.onExpandedProviderFieldUpdated(!expandedProviderField)
            },
        ) {
            BrownieDefaultTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                isReadOnly = true,
                isSingleLine = true,
                value = cardProviderName,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandedProviderField
                    )
                },
                labelRes = R.string.card_provider,
                placeHolderRes = R.string.card_provider_placeholder
            )

            DropdownMenu(
                modifier = Modifier
                    .background(Color.White)
                    .exposedDropdownSize(true),
                properties = PopupProperties(focusable = false),
                expanded = expandedProviderField,
                onDismissRequest = {
                    actionListener.onExpandedProviderFieldUpdated(false)
                },
            ) {
                cardSuggestions.forEach { cardInfo ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = cardInfo.first,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = poppinsFamily,
                                    color = Gray
                                )
                            )
                        },
                        onClick = {
                            actionListener.onCardProviderUpdated(
                                cardProviderName = cardInfo.first,
                                selectedCardImage = cardInfo.second
                            )
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }

            LaunchedEffect(hideKeyboard) {
                delay(100)
                keyboardController?.hide()
            }
        }
    }
}

val creditCardOffsetMapping = object : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        if (offset <= 3) return offset
        if (offset <= 7) return offset + 1
        if (offset <= 11) return offset + 2
        if (offset <= 16) return offset + 3
        return 19
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset <= 4) return offset
        if (offset <= 9) return offset - 1
        if (offset <= 14) return offset - 2
        if (offset <= 19) return offset - 3
        return 16
    }
}

// Making XXXX-XXXX-XXXX-XXXX string.
private val visualTransformation = VisualTransformation { text ->
    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
    var out = ""

    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 4 == 3 && i != 15) out += " "
    }
    TransformedText(
        AnnotatedString(out),
        creditCardOffsetMapping
    )
}
