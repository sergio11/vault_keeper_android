package com.dreamsoftware.vaultkeeper.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.vaultkeeper.data.firebase.datasource.IAuthDataSource
import com.dreamsoftware.vaultkeeper.data.firebase.dto.AuthUserDTO
import com.dreamsoftware.vaultkeeper.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.vaultkeeper.domain.exception.CheckAuthenticatedException
import com.dreamsoftware.vaultkeeper.domain.exception.CloseSessionException
import com.dreamsoftware.vaultkeeper.domain.exception.SignInException
import com.dreamsoftware.vaultkeeper.domain.exception.SignUpException
import com.dreamsoftware.vaultkeeper.domain.model.AuthRequestBO
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.repository.IUserRepository

internal class UserRepositoryImpl(
    private val authDataSource: IAuthDataSource,
    private val authUserMapper: IBrownieOneSideMapper<AuthUserDTO, AuthUserBO>
): SupportRepositoryImpl(), IUserRepository {

    @Throws(CheckAuthenticatedException::class)
    override suspend fun isAuthenticated(): Boolean = safeExecute {
        try {
            authDataSource.isAuthenticated()
        } catch (ex: Exception) {
            throw CheckAuthenticatedException(
                "An error occurred when trying to check if user is already authenticated",
                ex
            )
        }
    }

    @Throws(CheckAuthenticatedException::class)
    override suspend fun getUserAuthenticatedUid(): String = safeExecute {
        try {
            authDataSource.getUserAuthenticatedUid()
        } catch (ex: Exception) {
            throw CheckAuthenticatedException(
                "An error occurred when trying to check if user is already authenticated",
                ex
            )
        }
    }

    @Throws(SignInException::class)
    override suspend fun signIn(authRequest: AuthRequestBO): AuthUserBO = safeExecute {
        try {
            with(authRequest) {
                val authUser = authDataSource.signIn(email, password)
                authUserMapper.mapInToOut(authUser)
            }
        } catch (ex: Exception) {
            throw SignInException("An error occurred when trying to sign in user", ex)
        }
    }

    @Throws(SignUpException::class)
    override suspend fun signUp(email: String, password: String): AuthUserBO = safeExecute {
        try {
            val authUser = authDataSource.signUp(email, password)
            authUserMapper.mapInToOut(authUser)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw SignUpException("An error occurred when trying to sign up user", ex)
        }
    }

    @Throws(CloseSessionException::class)
    override suspend fun closeSession() = safeExecute {
        try {
            authDataSource.closeSession()
        } catch (ex: Exception) {
            throw CloseSessionException("An error occurred when trying to close user session", ex)
        }
    }
}