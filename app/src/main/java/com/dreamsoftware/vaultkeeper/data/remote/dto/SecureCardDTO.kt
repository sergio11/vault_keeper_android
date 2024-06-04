package com.dreamsoftware.vaultkeeper.data.remote.dto

data class SecureCardDTO(
    val uid: String,
    val cardHolderName: String,
    val cardNumber: String,
    val cardExpiryDate: String,
    val cardCvv: String,
    val cardProvider: String,
    val createdAt: Long,
    val userUid: String
)