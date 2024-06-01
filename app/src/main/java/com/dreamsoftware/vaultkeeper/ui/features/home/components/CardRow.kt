package com.dreamsoftware.vaultkeeper.ui.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.ui.features.home.HomeScreenActionListener
import com.dreamsoftware.vaultkeeper.ui.theme.Gray
import com.dreamsoftware.vaultkeeper.ui.theme.poppinsFamily
import com.dreamsoftware.vaultkeeper.utils.cardSuggestions

@Composable
fun CardRow(
    card: SecureCardBO,
    actionListener: HomeScreenActionListener
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        var expanded by remember { mutableStateOf(false) }

        val clipboardManager: ClipboardManager = LocalClipboardManager.current

        val matchingImage =
            cardSuggestions.firstOrNull { it.first == card.cardProvider }?.second

        val painter = matchingImage ?: R.drawable.icon_card

        val tint = if (painter == R.drawable.icon_card) {
            Gray
        } else {
            Color.Unspecified
        }

        val cardNumber = card.cardNumber.takeLast(4).padStart(card.cardNumber.length, '*')

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp, end = 12.dp,
                    top = 12.dp, bottom = 12.dp
                )
        ) {

            Icon(
                painter = painterResource(painter),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp),
                tint = tint
            )

            Column(
                modifier = Modifier
                    .padding(start = 14.dp)
                    .weight(1f)
            ) {

                Text(
                    text = card.cardHolderName,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = cardNumber,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        clipboardManager.setText(
                            AnnotatedString(card.cardNumber)
                        )
                        //viewModel.showCopyMsg(stringType = "Card Number")
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(28.dp)
                            .padding(end = 6.dp),
                        painter = painterResource(R.drawable.icon_copy),
                        contentDescription = "Copy Icon"
                    )
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
                                        fontFamily = poppinsFamily,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            },
                            onClick = {
                                //add edit screen navigation
                                //navigator.navigate(CardScreenDestination(card.id))
                                expanded = false
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