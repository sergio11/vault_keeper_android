package com.dreamsoftware.vaultkeeper.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "secure_cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val cardHolderName: String,
    val cardNumber: String,
    val cardExpiryDate: String,
    val cardCvv: String,
    val cardProvider: String,
    val createdAt: Long
)