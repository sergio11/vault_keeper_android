package com.dreamsoftware.vaultkeeper.data.repository.impl.core

import com.dreamsoftware.vaultkeeper.domain.exception.DomainRepositoryException
import com.dreamsoftware.vaultkeeper.domain.exception.RepositoryOperationException

abstract class SupportRepositoryImpl {

    protected suspend fun <T> safeExecute(block: suspend () -> T): T = try {
        block()
    }
    catch (ex: DomainRepositoryException) {
        throw ex
    }
    catch (ex: Exception) {
        throw RepositoryOperationException("Failed to execute operation", ex)
    }
}