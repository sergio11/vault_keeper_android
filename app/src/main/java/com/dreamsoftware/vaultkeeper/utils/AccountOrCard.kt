package com.dreamsoftware.vaultkeeper.utils

import com.dreamsoftware.vaultkeeper.data.database.entity.AccountEntity
import com.dreamsoftware.vaultkeeper.data.database.entity.SecureCardEntity

sealed class AccountOrCard {
    data class AccountItem(val account: AccountEntity) : AccountOrCard()
    data class CardItem(val card: SecureCardEntity) : AccountOrCard()
}