package com.dreamsoftware.vaultkeeper.ui.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreamsoftware.brownie.component.BrownieDialog
import com.dreamsoftware.brownie.component.BrownieImageIcon
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.BrownieType
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.ui.core.components.BottomSheet
import com.dreamsoftware.vaultkeeper.ui.core.components.SheetSurface
import com.dreamsoftware.vaultkeeper.ui.core.components.fab.MultiFloatingActionButton
import com.dreamsoftware.vaultkeeper.ui.features.home.components.AccountRow
import com.dreamsoftware.vaultkeeper.ui.features.home.components.CardRow
import com.dreamsoftware.vaultkeeper.ui.features.home.components.ColumnProgressIndicator
import com.dreamsoftware.vaultkeeper.ui.features.home.components.EmptyListPlaceholder
import com.dreamsoftware.vaultkeeper.ui.theme.BgBlack
import com.dreamsoftware.vaultkeeper.ui.theme.Blue
import com.dreamsoftware.vaultkeeper.ui.theme.poppinsFamily

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    clipboardManager: ClipboardManager,
    actionListener: HomeScreenActionListener
) {
    with(uiState) {
        val isVisible = rememberSaveable { mutableStateOf(true) }
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    // Hide FAB
                    if (available.y < -1) {
                        isVisible.value = false
                    }

                    // Show FAB
                    if (available.y > 1) {
                        isVisible.value = true
                    }

                    return Offset.Zero
                }
            }
        }

        BrownieDialog(
            isVisible = showCardDeleteDialog,
            mainLogoRes = R.drawable.main_logo_inverse,
            titleRes = R.string.delete_card_dialog_title,
            descriptionRes = R.string.delete_card_dialog_description,
            cancelRes = R.string.delete_card_dialog_cancel,
            acceptRes = R.string.delete_card_dialog_accept,
            onCancelClicked = actionListener::onDeleteSecureCardCancelled,
            onAcceptClicked = actionListener::onDeleteAccountConfirmed
        )

        BrownieDialog(
            isVisible = showAccountDeleteDialog,
            mainLogoRes = R.drawable.main_logo_inverse,
            titleRes = R.string.delete_password_dialog_title,
            descriptionRes = R.string.delete_password_dialog_description,
            cancelRes = R.string.delete_password_dialog_cancel,
            acceptRes = R.string.delete_password_dialog_accept,
            onCancelClicked = actionListener::onDeleteAccountCancelled,
            onAcceptClicked = actionListener::onDeleteAccountConfirmed
        )

        Scaffold(
            floatingActionButton = {
                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = slideInVertically(initialOffsetY = { it * 2 }),
                    exit = slideOutVertically(targetOffsetY = { it * 2 }),
                ) {
                    MultiFloatingActionButton(
                        items = fabButtonItemList,
                        fabIcon = fabButtonMain,
                        onFabItemClicked = actionListener::onFabItemClicked
                    )
                }
            }
        ) { innerPadding ->

            var showSheet by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(color = BgBlack),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                BrownieText(
                    modifier = Modifier.padding(
                        top = 18.dp, bottom = 12.dp
                    ),
                    type = BrownieTextTypeEnum.TITLE_LARGE,
                    titleText = "My Vault",
                    textColor = Color.White
                )

                SheetSurface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp, end = 6.dp,
                                        top = 16.dp, bottom = 8.dp
                                    )
                                    .weight(1f),
                                value = searchQuery,
                                onValueChange = {
                                    if (it.length <= 25) {
                                        actionListener.onSearchQueryUpdated(newSearchQuery = it)
                                    }
                                },
                                placeholder = {
                                    Text(
                                        "Search in Vault",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontFamily = poppinsFamily,
                                            fontWeight = FontWeight.Normal
                                        )
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_search),
                                        contentDescription = null
                                    )
                                },
                                shape = RoundedCornerShape(16.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = Color.Gray
                                )
                            )

                            Box(
                                modifier = Modifier
                                    .padding(
                                        start = 6.dp, end = 16.dp,
                                        top = 16.dp, bottom = 8.dp
                                    )
                                    .size(54.dp)
                                    .background(Blue, shape = RoundedCornerShape(16.dp))
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        showSheet = true
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(28.dp),
                                    painter = painterResource(R.drawable.icon_filter),
                                    contentDescription = "Filter Icon",
                                    tint = Color.White
                                )
                            }
                        }

                        if (showSheet) {
                            BottomSheet(
                                onDismiss = { showSheet = false },
                                content = {
                                    LazyColumn {
                                        items(filterOptions.size) { option ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        actionListener.onFilterOptionUpdated(
                                                            newFilterOption = filterOptions[option]
                                                        )
                                                    }
                                                    .padding(16.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                val isSelected = selectedOption == filterOptions[option]
                                                val icon =
                                                    if (isSelected) R.drawable.icon_selected else R.drawable.icon_unselected
                                                BrownieImageIcon(type = BrownieType.ICON, iconRes = icon)
                                                Spacer(modifier = Modifier.width(8.dp))
                                                BrownieText(
                                                    type = BrownieTextTypeEnum.LABEL_MEDIUM,
                                                    titleText = filterOptions[option].name,
                                                    textColor = Color.Black
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.navigationBarsPadding())
                                    Spacer(
                                        modifier = Modifier
                                            .height(50.dp)
                                            .fillMaxWidth()
                                            .background(color = BgBlack)
                                    )
                                }
                            )
                        }
                        if (isLoading) {
                            ColumnProgressIndicator()
                        } else if (credentials.isEmpty()) {
                            EmptyListPlaceholder(isSearch = searchQuery.isNotEmpty(), selectedOption = selectedOption)
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        start = 8.dp, end = 8.dp,
                                        top = 8.dp, bottom = 0.dp
                                    )
                                    .nestedScroll(nestedScrollConnection),
                            ) {

                                items(credentials.size) { idx ->
                                    when (val credential = credentials[idx]) {
                                        is AccountBO -> {
                                            if (selectedOption == FilterOptionsEnum.ALL || selectedOption == FilterOptionsEnum.PASSWORDS) {
                                                AccountRow(account = credential, actionListener = actionListener)
                                            }
                                        }

                                        is SecureCardBO -> {
                                            if (selectedOption == FilterOptionsEnum.ALL || selectedOption == FilterOptionsEnum.CARDS) {
                                                CardRow(card = credential, actionListener = actionListener)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}