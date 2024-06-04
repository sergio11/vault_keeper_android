package com.dreamsoftware.vaultkeeper.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey
    val uid: String,
    val accountName: String,
    val userName: String,
    val email: String,
    val mobileNumber: String,
    val password: String,
    val note: String,
    val createdAt: Long,
    val userUid: String
)