package com.dreamsoftware.lockbuddy.util

import com.dreamsoftware.lockbuddy.data.database.entity.AccountEntity
import com.dreamsoftware.lockbuddy.data.database.entity.CardEntity

sealed class AccountOrCard {
    data class AccountItem(val account: AccountEntity) : AccountOrCard()
    data class CardItem(val card: CardEntity) : AccountOrCard()
}