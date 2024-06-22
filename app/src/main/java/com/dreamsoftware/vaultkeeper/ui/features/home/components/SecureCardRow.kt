package com.dreamsoftware.vaultkeeper.ui.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieCardRow
import com.dreamsoftware.brownie.component.BrownieDropdownMenuButton
import com.dreamsoftware.brownie.component.BrownieDropdownMenuButtonItem
import com.dreamsoftware.brownie.component.BrownieIconButton
import com.dreamsoftware.brownie.component.BrownieImageIcon
import com.dreamsoftware.brownie.component.BrownieImageSize
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.utils.clickWithRipple
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.model.CardProviderEnum
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.ui.features.home.HomeScreenActionListener
import com.dreamsoftware.vaultkeeper.ui.theme.Gray
import com.dreamsoftware.vaultkeeper.ui.utils.obfuscateSecret
import com.dreamsoftware.vaultkeeper.ui.utils.toCardProviderBrush
import com.dreamsoftware.vaultkeeper.ui.utils.toCardProviderImage

private const val EDIT_SECURE_CARD_ITEM_ID = "EDIT_SECURE_CARD"
private const val DELETE_SECURE_CARD_ITEM_ID = "DELETE_SECURE_CARD"

@Composable
fun SecureCardRow(
    card: SecureCardBO,
    actionListener: HomeScreenActionListener
) {

    with(MaterialTheme.colorScheme) {
        val context = LocalContext.current
        val cardBrush by rememberUpdatedState(card.cardProvider.toCardProviderBrush(context))
        BrownieCardRow(
            modifier = Modifier.clickWithRipple {  actionListener.onSecureCardClicked(card.uid) },
            contentBrush = cardBrush
        ) {

            BrownieImageIcon(
                iconRes = card.cardProvider.toCardProviderImage(),
                size = BrownieImageSize.LARGE,
                tintColor = if(card.cardProvider == CardProviderEnum.OTHER) {
                    Gray
                } else {
                    Color.Unspecified
                }
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {

                BrownieText(
                    type = BrownieTextTypeEnum.TITLE_MEDIUM,
                    titleText = card.cardHolderName,
                    textBold = true,
                    textColor = onPrimary
                )

                BrownieText(
                    type = BrownieTextTypeEnum.BODY_LARGE,
                    titleText = card.cardNumber.obfuscateSecret(4),
                    singleLine = true,
                    textColor = onPrimary
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                BrownieIconButton(
                    iconRes = R.drawable.icon_copy,
                    containerColor = background,
                    iconTintColor = primary,
                    containerSize = 32.dp,
                    iconSize = 28.dp,
                    iconPadding = 4.dp
                ) {
                    actionListener.onCopyCardNumberToClipboard(card.cardNumber)
                }
                
                Spacer(modifier = Modifier.size(4.dp))

                BrownieDropdownMenuButton(
                    iconRes = R.drawable.icon_more,
                    containerColor = background,
                    iconTintColor = primary,
                    containerSize = 32.dp,
                    iconSize = 28.dp,
                    iconPadding = 4.dp,
                    dropdownMenuItems = listOf(
                        BrownieDropdownMenuButtonItem(
                            id = EDIT_SECURE_CARD_ITEM_ID,
                            titleRes = R.string.edit_secure_card_dropdown_option_text,
                            iconRes = R.drawable.icon_edit
                        ),
                        BrownieDropdownMenuButtonItem(
                            id = DELETE_SECURE_CARD_ITEM_ID,
                            titleRes = R.string.delete_secure_card_dropdown_option_text,
                            iconRes = R.drawable.icon_delete
                        )
                    ),
                    onMenuItemClicked = {
                        when(it.id) {
                            EDIT_SECURE_CARD_ITEM_ID -> actionListener.onEditSecureCard(card.uid)
                            DELETE_SECURE_CARD_ITEM_ID -> actionListener.onDeleteSecureCard(card)
                        }
                    }
                )
            }
        }
    }
}