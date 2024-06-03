package com.dreamsoftware.vaultkeeper.domain.model

interface ICryptable<out T : ICryptable<T>> {
    fun accept(visitor: ICryptoVisitor): T
}