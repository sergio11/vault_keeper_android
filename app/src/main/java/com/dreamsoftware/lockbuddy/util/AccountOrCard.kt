package com.dreamsoftware.lockbuddy.util

import com.dreamsoftware.lockbuddy.database.AccountEntity
import com.dreamsoftware.lockbuddy.database.CardEntity

sealed class AccountOrCard {
    data class AccountItem(val account: AccountEntity) : AccountOrCard()
    data class CardItem(val card: CardEntity) : AccountOrCard()
}