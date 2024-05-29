package com.dreamsoftware.vaultkeeper.domain.model

data class SecureCardBO(
    val id: Int,
    val cardHolderName: String,
    val cardNumber: String,
    val cardExpiryDate: String,
    val cardCvv: String,
    val cardProvider: String,
    val createdAt: Long
)
