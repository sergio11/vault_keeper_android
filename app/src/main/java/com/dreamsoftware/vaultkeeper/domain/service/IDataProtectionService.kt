package com.dreamsoftware.vaultkeeper.domain.service

import com.dreamsoftware.vaultkeeper.domain.model.ICryptable

interface IDataProtectionService {

    suspend fun <T: ICryptable<T>> wrap(data: T): T

    suspend fun <T: ICryptable<T>> unwrap(data: T): T
}