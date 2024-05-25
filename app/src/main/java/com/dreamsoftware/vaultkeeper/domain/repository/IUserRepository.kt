package com.dreamsoftware.vaultkeeper.domain.repository

import com.dreamsoftware.vaultkeeper.domain.exception.CheckAuthenticatedException
import com.dreamsoftware.vaultkeeper.domain.exception.CloseSessionException
import com.dreamsoftware.vaultkeeper.domain.exception.SignInException
import com.dreamsoftware.vaultkeeper.domain.exception.SignUpException
import com.dreamsoftware.vaultkeeper.domain.model.AuthRequestBO
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO

interface IUserRepository {

    @Throws(CheckAuthenticatedException::class)
    suspend fun isAuthenticated(): Boolean

    @Throws(CheckAuthenticatedException::class)
    suspend fun getUserAuthenticatedUid(): String

    @Throws(SignInException::class)
    suspend fun signIn(authRequest: AuthRequestBO): AuthUserBO

    @Throws(SignUpException::class)
    suspend fun signUp(email: String, password: String): AuthUserBO

    @Throws(CloseSessionException::class)
    suspend fun closeSession()
}