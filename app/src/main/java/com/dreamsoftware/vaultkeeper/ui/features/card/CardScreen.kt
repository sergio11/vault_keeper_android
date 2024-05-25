package com.dreamsoftware.vaultkeeper.ui.features.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.core.components.SheetSurface
import com.dreamsoftware.vaultkeeper.ui.features.password.TextFieldSeparator
import com.dreamsoftware.vaultkeeper.ui.theme.BgBlack
import com.dreamsoftware.vaultkeeper.ui.theme.Blue
import com.dreamsoftware.vaultkeeper.ui.theme.Gray
import com.dreamsoftware.vaultkeeper.ui.theme.poppinsFamily
import com.dreamsoftware.vaultkeeper.util.LocalSnackbar
import com.dreamsoftware.vaultkeeper.util.MaskVisualTransformation
import com.dreamsoftware.vaultkeeper.util.cardSuggestions
import com.dreamsoftware.vaultkeeper.util.clickWithRipple
import com.dreamsoftware.vaultkeeper.util.visualTransformation
import kotlinx.coroutines.delay

@Composable
fun CardScreen(
    viewModel: CardViewModel = hiltViewModel(),
    cardId: Int
) {

    if (cardId != -1) {
        viewModel.isEditScreen = true
    }

    if (viewModel.isEditScreen) {
        LaunchedEffect(Unit) {
            viewModel.getCardById(cardId)
        }
    }

    val snackbar = LocalSnackbar.current

    LaunchedEffect(Unit) {
        viewModel.messages.collect {
            snackbar(it)
        }
    }

    Column(
        modifier = Modifier
            .background(color = BgBlack)
    ) {

        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(16.dp))
            Icon(
                painter = painterResource(R.drawable.icon_arrow_left),
                tint = Color.White,
                contentDescription = "Go back",
                modifier = Modifier.clickWithRipple {
                    //navigator.popBackStack()
                }
            )

            Text(
                modifier = Modifier.padding(
                    top = 18.dp, bottom = 12.dp,
                    start = 16.dp, end = 16.dp
                ),
                text = if (viewModel.isEditScreen) "Edit Card" else "Add New Card",
                color = Color.White,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold
                ),
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

                Spacer(modifier = Modifier.height(16.dp))

                val matchingImage =
                    cardSuggestions.firstOrNull { it.first == viewModel.cardProviderName }?.second

                val painter = matchingImage ?: R.drawable.icon_card

                CardUi(
                    cardHolderName = viewModel.cardHolderName,
                    cardNumber = viewModel.cardNumber,
                    cardExpiryDate = viewModel.cardExpiryDate,
                    cardCVV = viewModel.cardCVV,
                    cardIcon = painter
                )

                Spacer(modifier = Modifier.height(22.dp))

                TextFieldDropDown()

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    value = viewModel.cardNumber,
                    label = {
                        Text(
                            text = "Card Number",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = poppinsFamily,
                                color = Gray
                            )
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = {
                        if (it.length <= 16) {
                            viewModel.cardNumber = it
                        }
                    },
                    supportingText = {
                        Text(text = "${viewModel.cardNumber.length}/16")
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = Color.Gray
                    ),
                    visualTransformation = visualTransformation,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            painter = painterResource(
                                id = R.drawable.icon_card_number
                            ),
                            tint = Color.Gray,
                            contentDescription = null
                        )
                    },
                    prefix = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextFieldSeparator(24)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    value = viewModel.cardHolderName,
                    label = {
                        Text(
                            text = "Card Holder Name",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = poppinsFamily,
                                color = Gray
                            )
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = {
                        if (it.length <= 30) {
                            viewModel.cardHolderName = it
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = Color.Gray
                    ),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            painter = painterResource(
                                id = R.drawable.icon_username
                            ),
                            tint = Color.Gray,
                            contentDescription = null
                        )
                    },
                    prefix = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextFieldSeparator(24)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1.4f)
                            .padding(end = 6.dp),
                        value = viewModel.cardExpiryDate,
                        onValueChange = { if (it.length <= 4) viewModel.cardExpiryDate = it },
                        label = {
                            Text(
                                text = "Expiry Date",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = poppinsFamily,
                                    color = Gray
                                )
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        supportingText = {
                            Text(text = "mm/yy")
                        },
                        visualTransformation = MaskVisualTransformation("##/##"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Gray,
                            cursorColor = Color.Gray
                        ),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(
                                    id = R.drawable.icon_date
                                ),
                                tint = Color.Gray,
                                contentDescription = null
                            )
                        },
                        prefix = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextFieldSeparator(24)
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.moveFocus(FocusDirection.Right)
                            }
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 6.dp),
                        value = viewModel.cardCVV,
                        label = {
                            Text(
                                text = "CVV",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = poppinsFamily,
                                    color = Gray
                                )
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        onValueChange = {
                            if (it.length <= 3) {
                                viewModel.cardCVV = it
                            }
                        },
                        supportingText = {
                            Text(text = "${viewModel.cardCVV.length}/3")
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Gray,
                            cursorColor = Color.Gray
                        ),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(
                                    id = R.drawable.icon_cvv
                                ),
                                tint = Color.Gray,
                                contentDescription = null
                            )
                        },
                        prefix = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextFieldSeparator(24)
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                if (viewModel.isEditScreen) {
                                    viewModel.validateAndUpdate(cardId)
                                } else {
                                    viewModel.validateAndInsert()
                                }
                            }
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        if (viewModel.isEditScreen) {
                            viewModel.validateAndUpdate(cardId)
                        } else {
                            viewModel.validateAndInsert()
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (viewModel.isEditScreen) "Update Card" else "Save Card",
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                if (viewModel.success.value) {
                    LaunchedEffect(Unit) {
                        //navigator.popBackStack()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDropDown(
    viewModel: CardViewModel = hiltViewModel()
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    ExposedDropdownMenuBox(
        modifier = Modifier.padding(horizontal = 16.dp),
        expanded = viewModel.expandedProviderField,
        onExpandedChange = {
            viewModel.expandedProviderField = !viewModel.expandedProviderField
            viewModel.hideKeyboard = true
        },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = viewModel.cardProviderName,
            onValueChange = {},
            label = {
                Text(
                    text = "Card Provider",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = poppinsFamily,
                        color = Gray
                    )
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = viewModel.expandedProviderField
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.Gray
            ),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = poppinsFamily,
                color = Color.Black
            )
        )

        DropdownMenu(
            modifier = Modifier
                .background(Color.White)
                .exposedDropdownSize(true),
            properties = PopupProperties(focusable = false),
            expanded = viewModel.expandedProviderField,
            onDismissRequest = { viewModel.expandedProviderField = false },
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
                        viewModel.cardProviderName = cardInfo.first
                        viewModel.selectedCardImage = cardInfo.second
                        viewModel.expandedProviderField = false
                        viewModel.hideKeyboard = true
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }

        LaunchedEffect(viewModel.hideKeyboard) {
            delay(100)
            keyboardController?.hide()
        }
    }
}
