package com.dreamsoftware.vaultkeeper.ui.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreamsoftware.brownie.component.BrownieCardRow
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.ui.features.home.HomeScreenActionListener
import com.dreamsoftware.vaultkeeper.utils.suggestionsWithImages

@Composable
fun AccountPasswordRow(
    account: AccountPasswordBO,
    actionListener: HomeScreenActionListener
) {
    with(MaterialTheme.colorScheme) {
        var expanded by remember { mutableStateOf(false) }
        BrownieCardRow {
            val matchingImage =
                suggestionsWithImages.firstOrNull { it.first == account.accountName }?.second

            val painter = matchingImage ?: R.drawable.icon_others
            Image(
                painter = painterResource(painter),
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .padding(start = 14.dp)
                    .weight(1f)
            ) {

                BrownieText(
                    type = BrownieTextTypeEnum.TITLE_MEDIUM,
                    titleText = account.accountName,
                    textBold = true,
                    textColor = onPrimary
                )

                BrownieText(
                    type = BrownieTextTypeEnum.BODY_LARGE,
                    titleText = account.displayInfo,
                    singleLine = true,
                    textColor = onPrimary
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                BrownieIconButton(
                    iconRes = R.drawable.icon_copy,
                    containerColor = Color.Transparent,
                    containerSize = 28.dp,
                    iconSize = 24.dp,
                    iconPadding = 4.dp
                ) {
                    actionListener.onCopyAccountPasswordToClipboard(account.password)
                }

                Box {
                    IconButton(
                        modifier = Modifier
                            .size(26.dp)
                            .padding(end = 4.dp),
                        onClick = {
                            expanded = true
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(26.dp),
                            painter = painterResource(R.drawable.icon_more),
                            contentDescription = "Options Icon"
                        )
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
                                expanded = false
                                actionListener.onEditAccountPassword(account.uid)
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
                                actionListener.onDeleteAccount(account)
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
