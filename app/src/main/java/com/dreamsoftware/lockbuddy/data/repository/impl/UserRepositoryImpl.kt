package com.dreamsoftware.lockbuddy.data.repository.impl

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.lockbuddy.data.firebase.datasource.IAuthDataSource
import com.dreamsoftware.lockbuddy.data.firebase.dto.AuthUserDTO
import com.dreamsoftware.lockbuddy.domain.exception.CheckAuthenticatedException
import com.dreamsoftware.lockbuddy.domain.exception.CloseSessionException
import com.dreamsoftware.lockbuddy.domain.exception.SignInException
import com.dreamsoftware.lockbuddy.domain.exception.SignUpException
import com.dreamsoftware.lockbuddy.domain.model.AuthRequestBO
import com.dreamsoftware.lockbuddy.domain.model.AuthUserBO
import com.dreamsoftware.lockbuddy.domain.repository.IUserRepository

internal class UserRepositoryImpl(
    private val authDataSource: IAuthDataSource,
    private val authUserMapper: IBrownieOneSideMapper<AuthUserDTO, AuthUserBO>
): IUserRepository {

    @Throws(CheckAuthenticatedException::class)
    override suspend fun isAuthenticated(): Boolean = try {
        authDataSource.isAuthenticated()
    } catch (ex: Exception) {
        throw CheckAuthenticatedException(
            "An error occurred when trying to check if user is already authenticated",
            ex
        )
    }

    @Throws(CheckAuthenticatedException::class)
    override suspend fun getUserAuthenticatedUid(): String = try {
        authDataSource.getUserAuthenticatedUid()
    } catch (ex: Exception) {
        throw CheckAuthenticatedException(
            "An error occurred when trying to check if user is already authenticated",
            ex
        )
    }

    @Throws(SignInException::class)
    override suspend fun signIn(authRequest: AuthRequestBO): AuthUserBO = try {
        with(authRequest) {
            val authUser = authDataSource.signIn(email, password)
            authUserMapper.mapInToOut(authUser)
        }
    } catch (ex: Exception) {
        throw SignInException("An error occurred when trying to sign in user", ex)
    }

    @Throws(SignUpException::class)
    override suspend fun signUp(email: String, password: String): AuthUserBO = try {
        val authUser = authDataSource.signUp(email, password)
        authUserMapper.mapInToOut(authUser)
    } catch (ex: Exception) {
        ex.printStackTrace()
        throw SignUpException("An error occurred when trying to sign up user", ex)
    }

    @Throws(CloseSessionException::class)
    override suspend fun closeSession() {
        try {
            authDataSource.closeSession()
        } catch (ex: Exception) {
            throw CloseSessionException("An error occurred when trying to close user session", ex)
        }
    }
}