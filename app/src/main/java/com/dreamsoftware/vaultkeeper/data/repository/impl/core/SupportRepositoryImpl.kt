package com.dreamsoftware.vaultkeeper.data.repository.impl.core

import com.dreamsoftware.vaultkeeper.domain.exception.DomainRepositoryException
import com.dreamsoftware.vaultkeeper.domain.exception.RepositoryOperationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SupportRepositoryImpl(
    private val dispatcher: CoroutineDispatcher
) {
    protected suspend fun <T> safeExecute(block: suspend () -> T): T = withContext(dispatcher) {
        try {
            block()
        }
        catch (ex: DomainRepositoryException) {
            throw ex
        }
        catch (ex: Exception) {
            throw RepositoryOperationException("Failed to execute operation", ex)
        }
    }
}