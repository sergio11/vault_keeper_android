package com.dreamsoftware.vaultkeeper.data.remote.dto

data class AccountDTO(
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