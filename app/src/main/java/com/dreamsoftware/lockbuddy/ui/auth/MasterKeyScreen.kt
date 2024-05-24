package com.dreamsoftware.lockbuddy.ui.auth

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.lockbuddy.R
import com.dreamsoftware.lockbuddy.ui.components.CreateMasterKeySheetContent
import com.dreamsoftware.lockbuddy.ui.components.UpdateMasterKeySheetContent
import com.dreamsoftware.lockbuddy.ui.theme.BgBlack
import com.dreamsoftware.lockbuddy.ui.theme.poppinsFamily
import com.dreamsoftware.lockbuddy.util.LocalSnackbar

enum class NavigationSource {
    INTRO,
    SETTINGS
}

@Composable
fun MasterKeyScreen(
    navigationSource: NavigationSource,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val snackbar = LocalSnackbar.current

    LaunchedEffect(Unit) {
        viewModel.messages.collect {
            snackbar(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigateToHome.collect {
            /*navigator.navigate(HomeScreenDestination) {
                popUpTo(HomeScreenDestination) {
                    inclusive = true
                }
            }*/
        }
    }

    Column(
        modifier = Modifier
            .background(color = BgBlack)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 32.dp),
            text = stringResource(R.string.setup_key_tagline_1),
            textAlign = TextAlign.Left,
            color = Color.White,
            style = TextStyle(
                fontSize = 46.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFamily,
                lineHeight = 60.sp
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (navigationSource == NavigationSource.SETTINGS) {
            UpdateMasterKeySheetContent()
        } else {
            CreateMasterKeySheetContent()
        }

        BackHandler {
            if (navigationSource == NavigationSource.INTRO) {
                (context as ComponentActivity).finish()
            } else {
                //navigator.popBackStack()
            }
        }
    }
}