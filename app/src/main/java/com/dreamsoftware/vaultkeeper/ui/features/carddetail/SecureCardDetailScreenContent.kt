package com.dreamsoftware.vaultkeeper.ui.features.carddetail

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
import com.dreamsoftware.brownie.component.BrownieButton
import com.dreamsoftware.brownie.component.BrownieButtonStyleTypeEnum
import com.dreamsoftware.brownie.component.BrownieButtonTypeEnum
import com.dreamsoftware.brownie.component.BrownieDefaultTextField
import com.dreamsoftware.brownie.component.BrownieDialog
import com.dreamsoftware.brownie.component.BrownieImageIcon
import com.dreamsoftware.brownie.component.BrownieImageSize
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.BrownieType
import com.dreamsoftware.brownie.component.core.BrownieMaskVisualTransformation
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.brownie.utils.clickWithRipple
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.core.components.LoadingDialog
import com.dreamsoftware.vaultkeeper.ui.core.components.SecureCardPreview
import com.dreamsoftware.vaultkeeper.ui.utils.toCardProviderImage
import com.dreamsoftware.vaultkeeper.utils.secureCardVisualTransformation

private val secureCardVisualTransformation = secureCardVisualTransformation()

@Composable
fun SecureCardDetailScreenContent(
    uiState: SecureCardDetailUiState,
    actionListener: SecureCardDetailScreenActionListener
) {
    with(uiState) {
        with(MaterialTheme.colorScheme) {
            LoadingDialog(isShowingDialog = isLoading)
            BrownieDialog(
                isVisible = showCardDeleteDialog,
                mainLogoRes = R.drawable.main_logo_inverse,
                titleRes = R.string.delete_card_dialog_title,
                descriptionRes = R.string.delete_card_dialog_description,
                cancelRes = R.string.delete_card_dialog_cancel,
                acceptRes = R.string.delete_card_dialog_accept,
                onCancelClicked = actionListener::onDeleteSecureCardCancelled,
                onAcceptClicked = actionListener::onDeleteSecureCardConfirmed
            )
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
                        titleRes = R.string.secure_card_detail_screen_title,
                        textColor = onPrimary
                    )
                }

                BrownieSheetSurface(enableVerticalScroll = false) {

                    Spacer(modifier = Modifier.height(16.dp))

                    SecureCardPreview(
                        cardHolderName = cardHolderName,
                        cardNumber = cardNumber,
                        cardExpiryDate = cardExpiryDate,
                        cardCVV = cardCVV,
                        cardIcon = cardProviderEnum.toCardProviderImage(),
                        cardProviderEnum = cardProviderEnum
                    )

                    Spacer(modifier = Modifier.height(22.dp))


                    Spacer(modifier = Modifier.height(16.dp))

                    BrownieDefaultTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        value = cardNumber,
                        isReadOnly = true,
                        labelRes = R.string.card_number,
                        placeHolderRes = R.string.card_number_placeholder,
                        supportingText = {
                            "${cardNumber.length}/16"
                        },
                        keyboardType = KeyboardType.Number,
                        visualTransformation = secureCardVisualTransformation,
                        isSingleLine = true,
                        leadingIconRes = R.drawable.icon_card_number
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BrownieDefaultTextField(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        value = cardHolderName,
                        isReadOnly = true,
                        isSingleLine = true,
                        labelRes = R.string.card_holder_name,
                        placeHolderRes = R.string.card_holder_name_placeholder,
                        leadingIconRes = R.drawable.icon_username
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BrownieDefaultTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        labelRes = R.string.card_expiry_date,
                        placeHolderRes = R.string.card_expiry_date_placeholder,
                        value = cardExpiryDate,
                        isReadOnly = true,
                        supportingText = {
                            "mm/yy"
                        },
                        visualTransformation = BrownieMaskVisualTransformation("##/##"),
                        isSingleLine = true,
                        leadingIconRes = R.drawable.icon_date,
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BrownieDefaultTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = cardCVV,
                        labelRes = R.string.card_cvv,
                        placeHolderRes = R.string.card_cvv_placeholder,
                        supportingText = {
                            "${cardCVV.length}/3"
                        },
                        isSingleLine = true,
                        leadingIconRes = R.drawable.icon_secret,
                        keyboardType = KeyboardType.Number,
                        isReadOnly = true
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    BrownieButton(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .fillMaxWidth(),
                        type = BrownieButtonTypeEnum.LARGE,
                        onClick = actionListener::onEditSecureCard,
                        textRes = R.string.secure_card_detail_edit_button
                    )

                    BrownieButton(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .fillMaxWidth(),
                        type = BrownieButtonTypeEnum.LARGE,
                        style = BrownieButtonStyleTypeEnum.DANGER,
                        onClick = actionListener::onDeleteSecureCard,
                        textRes = R.string.secure_card_detail_delete_button
                    )
                }
            }
        }
    }
}