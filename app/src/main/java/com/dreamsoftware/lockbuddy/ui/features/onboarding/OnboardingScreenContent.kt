package com.dreamsoftware.lockbuddy.ui.features.onboarding

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.lockbuddy.R
import com.dreamsoftware.lockbuddy.ui.theme.Blue
import com.dreamsoftware.lockbuddy.ui.theme.poppinsFamily

@Composable
fun OnboardingScreenContent(
    viewModel: OnboardingViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(id = R.drawable.main_background),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.intro_tagline_1),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = TextStyle(
                    fontSize = 42.sp,
                    fontFamily = poppinsFamily,
                    lineHeight = 42.sp
                )
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.intro_tagline_2),
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Normal,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(46.dp))

            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                onClick = {

                    /*navigator.navigate(
                        MasterKeyScreenDestination(NavigationSource.INTRO)
                    )*/
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.get_started),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = poppinsFamily
                    )
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}