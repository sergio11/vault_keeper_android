package com.dreamsoftware.vaultkeeper.ui.features.savecard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.vaultkeeper.ui.utils.formatCardNumber
import com.dreamsoftware.vaultkeeper.ui.utils.formatExpiryDate
import com.dreamsoftware.vaultkeeper.utils.randomGradient

@Composable
fun CardUi(
    cardHolderName: String,
    cardNumber: String,
    cardExpiryDate: String,
    cardCVV: String,
    cardIcon: Int
) {
    val formattedCardNumber = cardNumber.formatCardNumber()
    val formattedExpiryDate = cardExpiryDate.formatExpiryDate()
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .height(190.dp)
                .width(320.dp),
            shape = RoundedCornerShape(10.dp),
            contentColor = Color.White,
            shadowElevation = 26.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(randomGradient)
                    )
            ) {
                BrownieText(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(all = 16.dp),
                    type = BrownieTextTypeEnum.LABEL_LARGE,
                    titleText = cardHolderName,
                )
                Image(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(80.dp)
                        .padding(16.dp),
                    painter = painterResource(cardIcon),
                    contentDescription = null,
                )
                BrownieText(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(horizontal = 16.dp),
                    type = BrownieTextTypeEnum.TITLE_SMALL,
                    titleText = formattedCardNumber,
                )
                Row(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(end = 16.dp)
                    ) {
                        BrownieText(
                            type = BrownieTextTypeEnum.TITLE_MEDIUM,
                            titleText = "VALID",
                        )
                        BrownieText(
                            type = BrownieTextTypeEnum.TITLE_MEDIUM,
                            titleText = formattedExpiryDate,
                            textColor = Color.White
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(end = 16.dp)
                    ) {
                        BrownieText(
                            type = BrownieTextTypeEnum.TITLE_MEDIUM,
                            titleText = "CVV",
                        )
                        BrownieText(
                            type = BrownieTextTypeEnum.TITLE_MEDIUM,
                            titleText = cardCVV,
                            textColor = Color.White
                        )
                    }
                }
            }
        }
    }
}
