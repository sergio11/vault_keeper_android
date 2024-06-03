package com.dreamsoftware.vaultkeeper.domain.model

data class PBEDataBO(
    val secret: String,
    val salt: String
)