package com.dreamsoftware.lockbuddy.domain.repository

import com.dreamsoftware.lockbuddy.domain.exception.CheckAuthenticatedException
import com.dreamsoftware.lockbuddy.domain.exception.CloseSessionException
import com.dreamsoftware.lockbuddy.domain.exception.SignInException
import com.dreamsoftware.lockbuddy.domain.exception.SignUpException
import com.dreamsoftware.lockbuddy.domain.model.AuthRequestBO
import com.dreamsoftware.lockbuddy.domain.model.AuthUserBO

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