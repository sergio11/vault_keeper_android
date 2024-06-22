package com.dreamsoftware.vaultkeeper.ui.core.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dreamsoftware.brownie.component.BrownieText
import com.dreamsoftware.brownie.component.BrownieTextTypeEnum
import com.dreamsoftware.vaultkeeper.domain.model.CardProviderEnum
import com.dreamsoftware.vaultkeeper.ui.utils.formatCardNumber
import com.dreamsoftware.vaultkeeper.ui.utils.formatExpiryDate
import com.dreamsoftware.vaultkeeper.ui.utils.toCardProviderBrush

@Composable
fun SecureCardPreview(
    cardHolderName: String,
    cardNumber: String,
    cardExpiryDate: String,
    cardCVV: String,
    cardIcon: Int,
    cardProviderEnum: CardProviderEnum
) {
    val context = LocalContext.current
    val formattedCardNumber = cardNumber.formatCardNumber()
    val formattedExpiryDate = cardExpiryDate.formatExpiryDate()
    val cardBrush by rememberUpdatedState(cardProviderEnum.toCardProviderBrush(context))
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
                    .background(cardBrush)
            ) {
                BrownieText(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(all = 16.dp),
                    type = BrownieTextTypeEnum.LABEL_LARGE,
                    titleText = cardHolderName,
                    textColor = Color.White
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
                    textColor = Color.White
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
                            textColor = Color.Black
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
                            textColor = Color.Black
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
