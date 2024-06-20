package com.dreamsoftware.vaultkeeper.ui.core.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.brownie.component.screen.BrownieScreenContent
import com.dreamsoftware.vaultkeeper.R

@Composable
fun CommonMasterKeyScreenContent(
    @StringRes mainTitleRes: Int,
    isLoading: Boolean,
    errorMessage: String? = null,
    onErrorMessageCleared: () -> Unit = {},
    infoMessage: String? = null,
    onInfoMessageCleared: () -> Unit = {},
    screenContent: @Composable ColumnScope.() -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    LoadingDialog(isShowingDialog = isLoading)
    BrownieScreenContent(
        enableVerticalScroll = true,
        hasTopBar = false,
        errorMessage = errorMessage,
        infoMessage = infoMessage,
        onErrorMessageCleared = onErrorMessageCleared,
        onInfoMessageCleared = onInfoMessageCleared,
        backgroundRes = R.drawable.main_background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .heightIn(min = screenHeight),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
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
                    .padding(start = 16.dp, end = 32.dp)
                    .fillMaxWidth(),
                type = BrownieTextTypeEnum.HEADLINE_SMALL,
                textColor = MaterialTheme.colorScheme.onPrimary,
                titleRes = mainTitleRes,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                screenContent()
            }
        }
    }
}