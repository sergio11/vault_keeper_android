package com.dreamsoftware.vaultkeeper.util

import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.entity.CardEntity

sealed class AccountOrCard {
    data class AccountItem(val account: AccountEntity) : AccountOrCard()
    data class CardItem(val card: CardEntity) : AccountOrCard()
}