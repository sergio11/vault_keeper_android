package com.dreamsoftware.lockbuddy.ui.settings

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.identity.Identity
import com.dreamsoftware.lockbuddy.R
import com.dreamsoftware.lockbuddy.signin.GoogleAuthUiClient
import com.dreamsoftware.lockbuddy.signin.UserData
import com.dreamsoftware.lockbuddy.ui.components.AlertDialogContent
import com.dreamsoftware.lockbuddy.ui.components.BottomSheet
import com.dreamsoftware.lockbuddy.ui.components.SheetSurface
import com.dreamsoftware.lockbuddy.ui.theme.BgBlack
import com.dreamsoftware.lockbuddy.ui.theme.Blue
import com.dreamsoftware.lockbuddy.ui.theme.LightBlue
import com.dreamsoftware.lockbuddy.ui.theme.Red
import com.dreamsoftware.lockbuddy.ui.theme.White
import com.dreamsoftware.lockbuddy.ui.theme.poppinsFamily
import com.dreamsoftware.lockbuddy.util.isBiometricSupported
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class SettingsItem(
    val text: String,
    val icon: Int,
    val onClick: () -> Unit
)

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    // intent for sharing app
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    val appPackageName = context.packageName
    val shareMessage = context.getString(R.string.share_message, appPackageName)

    var showSheet by remember { mutableStateOf(false) }

    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    val user = googleAuthUiClient.getSignedInUser()

    if (viewModel.showAllDataDeleteDialog) ConfirmDataDeletionDialog()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BgBlack),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(top = 18.dp, bottom = 12.dp),
            text = "Settings",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppinsFamily
        )

        val items = remember {
            listOf(
                SettingsItem(
                    text = "Reset Master Key",
                    icon = R.drawable.icon_lock_open,
                    onClick = {
                       // navigator.navigate(MasterKeyScreenDestination(NavigationSource.SETTINGS))
                    }
                ),
                if (isBiometricSupported(context)) SettingsItem(
                    text = "Fingerprint Unlock",
                    icon = R.drawable.icon_fingerprint,
                    onClick = {
                        /**/
                    }
                ) else null,
                SettingsItem(
                    text = "Share",
                    icon = R.drawable.icon_share,
                    onClick = {
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        context.startActivity(Intent.createChooser(shareIntent, "Share app link"))
                    }
                ),
                SettingsItem(
                    text = "About",
                    icon = R.drawable.icon_info,
                    onClick = { showSheet = true }
                ),
                SettingsItem(
                    text = "Logout",
                    icon = R.drawable.icon_logout,
                    onClick = {
                        viewModel.showAllDataDeleteDialog = true
                    }
                )
            )
        }

        SheetSurface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 16.dp
                    )
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                UserProfileRow(userData = user)

                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 16.dp,
                            horizontal = 46.dp
                        )
                )

                items.forEach { settingsItem ->
                    settingsItem?.let { SettingsItemRow(it) }
                }
            }
        }
        if (showSheet) {
            BottomSheet(
                onDismiss = { showSheet = false },
                content = {
                    Column(
                        Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(16.dp),
                            text = stringResource(R.string.about_info),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = poppinsFamily,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.navigationBarsPadding())
                        Spacer(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                                .background(color = BgBlack)
                        )
                    }
                }
            )
        }
    }
    BackHandler {
        (context as ComponentActivity).finish()
    }
}

@Composable
fun UserProfileRow(userData: UserData?) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            userData?.profilePictureUrl?.replace(
                oldValue = "s96-c", newValue = "s192-c", ignoreCase = true
            )?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.padding(start = 16.dp, end = 6.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                userData?.username?.let { username ->
                    Text(
                        text = username,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                userData?.email?.let { email ->
                    Text(
                        text = email,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsItemRow(
    settingsItem: SettingsItem,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .clickable {
                settingsItem.onClick.invoke()
            }
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp, bottom = 16.dp,
                    start = 16.dp, end = 16.dp
                )
        ) {

            Icon(
                painter = painterResource(settingsItem.icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (settingsItem.text == "Logout") Red else Color.Gray
            )

            Text(
                modifier = Modifier.padding(top = 2.dp, start = 16.dp),
                text = settingsItem.text,
                fontSize = 16.sp,
                color = if (settingsItem.text == "Logout") Red else Color.Black,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            if (settingsItem.text == "Fingerprint Unlock") {
                Switch(
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 18.dp),
                    checked = viewModel.checked,
                    onCheckedChange = {
                        viewModel.checked = !viewModel.checked
                        viewModel.setSwitchState(viewModel.checked)
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Blue,
                        uncheckedTrackColor = LightBlue,
                        uncheckedBorderColor = LightBlue,
                        uncheckedThumbColor = Color.White
                    )
                )
            }
        }
    }
}

@Composable
private fun ConfirmDataDeletionDialog(
    viewModel: SettingsViewModel = hiltViewModel(),
) {

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    AlertDialogContent(
        onDismissRequest = {
            viewModel.showAllDataDeleteDialog = false
        },
        onConfirmation = {
            scope.launch(Dispatchers.IO) {
                googleAuthUiClient.signOut()
            }
            viewModel.deleteAll()
            viewModel.showAllDataDeleteDialog = false
            /*navigator.navigate(IntroScreenDestination) {
                popUpTo(IntroScreenDestination) {
                    inclusive = true
                }
            }*/
        },
        dialogTitle = "Logout from lockbuddy?",
        dialogText = "Logging out will delete all your saved data including cards and passwords." +
                "\n\nAre you sure you want to proceed?",
        confirmTitle = "Logout"
    )
}