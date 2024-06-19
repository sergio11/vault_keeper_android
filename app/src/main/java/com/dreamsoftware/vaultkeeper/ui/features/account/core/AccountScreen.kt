package com.dreamsoftware.vaultkeeper.ui.features.account.core

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.core.components.CommonVideoBackground
import com.dreamsoftware.vaultkeeper.ui.core.components.LoadingDialog

@Composable
fun AccountScreen(
    @StringRes mainTitleRes: Int,
    @RawRes videoResourceId: Int? = null,
    @DrawableRes screenBackgroundRes: Int? = null,
    isLoading: Boolean,
    errorMessage: String? = null,
    onErrorMessageCleared: () -> Unit = {},
    infoMessage: String? = null,
    onInfoMessageCleared: () -> Unit = {},
    enableVerticalScroll: Boolean = true,
    screenContent: @Composable ColumnScope.() -> Unit
) {
    LoadingDialog(isShowingDialog = isLoading)
    BrownieScreenContent(
        titleRes = mainTitleRes,
        backgroundRes = screenBackgroundRes,
        enableVerticalScroll = enableVerticalScroll,
        hasTopBar = false,
        errorMessage = errorMessage,
        infoMessage = infoMessage,
        onBuildBackgroundContent = {
            videoResourceId?.let {
                CommonVideoBackground(videoResourceId = it)
            }
        },
        screenContent = {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.main_logo),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 32.dp, vertical = 10.dp)
            )
            BrownieText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 30.dp),
                type = BrownieTextTypeEnum.HEADLINE_SMALL,
                textColor = Color.White,
                titleRes = mainTitleRes,
                textBold = true
            )
            Spacer(modifier = Modifier.weight(1f, fill = true))
            BrownieSheetSurface(
                surfaceColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f),
                enableVerticalScroll = false,
                horizontalAlignment = Alignment.CenterHorizontally,
                onSurfaceContent = screenContent
            )
        }
    )
}