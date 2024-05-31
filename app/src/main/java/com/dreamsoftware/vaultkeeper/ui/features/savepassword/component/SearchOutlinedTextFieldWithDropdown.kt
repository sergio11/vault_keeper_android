package com.dreamsoftware.vaultkeeper.ui.features.savepassword.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.dreamsoftware.vaultkeeper.ui.features.savepassword.SavePasswordScreenActionListener
import com.dreamsoftware.vaultkeeper.ui.features.savepassword.SavePasswordUiState
import com.dreamsoftware.vaultkeeper.ui.theme.Gray
import com.dreamsoftware.vaultkeeper.ui.theme.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchOutlinedTextFieldWithDropdown(
    uiState: SavePasswordUiState,
    actionListener: SavePasswordScreenActionListener
) {
    with(uiState) {
        val focusManager = LocalFocusManager.current

//    Adjustment for dropdown height to display
//    single item in dropdown instead of empty list space.

        val dropDownMenuVerticalPadding = 8.dp
        val itemHeights = remember { mutableStateMapOf<Int, Int>() }
        val baseHeight = 330.dp
        val density = LocalDensity.current
        val maxHeight = remember(itemHeights.toMap()) {
            if (itemHeights.keys.toSet() != suggestions.indices.toSet()) {
                // if we don't have all heights calculated yet, return default value
                return@remember baseHeight
            }
            val baseHeightInt = with(density) { baseHeight.toPx().toInt() }

            // top+bottom system padding
            var sum = with(density) { dropDownMenuVerticalPadding.toPx().toInt() } * 2
            for ((i, itemSize) in itemHeights.toSortedMap()) {
                sum += itemSize
                if (sum >= baseHeightInt) {
                    return@remember with(density) { (sum - itemSize / 2).toDp() }
                }
            }
            // all items fit into base height
            baseHeight
        }

        ExposedDropdownMenuBox(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            expanded = suggestions.isNotEmpty(),
            onExpandedChange = { actionListener.onResetSuggestions() },
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = accountName,
                label = {
                    Text(
                        "Type Account Name ...",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = poppinsFamily,
                            color = Gray
                        )
                    )
                },
                onValueChange = {
                    actionListener.onFilterByAccountName(name = it)
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
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
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            DropdownMenu(
                modifier = Modifier
                    .background(Color.White)
                    .exposedDropdownSize(true)
                    .requiredSizeIn(maxHeight = maxHeight),
                properties = PopupProperties(focusable = false),
                expanded = suggestions.isNotEmpty(),
                onDismissRequest = actionListener::onResetSuggestions,
            ) {
                suggestions.forEachIndexed { index, selectedAccountName ->
                    DropdownMenuItem(
                        text = { Text(selectedAccountName) },
                        onClick = {
                            actionListener.onAccountNameUpdated(selectedAccountName)
                        },
                        modifier = Modifier.onSizeChanged {
                            itemHeights[index] = it.height
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}