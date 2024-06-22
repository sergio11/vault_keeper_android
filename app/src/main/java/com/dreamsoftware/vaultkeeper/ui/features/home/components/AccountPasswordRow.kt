package com.dreamsoftware.vaultkeeper.ui.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieCardRow
import com.dreamsoftware.brownie.component.BrownieDropdownMenuButton
import com.dreamsoftware.brownie.component.BrownieDropdownMenuButtonItem
import com.dreamsoftware.brownie.component.BrownieIconButton
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.ui.features.home.HomeScreenActionListener
import com.dreamsoftware.vaultkeeper.utils.suggestionsWithImages

private const val EDIT_ACCOUNT_PASSWORD_ITEM_ID = "EDIT_ACCOUNT_PASSWORD"
private const val DELETE_ACCOUNT_PASSWORD_ITEM_ID = "DELETE_ACCOUNT_PASSWORD"

@Composable
fun AccountPasswordRow(
    account: AccountPasswordBO,
    actionListener: HomeScreenActionListener
) {
    with(MaterialTheme.colorScheme) {
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
                    containerColor = background,
                    iconTintColor = primary,
                    containerSize = 32.dp,
                    iconSize = 28.dp,
                    iconPadding = 4.dp,
                ) {
                    actionListener.onCopyAccountPasswordToClipboard(account.password)
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
                            id = EDIT_ACCOUNT_PASSWORD_ITEM_ID,
                            titleRes = R.string.edit_account_password_dropdown_option_text,
                            iconRes = R.drawable.icon_edit
                        ),
                        BrownieDropdownMenuButtonItem(
                            id = DELETE_ACCOUNT_PASSWORD_ITEM_ID,
                            titleRes = R.string.delete_account_password_dropdown_option_text,
                            iconRes = R.drawable.icon_delete
                        )
                    ),
                    onMenuItemClicked = {
                        when(it.id) {
                            EDIT_ACCOUNT_PASSWORD_ITEM_ID -> actionListener.onEditAccountPassword(account.uid)
                            DELETE_ACCOUNT_PASSWORD_ITEM_ID -> actionListener.onDeleteAccount(account)
                        }
                    }
                )
            }
        }
    }
}
