package com.dreamsoftware.vaultkeeper.ui.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieBottomSheet
import com.dreamsoftware.brownie.component.BrownieDefaultTextField
import com.dreamsoftware.brownie.component.BrownieDialog
import com.dreamsoftware.brownie.component.BrownieImageIcon
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.BrownieType
import com.dreamsoftware.brownie.component.fab.BrownieFabButtonSub
import com.dreamsoftware.brownie.component.fab.BrownieMultiFloatingActionButton
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.model.AccountBO
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.ui.features.home.components.AccountPasswordRow
import com.dreamsoftware.vaultkeeper.ui.features.home.components.CardRow
import com.dreamsoftware.vaultkeeper.ui.features.home.components.ColumnProgressIndicator
import com.dreamsoftware.vaultkeeper.ui.features.home.components.EmptyListPlaceholder

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    clipboardManager: ClipboardManager,
    actionListener: HomeScreenActionListener
) {
    with(uiState) {
        with(MaterialTheme.colorScheme) {
            val isVisible = rememberSaveable { mutableStateOf(true) }
            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
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

            BrownieScreenContent(
                hasTopBar = false,
                screenContainerColor = primary,
                onBuildFloatingActionButton = {
                    AnimatedVisibility(
                        visible = isVisible.value,
                        enter = slideInVertically(initialOffsetY = { it * 2 }),
                        exit = slideOutVertically(targetOffsetY = { it * 2 }),
                    ) {
                        BrownieMultiFloatingActionButton(
                            items = fabButtonItemList,
                            fabIcon = fabButtonMain,
                            fabOption = BrownieFabButtonSub(backgroundTint = primaryContainer),
                            onFabItemClicked = actionListener::onFabItemClicked
                        )
                    }
                }
            ) {
                BrownieText(
                    modifier = Modifier
                        .padding(top = 18.dp, bottom = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    type = BrownieTextTypeEnum.TITLE_LARGE,
                    titleText = "My Vault",
                    textColor = onPrimary
                )
                BrownieSheetSurface(
                    enableVerticalScroll = false,
                    verticalArrangement = Arrangement.Top
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BrownieDefaultTextField(
                            modifier = Modifier
                                .padding(
                                    start = 16.dp, end = 6.dp,
                                    top = 16.dp, bottom = 8.dp
                                )
                                .fillMaxWidth(0.8f),
                            labelRes = R.string.home_search_text_input_label,
                            placeHolderRes = R.string.home_search_text_input_placeholder,
                            value = searchQuery,
                            onValueChanged = {
                                if (it.length <= 25) {
                                    actionListener.onSearchQueryUpdated(newSearchQuery = it)
                                }
                            },
                            leadingIconRes = R.drawable.icon_search,
                            isSingleLine = true,
                        )


                        Box(
                            modifier = Modifier
                                .padding(
                                    start = 6.dp, end = 16.dp,
                                    top = 16.dp, bottom = 8.dp
                                )
                                .size(54.dp)
                                .background(primaryContainer, shape = RoundedCornerShape(16.dp))
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    actionListener.onFilterBottomSheetVisibilityUpdated(
                                        isVisible = true
                                    )
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
                        BrownieBottomSheet(
                            onDismiss = {
                                actionListener.onFilterBottomSheetVisibilityUpdated(
                                    isVisible = false
                                )
                            },
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
                                            BrownieImageIcon(
                                                type = BrownieType.ICON,
                                                iconRes = icon
                                            )
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
                                        .background(color = Color.White)
                                )
                            }
                        )
                    }
                    if (isLoading) {
                        ColumnProgressIndicator()
                    } else if (credentials.isEmpty()) {
                        EmptyListPlaceholder(
                            isSearch = searchQuery.isNotEmpty(),
                            selectedOption = selectedOption
                        )
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
                                            AccountPasswordRow(
                                                account = credential,
                                                actionListener = actionListener
                                            )
                                        }
                                    }

                                    is SecureCardBO -> {
                                        if (selectedOption == FilterOptionsEnum.ALL || selectedOption == FilterOptionsEnum.CARDS) {
                                            CardRow(
                                                card = credential,
                                                actionListener = actionListener
                                            )
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