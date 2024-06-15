package com.dreamsoftware.vaultkeeper.ui.features.unlock

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreamsoftware.brownie.component.BrownieSheetSurface
import com.dreamsoftware.brownie.component.BrownieTextFieldPassword
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.theme.BgBlack
import com.dreamsoftware.vaultkeeper.ui.theme.Blue

@Composable
fun UnlockScreenContent(
    uiState: UnlockScreenUiState,
    actionListener: UnlockScreenActionListener
) {
    with(uiState) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BgBlack)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                BrownieSheetSurface {

                    Text(
                        modifier = Modifier
                            .padding(
                                start = 16.dp, end = 16.dp,
                                top = 16.dp, bottom = 8.dp
                            )
                            .fillMaxWidth(),
                        text = "Unlock with your \nMaster Key",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )

                    )

                    BrownieTextFieldPassword(
                        modifier = Modifier
                            .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        labelRes = R.string.create_master_key_label,
                        placeHolderRes = R.string.create_master_key_placeholder,
                        value = masterKey,
                        onValueChanged = {
                            if (it.length <= 8) {
                                actionListener.onMaterKeyUpdated(newMasterKey = it)
                            }
                        },
                        leadingIconRes = R.drawable.icon_lock,
                        supportingText = {
                            "${masterKey.length}/8"
                        },
                        onDone = {
                            actionListener.onValidate()
                        }
                    )

                    Button(
                        modifier = Modifier
                            .padding(
                                start = 16.dp, end = 16.dp,
                                top = 8.dp, bottom = 16.dp
                            )
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = actionListener::onValidate,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue,
                            contentColor = Color.White
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                text = "Proceed",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Image(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .offset(y = (80).dp),
                painter = painterResource(R.drawable.key),
                contentDescription = null
            )
        }
    }
}