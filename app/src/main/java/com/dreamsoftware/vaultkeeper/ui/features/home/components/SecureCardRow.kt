package com.dreamsoftware.vaultkeeper.ui.features.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreamsoftware.brownie.component.BrownieCardRow
import com.dreamsoftware.brownie.component.BrownieImageIcon
import com.dreamsoftware.brownie.component.BrownieImageSize
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.model.CardProviderEnum
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.ui.features.home.HomeScreenActionListener
import com.dreamsoftware.vaultkeeper.ui.theme.Gray
import com.dreamsoftware.vaultkeeper.ui.utils.obfuscateSecret
import com.dreamsoftware.vaultkeeper.ui.utils.toCardProviderBrush
import com.dreamsoftware.vaultkeeper.ui.utils.toCardProviderImage
import com.dreamsoftware.vaultkeeper.utils.clickWithRipple

@Composable
fun SecureCardRow(
    card: SecureCardBO,
    actionListener: HomeScreenActionListener
) {

    with(MaterialTheme.colorScheme) {
        val context = LocalContext.current
        var expanded by remember { mutableStateOf(false) }
        val cardBrush by rememberUpdatedState(card.cardProvider.toCardProviderBrush(context))
        BrownieCardRow(contentBrush = cardBrush) {

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

                Box {
                    BrownieIconButton(
                        iconRes = R.drawable.icon_more,
                        containerColor = background,
                        iconTintColor = primary,
                        containerSize = 32.dp,
                        iconSize = 28.dp,
                        iconPadding = 4.dp
                    ) {
                        expanded = true
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Edit",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            },
                            onClick = {
                                //add edit screen navigation
                                expanded = false
                                actionListener.onEditSecureCard(card.uid)
                            },
                            trailingIcon = {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(R.drawable.icon_edit),
                                    contentDescription = "Edit Icon"
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Delete", style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            },
                            onClick = {
                                actionListener.onDeleteSecureCard(card)
                                expanded = false
                            },
                            trailingIcon = {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(R.drawable.icon_delete),
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun BrownieIconButton(
    @DrawableRes iconRes: Int,
    isEnabled: Boolean = true,
    containerSize: Dp = 52.dp,
    iconSize: Dp = 40.dp,
    iconPadding: Dp = 8.dp,
    iconTintColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    onClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(containerSize)
            .clip(CircleShape)
            .shadow(10.dp)
            .background(color = containerColor)
            .clickWithRipple {
                onClicked()
            },
    ) {
        Icon(
            modifier = Modifier
                .padding(all = iconPadding)
                .size(iconSize),
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = iconTintColor
        )
    }

    /*IconButton(
        modifier = Modifier
            .size(28.dp),
        enabled = isEnabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor
        ),
        onClick = onClicked
    ) {
        Icon(
            modifier = Modifier
                .size(28.dp),
            painter = painterResource(iconRes),
            contentDescription = "Copy Icon",
            tint = iconTintColor
        )
    }*/
}