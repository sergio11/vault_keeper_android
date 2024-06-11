package com.dreamsoftware.vaultkeeper.ui.features.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.dreamsoftware.vaultkeeper.ui.theme.poppinsFamily
import com.dreamsoftware.vaultkeeper.ui.utils.obfuscateSecret
import com.dreamsoftware.vaultkeeper.ui.utils.toCardProviderImage
import com.dreamsoftware.vaultkeeper.utils.generateRandomBrush

@Composable
fun SecureCardRow(
    card: SecureCardBO,
    actionListener: HomeScreenActionListener
) {

    with(MaterialTheme.colorScheme) {
        var expanded by remember { mutableStateOf(false) }

        val contentBrush by remember { mutableStateOf(generateRandomBrush()) }

        val clipboardManager: ClipboardManager = LocalClipboardManager.current

        BrownieCardRow(contentBrush = contentBrush) {

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
                    containerColor = Color.Transparent
                ) {
                    clipboardManager.setText(AnnotatedString(card.cardNumber))
                    //viewModel.showCopyMsg(stringType = "Card Number")
                }

                Box {

                    BrownieIconButton(
                        iconRes = R.drawable.icon_more,
                        containerColor = Color.Transparent
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
                                        fontFamily = poppinsFamily,
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
                                        fontFamily = poppinsFamily,
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
    iconTintColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    onClicked: () -> Unit = {}
) {
    IconButton(
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
                .size(24.dp),
            painter = painterResource(iconRes),
            contentDescription = "Copy Icon",
            tint = iconTintColor
        )
    }
}