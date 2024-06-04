package com.dreamsoftware.vaultkeeper.data.remote.dto

data class SecretDTO(
    val userUid: String,
    val secret: String,
    val salt: String
)
