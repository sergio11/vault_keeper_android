package com.dreamsoftware.vaultkeeper.ui.features.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.ui.theme.White

@Composable
fun UserProfileRow(userData: AuthUserBO?) {
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
            userData?.photoUrl?.replace(
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
                userData?.displayName?.let { username ->
                    BrownieText(
                        titleText = username,
                        type = BrownieTextTypeEnum.LABEL_LARGE,
                        textColor = Color.Black,
                        textBold = true
                    )
                }
                userData?.email?.let { email ->
                    BrownieText(
                        titleText = email,
                        type = BrownieTextTypeEnum.LABEL_LARGE,
                        textColor = Color.Black
                    )
                }
            }
        }
    }
}