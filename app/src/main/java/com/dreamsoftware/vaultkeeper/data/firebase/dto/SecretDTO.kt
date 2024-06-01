package com.dreamsoftware.vaultkeeper.data.firebase.dto

data class SecretDTO(
    val userUid: String,
    val secret: String,
    val salt: String
)
