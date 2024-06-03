package com.dreamsoftware.vaultkeeper.domain.model

interface ICryptoVisitor {
    fun visit(field: String, value: String): String
}