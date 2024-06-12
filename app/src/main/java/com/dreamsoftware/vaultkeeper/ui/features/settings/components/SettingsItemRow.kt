package com.dreamsoftware.vaultkeeper.ui.features.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreamsoftware.vaultkeeper.ui.features.settings.SettingsItem
import com.dreamsoftware.vaultkeeper.ui.theme.Blue
import com.dreamsoftware.vaultkeeper.ui.theme.LightBlue
import com.dreamsoftware.vaultkeeper.ui.theme.Red

@Composable
internal fun SettingsItemRow(
    item: SettingsItem,
    onClicked: (SettingsItem) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .clickable { onClicked(item) }
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
                painter = painterResource(item.icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (item is SettingsItem.LogoutItem) Red else Color.Gray
            )

            Text(
                modifier = Modifier.padding(top = 2.dp, start = 16.dp),
                text = item.text,
                fontSize = 16.sp,
                color = if (item is SettingsItem.LogoutItem) Red else Color.Black,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            if (item is SettingsItem.BiometricItem) {
                Switch(
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 18.dp),
                    checked = item.isEnabled,
                    onCheckedChange = { onClicked(item) },
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